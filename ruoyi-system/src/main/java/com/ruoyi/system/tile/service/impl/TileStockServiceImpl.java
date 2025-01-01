package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.tile.mapper.TileStockMapper;
import com.ruoyi.system.tile.domain.TileStock;
import com.ruoyi.system.tile.service.ITileStockService;

/**
 * 库存管理Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileStockServiceImpl implements ITileStockService 
{
    @Autowired
    private TileStockMapper tileStockMapper;

    /**
     * 查询库存管理
     * 
     * @param stockId 库存管理主键
     * @return 库存管理
     */
    @Override
    public TileStock selectTileStockByStockId(Long stockId)
    {
        return tileStockMapper.selectTileStockByStockId(stockId);
    }

    /**
     * 查询库存管理列表
     * 
     * @param tileStock 库存管理
     * @return 库存管理
     */
    @Override
    public List<TileStock> selectTileStockList(TileStock tileStock)
    {
        return tileStockMapper.selectTileStockList(tileStock);
    }

    /**
     * 新增库存管理
     * 
     * @param tileStock 库存管理
     * @return 结果
     */
    @Override
    public int insertTileStock(TileStock tileStock)
    {
        return tileStockMapper.insertTileStock(tileStock);
    }

    /**
     * 修改库存管理
     * 
     * @param tileStock 库存管理
     * @return 结果
     */
    @Override
    public int updateTileStock(TileStock tileStock)
    {
        return tileStockMapper.updateTileStock(tileStock);
    }

    /**
     * 批量删除库存管理
     * 
     * @param stockIds 需要删除的库存管理主键
     * @return 结果
     */
    @Override
    public int deleteTileStockByStockIds(Long[] stockIds)
    {
        return tileStockMapper.deleteTileStockByStockIds(stockIds);
    }

    /**
     * 删除库存管理信息
     * 
     * @param stockId 库存管理主键
     * @return 结果
     */
    @Override
    public int deleteTileStockByStockId(Long stockId)
    {
        return tileStockMapper.deleteTileStockByStockId(stockId);
    }

    /**
     * 增加库存
     * 
     * @param goodsId 商品ID
     * @param warehouseId 仓库ID
     * @param quantity 增加数量
     * @return 结果
     */
    @Override
    public int addStock(Long goodsId, Long warehouseId, Integer quantity)
    {
        // 查询是否存在库存记录
        TileStock stock = new TileStock();
        stock.setGoodsId(goodsId);
        stock.setWarehouseId(warehouseId);
        List<TileStock> stocks = tileStockMapper.selectTileStockList(stock);
        
        if (stocks != null && !stocks.isEmpty())
        {
            // 存在则更新
            TileStock existStock = stocks.get(0);
            existStock.setQuantity(existStock.getQuantity() + quantity);
            return tileStockMapper.updateTileStock(existStock);
        }
        else
        {
            // 不存在则新增
            stock.setQuantity(quantity);
            return tileStockMapper.insertTileStock(stock);
        }
    }

    /**
     * 减少库存
     * 
     * @param goodsId 商品ID
     * @param warehouseId 仓库ID
     * @param quantity 数量
     * @return 结果
     */
    @Override
    public int subtractStock(Long goodsId, Long warehouseId, Integer quantity)
    {
        // 查询库存记录
        TileStock stock = selectTileStockByGoodsIdAndWarehouseId(goodsId, warehouseId);
        if (stock == null)
        {
            throw new RuntimeException("库存记录不存在");
        }
        if (stock.getQuantity() < quantity)
        {
            throw new RuntimeException("库存不足");
        }
        // 更新库存
        stock.setQuantity(stock.getQuantity() - quantity);
        stock.setUpdateTime(DateUtils.getNowDate());
        return updateTileStock(stock);
    }

    /**
     * 根据商品ID和仓库ID查询库存记录
     * 
     * @param goodsId 商品ID
     * @param warehouseId 仓库ID
     * @return 库存记录
     */
    private TileStock selectTileStockByGoodsIdAndWarehouseId(Long goodsId, Long warehouseId)
    {
        TileStock stock = new TileStock();
        stock.setGoodsId(goodsId);
        stock.setWarehouseId(warehouseId);
        List<TileStock> stocks = tileStockMapper.selectTileStockList(stock);
        if (stocks != null && !stocks.isEmpty())
        {
            return stocks.get(0);
        }
        return null;
    }
}
