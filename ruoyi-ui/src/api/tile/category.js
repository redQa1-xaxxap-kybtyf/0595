import request from '@/utils/request'

// 查询瓷砖分类列表
export function listCategory(query) {
  return request({
    url: '/tile/category/list',
    method: 'get',
    params: query
  })
}

// 查询瓷砖分类详细
export function getCategory(categoryId) {
  return request({
    url: '/tile/category/' + categoryId,
    method: 'get'
  })
}

// 新增瓷砖分类
export function addCategory(data) {
  return request({
    url: '/tile/category',
    method: 'post',
    data: data
  })
}

// 修改瓷砖分类
export function updateCategory(data) {
  return request({
    url: '/tile/category',
    method: 'put',
    data: data
  })
}

// 删除瓷砖分类
export function delCategory(categoryId) {
  return request({
    url: '/tile/category/' + categoryId,
    method: 'delete'
  })
}
