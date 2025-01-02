import request from '@/utils/request'

// 查询货位列表
export function listLocation(query) {
  return request({
    url: '/tile/location/list',
    method: 'get',
    params: query
  })
}

// 根据仓库ID查询货位列表
export function listLocationByWarehouseId(warehouseId) {
  return request({
    url: '/tile/location/list/' + warehouseId,
    method: 'get'
  })
}

// 查询货位详细
export function getLocation(locationId) {
  return request({
    url: '/tile/location/' + locationId,
    method: 'get'
  })
}

// 新增货位
export function addLocation(data) {
  return request({
    url: '/tile/location',
    method: 'post',
    data: data
  })
}

// 修改货位
export function updateLocation(data) {
  return request({
    url: '/tile/location',
    method: 'put',
    data: data
  })
}

// 删除货位
export function delLocation(locationId) {
  return request({
    url: '/tile/location/' + locationId,
    method: 'delete'
  })
}

// 导出货位
export function exportLocation(query) {
  return request({
    url: '/tile/location/export',
    method: 'get',
    params: query
  })
}
