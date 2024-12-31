import request from '@/utils/request'

// 查询表面处理列表
export function listSurface(query) {
  return request({
    url: '/tile/surface/list',
    method: 'get',
    params: query
  })
}

// 查询表面处理详细
export function getSurface(surfaceId) {
  return request({
    url: '/tile/surface/' + surfaceId,
    method: 'get'
  })
}

// 新增表面处理
export function addSurface(data) {
  return request({
    url: '/tile/surface',
    method: 'post',
    data: data
  })
}

// 修改表面处理
export function updateSurface(data) {
  return request({
    url: '/tile/surface',
    method: 'put',
    data: data
  })
}

// 删除表面处理
export function delSurface(surfaceId) {
  return request({
    url: '/tile/surface/' + surfaceId,
    method: 'delete'
  })
}

// 导出表面处理
export function exportSurface(query) {
  return request({
    url: '/tile/surface/export',
    method: 'post',
    params: query
  })
}
