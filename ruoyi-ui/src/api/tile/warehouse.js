import request from '@/utils/request'

// 查询仓库列表
export function listWarehouse(query) {
  return request({
    url: '/tile/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库详细
export function getWarehouse(warehouseId) {
  return request({
    url: '/tile/warehouse/' + warehouseId,
    method: 'get'
  })
}

// 新增仓库
export function addWarehouse(data) {
  return request({
    url: '/tile/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库
export function updateWarehouse(data) {
  return request({
    url: '/tile/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库
export function delWarehouse(warehouseId) {
  return request({
    url: '/tile/warehouse/' + warehouseId,
    method: 'delete'
  })
}

// 查询仓库货位列表
export function listWarehousePosition(warehouseId) {
  return request({
    url: '/tile/location/list',
    method: 'get',
    params: {
      warehouseId: warehouseId
    }
  })
}
