# 后端 API 设计（MVP）

## 基础约定
- 前缀：`/api/v1`
- 认证：店铺或内部后台 Token（例如 JWT），除 Webhook 外均需携带。
- 响应格式：`{ code: 0, message: "ok", data: { ... } }`；错误使用非 0 code。
- 分页参数：`page` 从 1 开始，`pageSize` 默认 20。

## 店铺 Shops
- `POST /shops` 创建店铺
  - Body: `{ shopName, platform, storeUrl?, apiKey?, apiSecret?, accessToken?, timezone? }`
- `GET /shops` 列表（可按 platform 模糊搜索名称）
- `GET /shops/{id}` 查看
- `PUT /shops/{id}` 更新
- `DELETE /shops/{id}` 软删除/禁用

## 订单 Orders（预留）
- `GET /orders` 按店铺/时间范围分页查询。
- `GET /orders/{id}` 查看订单与包裹列表。

## 运单 Tracking Numbers
- `POST /tracking` 单条创建
  - Body: `{ shopId, orderId?, parcelId?, trackingNumber, carrierCode?, source, shippedAt?, deliveredAt? }`
- `POST /tracking/batch` 批量创建（CSV 导入解析后调用）
  - Body: `{ shopId, items: [{ trackingNumber, carrierCode?, parcelNo?, orderExternalId?, source }] }`
  - 返回导入任务 ID、成功/失败行数。
- `DELETE /tracking/batch` 批量删除
  - Body: `{ ids: [trackingId1, ...] }`
- `GET /tracking` 列表
  - Query: `shopId?`, `status?`, `keyword?`(trackingNumber 模糊), `updatedFrom?`, `updatedTo?`, `page`, `pageSize`
  - 返回基础信息、最新状态、最后同步时间。
- `GET /tracking/{id}` 详情
  - 返回 tracking 基础信息、关联订单/包裹、承运商、最近状态、轨迹列表。

## 轨迹 Tracking Events
- `GET /tracking/{id}/events` 按时间倒序返回轨迹列表。

## CSV 导入
- `POST /imports/tracking/csv` 上传 CSV 文件，返回导入任务 ID
- `GET /imports/{jobId}` 查看导入进度与错误行。

## Webhook
- `POST /webhook/17track`
  - Headers: `X-17Track-Signature`, `X-17Track-Timestamp`
  - Body: 按 17TRACK 文档透传。
  - 行为：
    1. 校验签名并记录 `tracking_webhook_logs`。
    2. 幂等：根据 trackingNumber+eventTime 去重。
    3. 入队异步处理，更新 `tracking_numbers.track_status/raw_status` 与 `tracking_events`。
    4. 返回 `200 { code:0 }`。

## 同步调度
- `POST /sync/trigger` 手动触发单个或批量同步
  - Body: `{ trackingIds: [], forcePull?: true }`
- `GET /sync/jobs` 查看同步任务
  - Query: `trackingId?`, `status?`, `page`, `pageSize`

## 承运商 Carriers
- `GET /carriers` 列表，支持代码/名称搜索。
- `POST /carriers` 创建或导入承运商信息。

## 系统监控
- `GET /health` 健康检查（数据库、Redis、消息队列）。

## 状态与枚举
- `track_status/stage` 对应 17TRACK：`InfoReceived`, `InTransit`, `OutForDelivery`, `Delivered`, `Exception`, `Expired` 等。
- 失败重试策略：队列消费异常时重试 3 次并记录 `sync_jobs`；超过阈值将 job 标记 failed 并告警。
