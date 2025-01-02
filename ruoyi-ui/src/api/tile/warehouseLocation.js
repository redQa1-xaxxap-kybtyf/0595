import request from '@/utils/request'

// 查询货位列表
export function listWarehouseLocation(query) {
  return request({
    url: '/tile/warehouseLocation/list',
    method: 'get',
    params: query
  })
}

// 根据仓库ID查询货位列表
export function listWarehouseLocationByWarehouseId(warehouseId) {
  return request({
    url: '/tile/warehouseLocation/list/' + warehouseId,
    method: 'get'
  })
}

// 查询货位详细
export function getWarehouseLocation(locationId) {
  return request({
    url: '/tile/warehouseLocation/' + locationId,
    method: 'get'
  })
}

// 新增货位
export function addWarehouseLocation(data) {
  return request({
    url: '/tile/warehouseLocation',
    method: 'post',
    data: data
  })
}

// 修改货位
export function updateWarehouseLocation(data) {
  return request({
    url: '/tile/warehouseLocation',
    method: 'put',
    data: data
  })
}

// 删除货位
export function delWarehouseLocation(locationId) {
  return request({
    url: '/tile/warehouseLocation/' + locationId,
    method: 'delete'
  })
}

// 导出货位
export function exportWarehouseLocation(query) {
  return request({
    url: '/tile/warehouseLocation/export',
    method: 'get',
    params: query
  })
}
