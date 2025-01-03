import request from '@/utils/request'

// 查询供应商价格列表
export function listPrice(query) {
  return request({
    url: '/tile/price/list',
    method: 'get',
    params: query
  })
}

// 查询供应商价格详细
export function getPrice(priceId) {
  return request({
    url: '/tile/price/' + priceId,
    method: 'get'
  })
}

// 查询供应商的价格列表
export function getSupplierPrices(supplierId) {
  return request({
    url: '/tile/price/supplier/' + supplierId,
    method: 'get'
  })
}

// 新增供应商价格
export function addPrice(data) {
  return request({
    url: '/tile/price',
    method: 'post',
    data: data
  })
}

// 批量新增供应商价格
export function addPrices(data) {
  return request({
    url: '/tile/price/batch',
    method: 'post',
    data: data
  })
}

// 修改供应商价格
export function updatePrice(data) {
  return request({
    url: '/tile/price',
    method: 'put',
    data: data
  })
}

// 批量修改供应商价格
export function updatePrices(data) {
  return request({
    url: '/tile/price',
    method: 'put',
    data: data
  })
}

// 删除供应商价格
export function delPrice(priceId) {
  return request({
    url: '/tile/price/' + priceId,
    method: 'delete'
  })
}

// 导出供应商价格
export function exportPrice(query) {
  return request({
    url: '/tile/price/export',
    method: 'get',
    params: query
  })
}
