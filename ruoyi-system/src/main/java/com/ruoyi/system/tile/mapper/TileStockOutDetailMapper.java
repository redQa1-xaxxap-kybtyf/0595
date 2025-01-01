package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStockOutDetail;

/**
 * 出库单明细Mapper接口
 * 
 * @author ruoyi
 */
public interface TileStockOutDetailMapper 
{
    /**
     * 查询出库单明细
     * 
     * @param detailId 出库单明细主键
     * @return 出库单明细
     */
    public TileStockOutDetail selectTileStockOutDetailByDetailId(Long detailId);

    /**
     * 查询出库单明细列表
     * 
     * @param tileStockOutDetail 出库单明细
     * @return 出库单明细集合
     */
    public List<TileStockOutDetail> selectTileStockOutDetailList(TileStockOutDetail tileStockOutDetail);

    /**
     * 根据出库单ID查询明细
     * 
     * @param outId 出库单ID
     * @return 出库单明细集合
     */
    public List<TileStockOutDetail> selectTileStockOutDetailByOutId(Long outId);

    /**
     * 新增出库单明细
     * 
     * @param tileStockOutDetail 出库单明细
     * @return 结果
     */
    public int insertTileStockOutDetail(TileStockOutDetail tileStockOutDetail);

    /**
     * 修改出库单明细
     * 
     * @param tileStockOutDetail 出库单明细
     * @return 结果
     */
    public int updateTileStockOutDetail(TileStockOutDetail tileStockOutDetail);

    /**
     * 删除出库单明细
     * 
     * @param detailId 出库单明细主键
     * @return 结果
     */
    public int deleteTileStockOutDetailByDetailId(Long detailId);

    /**
     * 批量删除出库单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileStockOutDetailByDetailIds(Long[] detailIds);

    /**
     * 批量新增出库单明细
     * 
     * @param tileStockOutDetailList 出库单明细列表
     * @return 结果
     */
    public int batchInsertTileStockOutDetail(List<TileStockOutDetail> tileStockOutDetailList);

    /**
     * 通过出库单ID删除出库单明细
     * 
     * @param outId 出库单ID
     * @return 结果
     */
    public int deleteTileStockOutDetailByOutId(Long outId);
}
