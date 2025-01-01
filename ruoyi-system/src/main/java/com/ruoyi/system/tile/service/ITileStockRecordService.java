package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileStockRecord;

/**
 * 库存记录Service接口
 * 
 * @author ruoyi
 */
public interface ITileStockRecordService 
{
    /**
     * 查询库存记录
     * 
     * @param recordId 库存记录主键
     * @return 库存记录
     */
    public TileStockRecord selectTileStockRecordByRecordId(Long recordId);

    /**
     * 查询库存记录列表
     * 
     * @param tileStockRecord 库存记录
     * @return 库存记录集合
     */
    public List<TileStockRecord> selectTileStockRecordList(TileStockRecord tileStockRecord);

    /**
     * 新增库存记录
     * 
     * @param tileStockRecord 库存记录
     * @return 结果
     */
    public int insertTileStockRecord(TileStockRecord tileStockRecord);

    /**
     * 修改库存记录
     * 
     * @param tileStockRecord 库存记录
     * @return 结果
     */
    public int updateTileStockRecord(TileStockRecord tileStockRecord);

    /**
     * 批量删除库存记录
     * 
     * @param recordIds 需要删除的库存记录主键集合
     * @return 结果
     */
    public int deleteTileStockRecordByRecordIds(Long[] recordIds);

    /**
     * 删除库存记录信息
     * 
     * @param recordId 库存记录主键
     * @return 结果
     */
    public int deleteTileStockRecordByRecordId(Long recordId);

    /**
     * 清空库存记录
     */
    public void cleanTileStockRecord();
}
