package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStock;

/**
 * 库存管理Service接口
 * 
 * @author ruoyi
 */
public interface ITileStockService 
{
    /**
     * 查询库存管理
     * 
     * @param stockId 库存管理主键
     * @return 库存管理
     */
    public TileStock selectTileStockByStockId(Long stockId);

    /**
     * 查询库存管理列表
     * 
     * @param tileStock 库存管理
     * @return 库存管理集合
     */
    public List<TileStock> selectTileStockList(TileStock tileStock);

    /**
     * 新增库存管理
     * 
     * @param tileStock 库存管理
     * @return 结果
     */
    public int insertTileStock(TileStock tileStock);

    /**
     * 修改库存管理
     * 
     * @param tileStock 库存管理
     * @return 结果
     */
    public int updateTileStock(TileStock tileStock);

    /**
     * 批量删除库存管理
     * 
     * @param stockIds 需要删除的库存管理主键集合
     * @return 结果
     */
    public int deleteTileStockByStockIds(Long[] stockIds);

    /**
     * 删除库存管理信息
     * 
     * @param stockId 库存管理主键
     * @return 结果
     */
    public int deleteTileStockByStockId(Long stockId);

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
    public int addStock(Long goodsId, Long warehouseId, Long quantity, String batchNo, Long locationId);

    /**
     * 减少库存
     * 
     * @param goodsId 商品ID
     * @param warehouseId 仓库ID
     * @param quantity 数量
     * @return 结果
     */
    public int subtractStock(Long goodsId, Long warehouseId, Long quantity);
}
