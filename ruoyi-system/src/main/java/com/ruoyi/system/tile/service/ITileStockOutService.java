package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStockOut;

/**
 * 出库单Service接口
 * 
 * @author ruoyi
 */
public interface ITileStockOutService 
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
     * 批量删除出库单
     * 
     * @param outIds 需要删除的出库单主键集合
     * @return 结果
     */
    public int deleteTileStockOutByOutIds(Long[] outIds);

    /**
     * 删除出库单信息
     * 
     * @param outId 出库单主键
     * @return 结果
     */
    public int deleteTileStockOutByOutId(Long outId);

    /**
     * 提交出库单
     * 
     * @param outId 出库单ID
     * @return 结果
     */
    public int submitTileStockOut(Long outId);

    /**
     * 取消出库单
     * 
     * @param outId 出库单ID
     * @return 结果
     */
    public int cancelTileStockOut(Long outId);
}
