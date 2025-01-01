package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStockInDetail;

/**
 * 入库单明细Service接口
 * 
 * @author ruoyi
 */
public interface ITileStockInDetailService 
{
    /**
     * 查询入库单明细
     * 
     * @param detailId 入库单明细主键
     * @return 入库单明细
     */
    public TileStockInDetail selectTileStockInDetailByDetailId(Long detailId);

    /**
     * 查询入库单明细列表
     * 
     * @param tileStockInDetail 入库单明细
     * @return 入库单明细集合
     */
    public List<TileStockInDetail> selectTileStockInDetailList(TileStockInDetail tileStockInDetail);

    /**
     * 根据入库单ID查询明细
     * 
     * @param inId 入库单ID
     * @return 入库单明细集合
     */
    public List<TileStockInDetail> selectTileStockInDetailByInId(Long inId);

    /**
     * 新增入库单明细
     * 
     * @param tileStockInDetail 入库单明细
     * @return 结果
     */
    public int insertTileStockInDetail(TileStockInDetail tileStockInDetail);

    /**
     * 修改入库单明细
     * 
     * @param tileStockInDetail 入库单明细
     * @return 结果
     */
    public int updateTileStockInDetail(TileStockInDetail tileStockInDetail);

    /**
     * 批量删除入库单明细
     * 
     * @param detailIds 需要删除的入库单明细主键集合
     * @return 结果
     */
    public int deleteTileStockInDetailByDetailIds(Long[] detailIds);

    /**
     * 删除入库单明细信息
     * 
     * @param detailId 入库单明细主键
     * @return 结果
     */
    public int deleteTileStockInDetailByDetailId(Long detailId);

    /**
     * 批量新增入库单明细
     * 
     * @param tileStockInDetailList 入库单明细列表
     * @return 结果
     */
    public int insertTileStockInDetails(List<TileStockInDetail> tileStockInDetailList);

    /**
     * 通过入库单ID删除入库单明细
     * 
     * @param inId 入库单ID
     * @return 结果
     */
    public int deleteTileStockInDetailByInId(Long inId);
}
