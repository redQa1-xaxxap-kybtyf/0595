import request from '@/utils/request'

// 查询用途列表
export function listUsage(query) {
  return request({
    url: '/tile/usage/list',
    method: 'get',
    params: query
  })
}

// 查询用途详细
export function getUsage(usageId) {
  return request({
    url: '/tile/usage/' + usageId,
    method: 'get'
  })
}

// 新增用途
export function addUsage(data) {
  return request({
    url: '/tile/usage',
    method: 'post',
    data: data
  })
}

// 修改用途
export function updateUsage(data) {
  return request({
    url: '/tile/usage',
    method: 'put',
    data: data
  })
}

// 删除用途
export function delUsage(usageId) {
  return request({
    url: '/tile/usage/' + usageId,
    method: 'delete'
  })
}

// 导出用途
export function exportUsage(query) {
  return request({
    url: '/tile/usage/export',
    method: 'post',
    params: query
  })
}
