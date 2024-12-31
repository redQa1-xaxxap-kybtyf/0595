import request from '@/utils/request'

// 查询图案列表
export function listPattern(query) {
  return request({
    url: '/tile/pattern/list',
    method: 'get',
    params: query
  })
}

// 查询图案详细
export function getPattern(patternId) {
  return request({
    url: '/tile/pattern/' + patternId,
    method: 'get'
  })
}

// 新增图案
export function addPattern(data) {
  return request({
    url: '/tile/pattern',
    method: 'post',
    data: data
  })
}

// 修改图案
export function updatePattern(data) {
  return request({
    url: '/tile/pattern',
    method: 'put',
    data: data
  })
}

// 删除图案
export function delPattern(patternId) {
  return request({
    url: '/tile/pattern/' + patternId,
    method: 'delete'
  })
}

// 导出图案
export function exportPattern(query) {
  return request({
    url: '/tile/pattern/export',
    method: 'post',
    params: query
  })
}
