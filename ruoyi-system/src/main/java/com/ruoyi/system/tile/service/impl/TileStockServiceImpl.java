package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.system.tile.mapper.TileStockMapper;
import com.ruoyi.system.tile.domain.TileStock;
import com.ruoyi.system.tile.service.ITileStockService;

/**
 * 库存管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
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
     * @param batchNo 批次号
     * @param locationId 货位ID
     * @return 结果
     */
    @Override
    public int addStock(Long goodsId, Long warehouseId, Long quantity, String batchNo, Long locationId)
    {
        // 查询是否已存在该商品的库存记录（相同商品、仓库、批次号和货位）
        TileStock param = new TileStock();
        param.setGoodsId(goodsId);
        param.setWarehouseId(warehouseId);
        param.setBatchNo(batchNo);
        param.setPositionId(locationId);
        param.setStatus("0"); // 正常状态
        List<TileStock> stocks = tileStockMapper.selectTileStockList(param);
        
        if (stocks != null && !stocks.isEmpty())
        {
            // 如果存在，则更新库存数量
            TileStock stock = stocks.get(0);
            stock.setQuantity(stock.getQuantity() + quantity);
            return updateTileStock(stock);
        }
        else
        {
            // 如果不存在，则新增库存记录
            TileStock stock = new TileStock();
            stock.setGoodsId(goodsId);
            stock.setWarehouseId(warehouseId);
            stock.setQuantity(quantity);
            stock.setBatchNo(batchNo);
            stock.setPositionId(locationId);
            stock.setStatus("0"); // 正常状态
            return insertTileStock(stock);
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
    public int subtractStock(Long goodsId, Long warehouseId, Long quantity)
    {
        TileStock param = new TileStock();
        param.setGoodsId(goodsId);
        param.setWarehouseId(warehouseId);
        param.setStatus("0"); // 正常状态

        List<TileStock> stocks = tileStockMapper.selectTileStockList(param);

        if (stocks != null && !stocks.isEmpty())
        {
            // 存在则更新
            TileStock existStock = stocks.get(0);
            if (existStock.getQuantity() < quantity)
            {
                throw new ServiceException("库存不足");
            }
            existStock.setQuantity(existStock.getQuantity() - quantity);
            return tileStockMapper.updateTileStock(existStock);
        }
        else
        {
            throw new ServiceException("库存不存在");
        }
    }
}
