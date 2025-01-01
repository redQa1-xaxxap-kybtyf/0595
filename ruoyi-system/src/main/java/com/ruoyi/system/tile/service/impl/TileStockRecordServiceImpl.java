package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileStockRecordMapper;
import com.ruoyi.system.tile.domain.TileStockRecord;
import com.ruoyi.system.tile.service.ITileStockRecordService;

/**
 * 库存记录Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileStockRecordServiceImpl implements ITileStockRecordService 
{
    @Autowired
    private TileStockRecordMapper tileStockRecordMapper;

    /**
     * 查询库存记录
     * 
     * @param recordId 库存记录主键
     * @return 库存记录
     */
    @Override
    public TileStockRecord selectTileStockRecordByRecordId(Long recordId)
    {
        return tileStockRecordMapper.selectTileStockRecordByRecordId(recordId);
    }

    /**
     * 查询库存记录列表
     * 
     * @param tileStockRecord 库存记录
     * @return 库存记录
     */
    @Override
    public List<TileStockRecord> selectTileStockRecordList(TileStockRecord tileStockRecord)
    {
        return tileStockRecordMapper.selectTileStockRecordList(tileStockRecord);
    }

    /**
     * 新增库存记录
     * 
     * @param tileStockRecord 库存记录
     * @return 结果
     */
    @Override
    public int insertTileStockRecord(TileStockRecord tileStockRecord)
    {
        return tileStockRecordMapper.insertTileStockRecord(tileStockRecord);
    }

    /**
     * 修改库存记录
     * 
     * @param tileStockRecord 库存记录
     * @return 结果
     */
    @Override
    public int updateTileStockRecord(TileStockRecord tileStockRecord)
    {
        return tileStockRecordMapper.updateTileStockRecord(tileStockRecord);
    }

    /**
     * 批量删除库存记录
     * 
     * @param recordIds 需要删除的库存记录主键
     * @return 结果
     */
    @Override
    public int deleteTileStockRecordByRecordIds(Long[] recordIds)
    {
        return tileStockRecordMapper.deleteTileStockRecordByRecordIds(recordIds);
    }

    /**
     * 删除库存记录信息
     * 
     * @param recordId 库存记录主键
     * @return 结果
     */
    @Override
    public int deleteTileStockRecordByRecordId(Long recordId)
    {
        return tileStockRecordMapper.deleteTileStockRecordByRecordId(recordId);
    }

    /**
     * 清空库存记录
     */
    @Override
    public void cleanTileStockRecord()
    {
        tileStockRecordMapper.cleanTileStockRecord();
    }
}
