import request from '@/utils/request'

// 查询库存管理列表
export function listStock(query) {
  return request({
    url: '/tile/stock/list',
    method: 'get',
    params: query
  })
}

// 查询库存管理详细
export function getStock(stockId) {
  return request({
    url: '/tile/stock/' + stockId,
    method: 'get'
  })
}

// 新增库存管理
export function addStock(data) {
  return request({
    url: '/tile/stock',
    method: 'post',
    data: data
  })
}

// 修改库存管理
export function updateStock(data) {
  return request({
    url: '/tile/stock',
    method: 'put',
    data: data
  })
}

// 删除库存管理
export function delStock(stockId) {
  return request({
    url: '/tile/stock/' + stockId,
    method: 'delete'
  })
}

// 导出库存管理
export function exportStock(query) {
  return request({
    url: '/tile/stock/export',
    method: 'post',
    params: query
  })
}

// 查询入库单列表
export function listStockIn(query) {
  return request({
    url: '/tile/stock/in/list',
    method: 'get',
    params: query
  })
}

// 查询入库单详细
export function getStockIn(inId) {
  return request({
    url: '/tile/stock/in/' + inId,
    method: 'get'
  })
}

// 新增入库单
export function addStockIn(data) {
  return request({
    url: '/tile/stock/in',
    method: 'post',
    data: data
  })
}

// 修改入库单
export function updateStockIn(data) {
  return request({
    url: '/tile/stock/in',
    method: 'put',
    data: data
  })
}

// 删除入库单
export function delStockIn(inId) {
  return request({
    url: '/tile/stock/in/' + inId,
    method: 'delete'
  })
}

// 导出入库单
export function exportStockIn(query) {
  return request({
    url: '/tile/stock/in/export',
    method: 'post',
    params: query
  })
}

// 提交入库单
export function submitStockIn(inId) {
  return request({
    url: '/tile/stock/in/submit/' + inId,
    method: 'put'
  })
}

// 取消入库单
export function cancelStockIn(inId) {
  return request({
    url: '/tile/stock/in/cancel/' + inId,
    method: 'put'
  })
}

// 查询出库单列表
export function listStockOut(query) {
  return request({
    url: '/tile/stock/out/list',
    method: 'get',
    params: query
  })
}

// 查询出库单详细
export function getStockOut(outId) {
  return request({
    url: '/tile/stock/out/' + outId,
    method: 'get'
  })
}

// 新增出库单
export function addStockOut(data) {
  return request({
    url: '/tile/stock/out',
    method: 'post',
    data: data
  })
}

// 修改出库单
export function updateStockOut(data) {
  return request({
    url: '/tile/stock/out',
    method: 'put',
    data: data
  })
}

// 删除出库单
export function delStockOut(outId) {
  return request({
    url: '/tile/stock/out/' + outId,
    method: 'delete'
  })
}

// 导出出库单
export function exportStockOut(query) {
  return request({
    url: '/tile/stock/out/export',
    method: 'post',
    params: query
  })
}

// 提交出库单
export function submitStockOut(outId) {
  return request({
    url: '/tile/stock/out/submit/' + outId,
    method: 'put'
  })
}

// 取消出库单
export function cancelStockOut(outId) {
  return request({
    url: '/tile/stock/out/cancel/' + outId,
    method: 'put'
  })
}

// 查询出库单明细列表
export function listStockOutDetail(query) {
  return request({
    url: '/tile/stock/out/detail/list',
    method: 'get',
    params: query
  })
}

// 查询出库单明细详细
export function getStockOutDetail(detailId) {
  return request({
    url: '/tile/stock/out/detail/' + detailId,
    method: 'get'
  })
}

// 根据出库单ID查询明细
export function getStockOutDetailByOutId(outId) {
  return request({
    url: '/tile/stock/out/detail/out/' + outId,
    method: 'get'
  })
}

// 新增出库单明细
export function addStockOutDetail(data) {
  return request({
    url: '/tile/stock/out/detail',
    method: 'post',
    data: data
  })
}

// 修改出库单明细
export function updateStockOutDetail(data) {
  return request({
    url: '/tile/stock/out/detail',
    method: 'put',
    data: data
  })
}

// 删除出库单明细
export function delStockOutDetail(detailId) {
  return request({
    url: '/tile/stock/out/detail/' + detailId,
    method: 'delete'
  })
}

// 导出出库单明细
export function exportStockOutDetail(query) {
  return request({
    url: '/tile/stock/out/detail/export',
    method: 'post',
    params: query
  })
}

// 查询库存记录列表
export function listStockRecord(query) {
  return request({
    url: '/tile/stock/record/list',
    method: 'get',
    params: query
  })
}

// 查询库存记录详细
export function getStockRecord(recordId) {
  return request({
    url: '/tile/stock/record/' + recordId,
    method: 'get'
  })
}

// 新增库存记录
export function addStockRecord(data) {
  return request({
    url: '/tile/stock/record',
    method: 'post',
    data: data
  })
}

// 修改库存记录
export function updateStockRecord(data) {
  return request({
    url: '/tile/stock/record',
    method: 'put',
    data: data
  })
}

// 删除库存记录
export function delStockRecord(recordId) {
  return request({
    url: '/tile/stock/record/' + recordId,
    method: 'delete'
  })
}

// 导出库存记录
export function exportStockRecord(query) {
  return request({
    url: '/tile/stock/record/export',
    method: 'post',
    params: query
  })
}

// 清空库存记录
export function cleanStockRecord() {
  return request({
    url: '/tile/stock/record/clean',
    method: 'delete'
  })
}

// 查询销售单列表
export function listSaleOrder(query) {
  return request({
    url: '/tile/sale/order/list',
    method: 'get',
    params: query
  })
}

// 查询销售单详情
export function getSaleOrder(orderId) {
  return request({
    url: '/tile/sale/order/' + orderId,
    method: 'get'
  })
}

// 查询客户列表
export function listCustomer(query) {
  return request({
    url: '/tile/customer/list',
    method: 'get',
    params: query
  })
}

// 查询仓位列表
export function listLocation(query) {
  return request({
    url: '/tile/location/list',
    method: 'get',
    params: query
  })
}
