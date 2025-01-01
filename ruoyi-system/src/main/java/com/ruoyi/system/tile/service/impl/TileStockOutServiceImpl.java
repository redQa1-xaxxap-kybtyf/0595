package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.tile.domain.TileStockOut;
import com.ruoyi.system.tile.domain.TileStockOutDetail;
import com.ruoyi.system.tile.domain.TileStockRecord;
import com.ruoyi.system.tile.mapper.TileStockOutMapper;
import com.ruoyi.system.tile.service.ITileStockOutService;
import com.ruoyi.system.tile.service.ITileStockOutDetailService;
import com.ruoyi.system.tile.service.ITileStockService;
import com.ruoyi.system.tile.service.ITileStockRecordService;

/**
 * 出库单Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileStockOutServiceImpl implements ITileStockOutService 
{
    @Autowired
    private TileStockOutMapper tileStockOutMapper;

    @Autowired
    private ITileStockOutDetailService tileStockOutDetailService;

    @Autowired
    private ITileStockService tileStockService;

    @Autowired
    private ITileStockRecordService tileStockRecordService;

    /**
     * 查询出库单
     * 
     * @param outId 出库单主键
     * @return 出库单
     */
    @Override
    public TileStockOut selectTileStockOutByOutId(Long outId)
    {
        TileStockOut tileStockOut = tileStockOutMapper.selectTileStockOutByOutId(outId);
        if (tileStockOut != null)
        {
            List<TileStockOutDetail> details = tileStockOutDetailService.selectTileStockOutDetailByOutId(outId);
            tileStockOut.setDetails(details);
        }
        return tileStockOut;
    }

    /**
     * 查询出库单列表
     * 
     * @param tileStockOut 出库单
     * @return 出库单
     */
    @Override
    public List<TileStockOut> selectTileStockOutList(TileStockOut tileStockOut)
    {
        return tileStockOutMapper.selectTileStockOutList(tileStockOut);
    }

    /**
     * 新增出库单
     * 
     * @param tileStockOut 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTileStockOut(TileStockOut tileStockOut)
    {
        tileStockOut.setCreateTime(DateUtils.getNowDate());
        // 生成出库单号
        tileStockOut.setOutCode(generateOutCode());
        // 设置状态为待出库
        tileStockOut.setStatus("1");
        int rows = tileStockOutMapper.insertTileStockOut(tileStockOut);
        // 批量新增出库单明细
        if (StringUtils.isNotNull(tileStockOut.getDetails()))
        {
            List<TileStockOutDetail> details = tileStockOut.getDetails();
            for (TileStockOutDetail detail : details)
            {
                detail.setOutId(tileStockOut.getOutId());
            }
            tileStockOutDetailService.batchInsertTileStockOutDetail(details);
        }
        return rows;
    }

    /**
     * 修改出库单
     * 
     * @param tileStockOut 出库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTileStockOut(TileStockOut tileStockOut)
    {
        tileStockOut.setUpdateTime(DateUtils.getNowDate());
        // 删除原有明细
        tileStockOutDetailService.deleteTileStockOutDetailByOutId(tileStockOut.getOutId());
        // 新增明细
        if (StringUtils.isNotNull(tileStockOut.getDetails()))
        {
            List<TileStockOutDetail> details = tileStockOut.getDetails();
            for (TileStockOutDetail detail : details)
            {
                detail.setOutId(tileStockOut.getOutId());
            }
            tileStockOutDetailService.batchInsertTileStockOutDetail(details);
        }
        return tileStockOutMapper.updateTileStockOut(tileStockOut);
    }

    /**
     * 批量删除出库单
     * 
     * @param outIds 需要删除的出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTileStockOutByOutIds(Long[] outIds)
    {
        for (Long outId : outIds)
        {
            tileStockOutDetailService.deleteTileStockOutDetailByOutId(outId);
        }
        return tileStockOutMapper.deleteTileStockOutByOutIds(outIds);
    }

    /**
     * 删除出库单信息
     * 
     * @param outId 出库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTileStockOutByOutId(Long outId)
    {
        tileStockOutDetailService.deleteTileStockOutDetailByOutId(outId);
        return tileStockOutMapper.deleteTileStockOutByOutId(outId);
    }

    /**
     * 提交出库单
     * 
     * @param outId 出库单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int submitTileStockOut(Long outId)
    {
        // 查询出库单
        TileStockOut stockOut = selectTileStockOutByOutId(outId);
        if (stockOut == null)
        {
            throw new RuntimeException("出库单不存在");
        }
        if (!"1".equals(stockOut.getStatus()))
        {
            throw new RuntimeException("出库单状态不正确");
        }
        // 更新库存
        List<TileStockOutDetail> details = stockOut.getDetails();
        for (TileStockOutDetail detail : details)
        {
            tileStockService.subtractStock(detail.getGoodsId(), stockOut.getWarehouseId(), Long.valueOf(detail.getQuantity()));
            
            // 添加库存记录
            TileStockRecord record = new TileStockRecord();
            record.setOperType("2"); // 出库
            record.setGoodsId(detail.getGoodsId());
            record.setWarehouseId(stockOut.getWarehouseId());
            record.setQuantity(Long.valueOf(detail.getQuantity()));
            record.setSourceId(stockOut.getOutId());
            record.setSourceType("2"); // 出库单
            record.setSourceCode(stockOut.getOutCode());
            record.setOperTime(DateUtils.getTime());
            record.setOperBy(SecurityUtils.getUsername());
            record.setRemark(stockOut.getRemark());
            tileStockRecordService.insertTileStockRecord(record);
        }
        // 更新出库单状态为已出库
        TileStockOut updateStockOut = new TileStockOut();
        updateStockOut.setOutId(outId);
        updateStockOut.setStatus("2");
        updateStockOut.setUpdateTime(DateUtils.getNowDate());
        updateStockOut.setUpdateBy(SecurityUtils.getUsername());
        return tileStockOutMapper.updateTileStockOut(updateStockOut);
    }

    /**
     * 取消出库单
     * 
     * @param outId 出库单ID
     * @return 结果
     */
    @Override
    public int cancelTileStockOut(Long outId)
    {
        // 查询出库单
        TileStockOut stockOut = selectTileStockOutByOutId(outId);
        if (stockOut == null)
        {
            throw new RuntimeException("出库单不存在");
        }
        if (!"1".equals(stockOut.getStatus()))
        {
            throw new RuntimeException("出库单状态不正确");
        }
        // 更新出库单状态为已取消
        TileStockOut updateStockOut = new TileStockOut();
        updateStockOut.setOutId(outId);
        updateStockOut.setStatus("3");
        updateStockOut.setUpdateTime(DateUtils.getNowDate());
        return tileStockOutMapper.updateTileStockOut(updateStockOut);
    }

    /**
     * 生成出库单号
     * 
     * @return 出库单号
     */
    private String generateOutCode()
    {
        return "SO" + DateUtils.dateTimeNow() + StringUtils.padl(1, 4);
    }
}
