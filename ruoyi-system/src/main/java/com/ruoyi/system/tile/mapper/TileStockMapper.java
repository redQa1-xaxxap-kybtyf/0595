package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStock;

/**
 * 库存管理Mapper接口
 * 
 * @author ruoyi
 */
public interface TileStockMapper 
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
     * 删除库存管理
     * 
     * @param stockId 库存管理主键
     * @return 结果
     */
    public int deleteTileStockByStockId(Long stockId);

    /**
     * 批量删除库存管理
     * 
     * @param stockIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileStockByStockIds(Long[] stockIds);
}
