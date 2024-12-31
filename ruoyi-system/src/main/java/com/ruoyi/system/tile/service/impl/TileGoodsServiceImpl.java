package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.tile.domain.TileGoods;
import com.ruoyi.system.tile.mapper.TileGoodsMapper;
import com.ruoyi.system.tile.service.ITileGoodsService;

/**
 * 瓷砖商品管理 服务实现
 * 
 * @author ruoyi
 */
@Service
public class TileGoodsServiceImpl implements ITileGoodsService
{
    @Autowired
    private TileGoodsMapper tileGoodsMapper;

    /**
     * 查询瓷砖商品列表
     * 
     * @param goods 瓷砖商品信息
     * @return 瓷砖商品集合
     */
    @Override
    public List<TileGoods> selectTileGoodsList(TileGoods goods)
    {
        return tileGoodsMapper.selectTileGoodsList(goods);
    }

    /**
     * 查询瓷砖商品信息
     * 
     * @param goodsId 瓷砖商品ID
     * @return 瓷砖商品信息
     */
    @Override
    public TileGoods selectTileGoodsById(Long goodsId)
    {
        return tileGoodsMapper.selectTileGoodsById(goodsId);
    }

    /**
     * 新增瓷砖商品
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTileGoods(TileGoods goods)
    {
        goods.setCreateTime(DateUtils.getNowDate());
        return tileGoodsMapper.insertTileGoods(goods);
    }

    /**
     * 修改瓷砖商品
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTileGoods(TileGoods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return tileGoodsMapper.updateTileGoods(goods);
    }

    /**
     * 删除瓷砖商品信息
     * 
     * @param goodsId 瓷砖商品ID
     * @return 结果
     */
    @Override
    public int deleteTileGoodsById(Long goodsId)
    {
        return tileGoodsMapper.deleteTileGoodsById(goodsId);
    }

    /**
     * 批量删除瓷砖商品信息
     * 
     * @param goodsIds 需要删除的瓷砖商品ID数组
     * @return 结果
     */
    @Override
    public int deleteTileGoodsByIds(Long[] goodsIds)
    {
        return tileGoodsMapper.deleteTileGoodsByIds(goodsIds);
    }

    /**
     * 校验商品编号是否唯一
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    @Override
    public boolean checkGoodsCodeUnique(TileGoods goods)
    {
        Long goodsId = StringUtils.isNull(goods.getGoodsId()) ? -1L : goods.getGoodsId();
        TileGoods info = tileGoodsMapper.checkGoodsCodeUnique(goods.getGoodsCode());
        if (StringUtils.isNotNull(info) && info.getGoodsId().longValue() != goodsId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 更新商品状态
     * 
     * @param goods 瓷砖商品信息
     * @return 结果
     */
    @Override
    public int updateTileGoodsStatus(TileGoods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return tileGoodsMapper.updateTileGoodsStatus(goods);
    }

    /**
     * 更新商品库存
     * 
     * @param goodsId 商品ID
     * @param stock 库存数量
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTileGoodsStock(Long goodsId, Integer stock)
    {
        return tileGoodsMapper.updateTileGoodsStock(goodsId, stock);
    }

    /**
     * 更新商品销量
     * 
     * @param goodsId 商品ID
     * @param sales 销量
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTileGoodsSales(Long goodsId, Integer sales)
    {
        return tileGoodsMapper.updateTileGoodsSales(goodsId, sales);
    }
}
