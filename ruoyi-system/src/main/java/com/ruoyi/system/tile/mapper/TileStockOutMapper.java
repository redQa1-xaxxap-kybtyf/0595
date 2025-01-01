package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStockOut;

/**
 * 出库单Mapper接口
 * 
 * @author ruoyi
 */
public interface TileStockOutMapper 
{
    /**
     * 查询出库单
     * 
     * @param outId 出库单主键
     * @return 出库单
     */
    public TileStockOut selectTileStockOutByOutId(Long outId);

    /**
     * 查询出库单列表
     * 
     * @param tileStockOut 出库单
     * @return 出库单集合
     */
    public List<TileStockOut> selectTileStockOutList(TileStockOut tileStockOut);

    /**
     * 新增出库单
     * 
     * @param tileStockOut 出库单
     * @return 结果
     */
    public int insertTileStockOut(TileStockOut tileStockOut);

    /**
     * 修改出库单
     * 
     * @param tileStockOut 出库单
     * @return 结果
     */
    public int updateTileStockOut(TileStockOut tileStockOut);

    /**
     * 删除出库单
     * 
     * @param outId 出库单主键
     * @return 结果
     */
    public int deleteTileStockOutByOutId(Long outId);

    /**
     * 批量删除出库单
     * 
     * @param outIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileStockOutByOutIds(Long[] outIds);
}
