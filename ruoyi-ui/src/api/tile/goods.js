import request from '@/utils/request'

// 查询瓷砖商品列表
export function listGoods(query) {
  return request({
    url: '/tile/goods/list',
    method: 'get',
    params: query
  })
}

// 查询瓷砖商品详细
export function getGoods(goodsId) {
  return request({
    url: '/tile/goods/' + goodsId,
    method: 'get'
  })
}

// 新增瓷砖商品
export function addGoods(data) {
  return request({
    url: '/tile/goods',
    method: 'post',
    data: data
  })
}

// 修改瓷砖商品
export function updateGoods(data) {
  return request({
    url: '/tile/goods',
    method: 'put',
    data: data
  })
}

// 删除瓷砖商品
export function delGoods(goodsIds) {
  return request({
    url: '/tile/goods/' + goodsIds,
    method: 'delete'
  })
}

// 导出瓷砖商品
export function exportGoods(query) {
  return request({
    url: '/tile/goods/export',
    method: 'get',
    params: query
  })
}

// 更新商品状态
export function updateGoodsStatus(data) {
  return request({
    url: '/tile/goods/status',
    method: 'put',
    data: data
  })
}

// 更新商品库存
export function updateGoodsStock(goodsId, stock) {
  return request({
    url: '/tile/goods/stock/' + goodsId + '/' + stock,
    method: 'put'
  })
}

// 更新商品销量
export function updateGoodsSales(goodsId, sales) {
  return request({
    url: '/tile/goods/sales/' + goodsId + '/' + sales,
    method: 'put'
  })
}
