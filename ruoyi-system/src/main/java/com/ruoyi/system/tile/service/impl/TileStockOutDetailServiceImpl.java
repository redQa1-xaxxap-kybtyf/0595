package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileStockOutDetailMapper;
import com.ruoyi.system.tile.domain.TileStockOutDetail;
import com.ruoyi.system.tile.service.ITileStockOutDetailService;

/**
 * 出库单明细Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileStockOutDetailServiceImpl implements ITileStockOutDetailService 
{
    @Autowired
    private TileStockOutDetailMapper tileStockOutDetailMapper;

    /**
     * 查询出库单明细
     * 
     * @param detailId 出库单明细主键
     * @return 出库单明细
     */
    @Override
    public TileStockOutDetail selectTileStockOutDetailByDetailId(Long detailId)
    {
        return tileStockOutDetailMapper.selectTileStockOutDetailByDetailId(detailId);
    }

    /**
     * 查询出库单明细列表
     * 
     * @param tileStockOutDetail 出库单明细
     * @return 出库单明细
     */
    @Override
    public List<TileStockOutDetail> selectTileStockOutDetailList(TileStockOutDetail tileStockOutDetail)
    {
        return tileStockOutDetailMapper.selectTileStockOutDetailList(tileStockOutDetail);
    }

    /**
     * 根据出库单ID查询明细
     * 
     * @param outId 出库单ID
     * @return 出库单明细集合
     */
    @Override
    public List<TileStockOutDetail> selectTileStockOutDetailByOutId(Long outId)
    {
        return tileStockOutDetailMapper.selectTileStockOutDetailByOutId(outId);
    }

    /**
     * 新增出库单明细
     * 
     * @param tileStockOutDetail 出库单明细
     * @return 结果
     */
    @Override
    public int insertTileStockOutDetail(TileStockOutDetail tileStockOutDetail)
    {
        return tileStockOutDetailMapper.insertTileStockOutDetail(tileStockOutDetail);
    }

    /**
     * 修改出库单明细
     * 
     * @param tileStockOutDetail 出库单明细
     * @return 结果
     */
    @Override
    public int updateTileStockOutDetail(TileStockOutDetail tileStockOutDetail)
    {
        return tileStockOutDetailMapper.updateTileStockOutDetail(tileStockOutDetail);
    }

    /**
     * 批量删除出库单明细
     * 
     * @param detailIds 需要删除的出库单明细主键
     * @return 结果
     */
    @Override
    public int deleteTileStockOutDetailByDetailIds(Long[] detailIds)
    {
        return tileStockOutDetailMapper.deleteTileStockOutDetailByDetailIds(detailIds);
    }

    /**
     * 删除出库单明细信息
     * 
     * @param detailId 出库单明细主键
     * @return 结果
     */
    @Override
    public int deleteTileStockOutDetailByDetailId(Long detailId)
    {
        return tileStockOutDetailMapper.deleteTileStockOutDetailByDetailId(detailId);
    }

    /**
     * 批量新增出库单明细
     * 
     * @param tileStockOutDetailList 出库单明细列表
     * @return 结果
     */
    @Override
    public int batchInsertTileStockOutDetail(List<TileStockOutDetail> tileStockOutDetailList)
    {
        return tileStockOutDetailMapper.batchInsertTileStockOutDetail(tileStockOutDetailList);
    }

    /**
     * 通过出库单ID删除出库单明细
     * 
     * @param outId 出库单ID
     * @return 结果
     */
    @Override
    public int deleteTileStockOutDetailByOutId(Long outId)
    {
        return tileStockOutDetailMapper.deleteTileStockOutDetailByOutId(outId);
    }
}
