package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileGoods;

/**
 * 瓷砖商品管理Service接口
 * 
 * @author ruoyi
 */
public interface ITileGoodsService
{
    /**
     * 查询瓷砖商品列表
     * 
     * @param goods 瓷砖商品信息
     * @return 瓷砖商品集合
     */
    public List<TileGoods> selectTileGoodsList(TileGoods goods);

    /**
     * 查询瓷砖商品信息
     * 
     * @param goodsId 瓷砖商品ID
     * @return 瓷砖商品信息
     */
    public TileGoods selectTileGoodsById(Long goodsId);

    /**
     * 新增瓷砖商品
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    public int insertTileGoods(TileGoods goods);

    /**
     * 修改瓷砖商品
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    public int updateTileGoods(TileGoods goods);

    /**
     * 删除瓷砖商品信息
     * 
     * @param goodsId 瓷砖商品ID
     * @return 结果
     */
    public int deleteTileGoodsById(Long goodsId);

    /**
     * 批量删除瓷砖商品信息
     * 
     * @param goodsIds 需要删除的瓷砖商品ID数组
     * @return 结果
     */
    public int deleteTileGoodsByIds(Long[] goodsIds);

    /**
     * 校验商品编号是否唯一
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    public boolean checkGoodsCodeUnique(TileGoods goods);

    /**
     * 更新商品状态
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    public int updateTileGoodsStatus(TileGoods goods);

    /**
     * 更新商品库存
     * 
     * @param goodsId 商品ID
     * @param stock 库存数量
     * @return 结果
     */
    public int updateTileGoodsStock(Long goodsId, Integer stock);

    /**
     * 更新商品销量
     * 
     * @param goodsId 商品ID
     * @param sales 销量
     * @return 结果
     */
    public int updateTileGoodsSales(Long goodsId, Integer sales);
}
