import request from '@/utils/request'

// 查询销售订单列表
export function listOrder(query) {
  return request({
    url: '/tile/sale/order/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单详细
export function getOrder(orderId) {
  return request({
    url: '/tile/sale/order/' + orderId,
    method: 'get'
  })
}

// 新增销售订单
export function addOrder(data) {
  return request({
    url: '/tile/sale/order',
    method: 'post',
    data: data
  })
}

// 修改销售订单
export function updateOrder(data) {
  return request({
    url: '/tile/sale/order',
    method: 'put',
    data: data
  })
}

// 删除销售订单
export function delOrder(orderId) {
  return request({
    url: '/tile/sale/order/' + orderId,
    method: 'delete'
  })
}

// 审核销售订单
export function auditOrder(orderId) {
  return request({
    url: '/tile/sale/order/audit/' + orderId,
    method: 'put'
  })
}

// 取消销售订单
export function cancelOrder(orderId) {
  return request({
    url: '/tile/sale/order/cancel/' + orderId,
    method: 'put'
  })
}

// 导出销售订单
export function exportOrder(query) {
  return request({
    url: '/tile/sale/order/export',
    method: 'post',
    params: query
  })
}

// 查询销售订单明细列表
export function listOrderDetail(query) {
  return request({
    url: '/tile/sale/order/detail/list',
    method: 'get',
    params: query
  })
}

// 查询销售订单明细详细
export function getOrderDetail(detailId) {
  return request({
    url: '/tile/sale/order/detail/' + detailId,
    method: 'get'
  })
}

// 根据订单ID查询明细列表
export function getOrderDetails(orderId) {
  return request({
    url: '/tile/sale/order/detail/order/' + orderId,
    method: 'get'
  })
}

// 新增销售订单明细
export function addOrderDetail(data) {
  return request({
    url: '/tile/sale/order/detail',
    method: 'post',
    data: data
  })
}

// 修改销售订单明细
export function updateOrderDetail(data) {
  return request({
    url: '/tile/sale/order/detail',
    method: 'put',
    data: data
  })
}

// 删除销售订单明细
export function delOrderDetail(detailId) {
  return request({
    url: '/tile/sale/order/detail/' + detailId,
    method: 'delete'
  })
}
