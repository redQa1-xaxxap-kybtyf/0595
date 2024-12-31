import request from '@/utils/request'

// 查询规格尺寸列表
export function listSpec(query) {
  return request({
    url: '/tile/spec/list',
    method: 'get',
    params: query
  })
}

// 查询规格尺寸详细
export function getSpec(specId) {
  return request({
    url: '/tile/spec/' + specId,
    method: 'get'
  })
}

// 新增规格尺寸
export function addSpec(data) {
  return request({
    url: '/tile/spec',
    method: 'post',
    data: data
  })
}

// 修改规格尺寸
export function updateSpec(data) {
  return request({
    url: '/tile/spec',
    method: 'put',
    data: data
  })
}

// 删除规格尺寸
export function delSpec(specId) {
  return request({
    url: '/tile/spec/' + specId,
    method: 'delete'
  })
}

// 导出规格尺寸
export function exportSpec(query) {
  return request({
    url: '/tile/spec/export',
    method: 'post',
    params: query
  })
}
