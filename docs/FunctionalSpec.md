# 功能与技术设计（MVP）

## 架构概览
- 前端：Vue3 + Vite + TypeScript，组件库可用 Element Plus。提供列表、筛选、详情时间线、CSV 导入/删除操作。
- 后端：Java Spring Boot + MyBatis，暴露 REST API。使用 MySQL 持久化、Redis 缓存热点查询、消息队列异步处理同步/重试。
- 数据流：
  1. 导入/创建运单 → 写入 `tracking_numbers`，初始状态记录 `raw_status`。
  2. 同步调度器根据 `next_sync_at` 推送任务到队列 → Worker 调用 17TRACK API → 更新状态、写入 `tracking_events`、刷新 `next_sync_at`。
  3. Webhook 接收 → 记录 `tracking_webhook_logs` → 校验签名/幂等 → 入队处理 → 更新 tracking 与 events。
  4. 前端通过 API 获取分页列表与详情，可使用 Redis 缓存列表查询（短期 TTL）与轨迹读取。

## 模块拆解
1. **店铺模块**
   - 功能：CRUD、凭证加密存储、平台枚举校验。
   - 表：`shops`。
2. **订单/包裹模块**
   - 功能：展示关联关系；从店铺同步时写入 `orders`、`parcels`；包裹与 tracking 绑定。
   - 表：`orders`, `parcels`。
3. **运单管理模块**
   - 功能：单条创建、批量导入、批量删除、列表分页、详情查询。
   - 表：`tracking_numbers`。
   - 缓存：按 trackingId 缓存基础信息；列表查询使用查询条件+页码缓存；删除/更新时失效。
4. **轨迹模块**
   - 功能：按时间倒序展示轨迹；新轨迹插入时去重(event_time+status+location)。
   - 表：`tracking_events`。
5. **Webhook 模块**
   - 功能：签名验证、日志记录、幂等控制（基于 trackingNumber+eventTime 哈希）；失败重试入队。
   - 表：`tracking_webhook_logs` (可选)。
6. **同步任务/重试模块**
   - 功能：调度器读取 due 的 `next_sync_at`，生成任务；消费者调用 17TRACK 查询；失败记录 `sync_jobs`，重试策略 3 次指数退避；超过阈值标记失败并通知。
   - 表：`tracking_numbers`（next_sync_at）、`sync_jobs`。
7. **导入模块**
   - 功能：上传 CSV → 解析 → 校验 → 生成导入任务记录（可复用 `sync_jobs` 或独立任务表）→ 批量写入 tracking 与 parcel 绑定。
   - 校验项：重复单号、缺失字段、承运商代码合法性。

## 前端页面
- 登录/鉴权（可后置）。
- 店铺管理页：列表、创建/编辑弹窗。
- 运单列表：筛选（店铺、状态、时间、关键字）、批量导入按钮、批量删除、分页。
- 导入结果弹窗：显示成功/失败行数与错误详情下载。
- 运单详情页：基础信息卡片、承运商信息、状态标签、轨迹时间线（倒序）。
- 同步任务页：显示 `sync_jobs`，支持重试按钮（调用 `/sync/trigger`）。

## 接入 17TRACK
- API 鉴权：使用 API Key/Secret 调用查询接口；可为多店铺配置不同 key。
- Webhook 校验：使用文档签名算法验证；拒绝超时/重复请求。
- 状态映射：将 17TRACK 状态字段映射到 `track_status` 与 `stage`，原始数据存 `raw_status`/`tracking_events.raw`。

## 消息队列与重试
- 建议 Topic：`tracking.sync`（主动拉取）、`tracking.webhook`（被动推送）、`tracking.retry`（失败重试）。
- 消费失败：记录 `sync_jobs`，增加 retry_count，使用延迟队列实现指数退避（1m/5m/15m）。

## 安全与审计
- API 鉴权与 RBAC；敏感凭证加密存储。
- Webhook 防重放：校验时间戳 + 签名；记录请求体以便审计。
- 输入校验与幂等：创建/导入接口使用请求幂等键（如批次 ID）。

## 性能与缓存
- 列表查询走 Redis 缓存，TTL 60s；详情缓存 5 分钟；轨迹列表可分页或限制最近 N 条。
- 频繁同步的 tracking 使用 `next_sync_at` 控制节流，递增间隔（已送达后延长同步间隔）。

## 监控
- 指标：Webhook 成功率、同步成功率、平均延迟、队列堆积量、API 错误率。
- 日志：按请求 ID/追踪号关联；错误日志进入告警渠道。
