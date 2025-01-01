package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStockIn;

/**
 * 入库单Service接口
 * 
 * @author ruoyi
 */
public interface ITileStockInService 
{
    /**
     * 查询入库单
     * 
     * @param inId 入库单主键
     * @return 入库单
     */
    public TileStockIn selectTileStockInByInId(Long inId);

    /**
     * 查询入库单列表
     * 
     * @param tileStockIn 入库单
     * @return 入库单集合
     */
    public List<TileStockIn> selectTileStockInList(TileStockIn tileStockIn);

    /**
     * 新增入库单
     * 
     * @param tileStockIn 入库单
     * @return 结果
     */
    public int insertTileStockIn(TileStockIn tileStockIn);

    /**
     * 修改入库单
     * 
     * @param tileStockIn 入库单
     * @return 结果
     */
    public int updateTileStockIn(TileStockIn tileStockIn);

    /**
     * 批量删除入库单
     * 
     * @param inIds 需要删除的入库单主键集合
     * @return 结果
     */
    public int deleteTileStockInByInIds(Long[] inIds);

    /**
     * 删除入库单信息
     * 
     * @param inId 入库单主键
     * @return 结果
     */
    public int deleteTileStockInByInId(Long inId);

    /**
     * 提交入库单
     * 
     * @param inId 入库单ID
     * @return 结果
     */
    public int submitTileStockIn(Long inId);

    /**
     * 取消入库单
     * 
     * @param inId 入库单ID
     * @return 结果
     */
    public int cancelTileStockIn(Long inId);
}
