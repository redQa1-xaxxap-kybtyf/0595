package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.domain.TileStockInDetail;
import com.ruoyi.system.tile.mapper.TileStockInDetailMapper;
import com.ruoyi.system.tile.service.ITileStockInDetailService;

/**
 * 入库单明细Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileStockInDetailServiceImpl implements ITileStockInDetailService 
{
    @Autowired
    private TileStockInDetailMapper tileStockInDetailMapper;

    /**
     * 查询入库单明细
     * 
     * @param detailId 入库单明细主键
     * @return 入库单明细
     */
    @Override
    public TileStockInDetail selectTileStockInDetailByDetailId(Long detailId)
    {
        return tileStockInDetailMapper.selectTileStockInDetailByDetailId(detailId);
    }

    /**
     * 查询入库单明细列表
     * 
     * @param tileStockInDetail 入库单明细
     * @return 入库单明细
     */
    @Override
    public List<TileStockInDetail> selectTileStockInDetailList(TileStockInDetail tileStockInDetail)
    {
        return tileStockInDetailMapper.selectTileStockInDetailList(tileStockInDetail);
    }

    /**
     * 根据入库单ID查询明细
     * 
     * @param inId 入库单ID
     * @return 入库单明细集合
     */
    @Override
    public List<TileStockInDetail> selectTileStockInDetailByInId(Long inId)
    {
        return tileStockInDetailMapper.selectTileStockInDetailByInId(inId);
    }

    /**
     * 新增入库单明细
     * 
     * @param tileStockInDetail 入库单明细
     * @return 结果
     */
    @Override
    public int insertTileStockInDetail(TileStockInDetail tileStockInDetail)
    {
        return tileStockInDetailMapper.insertTileStockInDetail(tileStockInDetail);
    }

    /**
     * 修改入库单明细
     * 
     * @param tileStockInDetail 入库单明细
     * @return 结果
     */
    @Override
    public int updateTileStockInDetail(TileStockInDetail tileStockInDetail)
    {
        return tileStockInDetailMapper.updateTileStockInDetail(tileStockInDetail);
    }

    /**
     * 批量删除入库单明细
     * 
     * @param detailIds 需要删除的入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteTileStockInDetailByDetailIds(Long[] detailIds)
    {
        return tileStockInDetailMapper.deleteTileStockInDetailByDetailIds(detailIds);
    }

    /**
     * 删除入库单明细信息
     * 
     * @param detailId 入库单明细主键
     * @return 结果
     */
    @Override
    public int deleteTileStockInDetailByDetailId(Long detailId)
    {
        return tileStockInDetailMapper.deleteTileStockInDetailByDetailId(detailId);
    }

    /**
     * 批量新增入库单明细
     * 
     * @param tileStockInDetailList 入库单明细列表
     * @return 结果
     */
    @Override
    public int insertTileStockInDetails(List<TileStockInDetail> tileStockInDetailList)
    {
        return tileStockInDetailMapper.batchInsertTileStockInDetail(tileStockInDetailList);
    }

    /**
     * 通过入库单ID删除入库单明细
     * 
     * @param inId 入库单ID
     * @return 结果
     */
    @Override
    public int deleteTileStockInDetailByInId(Long inId)
    {
        return tileStockInDetailMapper.deleteTileStockInDetailByInId(inId);
    }
}
