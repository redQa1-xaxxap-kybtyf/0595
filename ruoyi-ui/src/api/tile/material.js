import request from '@/utils/request'

// 查询材质列表
export function listMaterial(query) {
  return request({
    url: '/tile/material/list',
    method: 'get',
    params: query
  })
}

// 查询材质详细
export function getMaterial(materialId) {
  return request({
    url: '/tile/material/' + materialId,
    method: 'get'
  })
}

// 新增材质
export function addMaterial(data) {
  return request({
    url: '/tile/material',
    method: 'post',
    data: data
  })
}

// 修改材质
export function updateMaterial(data) {
  return request({
    url: '/tile/material',
    method: 'put',
    data: data
  })
}

// 删除材质
export function delMaterial(materialId) {
  return request({
    url: '/tile/material/' + materialId,
    method: 'delete'
  })
}

// 导出材质
export function exportMaterial(query) {
  return request({
    url: '/tile/material/export',
    method: 'post',
    params: query
  })
}
