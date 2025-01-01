package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.tile.domain.TileStockIn;
import com.ruoyi.system.tile.domain.TileStockInDetail;
import com.ruoyi.system.tile.domain.TileStockRecord;
import com.ruoyi.system.tile.mapper.TileStockInMapper;
import com.ruoyi.system.tile.service.ITileStockInService;
import com.ruoyi.system.tile.service.ITileStockInDetailService;
import com.ruoyi.system.tile.service.ITileStockService;
import com.ruoyi.system.tile.service.ITileStockRecordService;

/**
 * 入库单Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileStockInServiceImpl implements ITileStockInService 
{
    @Autowired
    private TileStockInMapper tileStockInMapper;

    @Autowired
    private ITileStockInDetailService tileStockInDetailService;

    @Autowired
    private ITileStockService tileStockService;

    @Autowired
    private ITileStockRecordService tileStockRecordService;

    /**
     * 查询入库单
     * 
     * @param inId 入库单主键
     * @return 入库单
     */
    @Override
    public TileStockIn selectTileStockInByInId(Long inId)
    {
        TileStockIn tileStockIn = tileStockInMapper.selectTileStockInByInId(inId);
        if (tileStockIn != null)
        {
            List<TileStockInDetail> details = tileStockInDetailService.selectTileStockInDetailByInId(inId);
            tileStockIn.setDetails(details);
        }
        return tileStockIn;
    }

    /**
     * 查询入库单列表
     * 
     * @param tileStockIn 入库单
     * @return 入库单
     */
    @Override
    public List<TileStockIn> selectTileStockInList(TileStockIn tileStockIn)
    {
        return tileStockInMapper.selectTileStockInList(tileStockIn);
    }

    /**
     * 新增入库单
     * 
     * @param tileStockIn 入库单
     * @return 结果
     */
    @Override
    @Transactional
    public int insertTileStockIn(TileStockIn tileStockIn)
    {
        tileStockIn.setCreateTime(DateUtils.getNowDate());
        tileStockIn.setCreateBy(SecurityUtils.getUsername());
        // 生成入库单号
        String inCode = generateInCode();
        tileStockIn.setInCode(inCode);
        // 设置初始状态为待入库
        tileStockIn.setStatus("1");
        int rows = tileStockInMapper.insertTileStockIn(tileStockIn);
        if (rows > 0)
        {
            // 新增入库单明细
            if (StringUtils.isNotNull(tileStockIn.getDetails()))
            {
                List<TileStockInDetail> details = tileStockIn.getDetails();
                for (TileStockInDetail detail : details)
                {
                    detail.setInId(tileStockIn.getInId());
                }
                tileStockInDetailService.insertTileStockInDetails(details);
            }
        }
        return rows;
    }

    /**
     * 修改入库单
     * 
     * @param tileStockIn 入库单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTileStockIn(TileStockIn tileStockIn)
    {
        tileStockIn.setUpdateTime(DateUtils.getNowDate());
        tileStockIn.setUpdateBy(SecurityUtils.getUsername());
        // 删除原有明细
        tileStockInDetailService.deleteTileStockInDetailByInId(tileStockIn.getInId());
        // 新增明细
        if (StringUtils.isNotNull(tileStockIn.getDetails()))
        {
            List<TileStockInDetail> details = tileStockIn.getDetails();
            for (TileStockInDetail detail : details)
            {
                detail.setInId(tileStockIn.getInId());
            }
            tileStockInDetailService.insertTileStockInDetails(details);
        }
        return tileStockInMapper.updateTileStockIn(tileStockIn);
    }

    /**
     * 批量删除入库单
     * 
     * @param inIds 需要删除的入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTileStockInByInIds(Long[] inIds)
    {
        for (Long inId : inIds)
        {
            tileStockInDetailService.deleteTileStockInDetailByInId(inId);
        }
        return tileStockInMapper.deleteTileStockInByInIds(inIds);
    }

    /**
     * 删除入库单信息
     * 
     * @param inId 入库单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTileStockInByInId(Long inId)
    {
        tileStockInDetailService.deleteTileStockInDetailByInId(inId);
        return tileStockInMapper.deleteTileStockInByInId(inId);
    }

    /**
     * 提交入库单
     * 
     * @param inId 入库单ID
     * @return 结果
     */
    @Transactional
    @Override
    public int submitTileStockIn(Long inId)
    {
        // 查询入库单
        TileStockIn stockIn = selectTileStockInByInId(inId);
        if (stockIn == null)
        {
            throw new RuntimeException("入库单不存在");
        }
        if (!"1".equals(stockIn.getStatus()))
        {
            throw new RuntimeException("入库单状态不正确");
        }
        // 更新库存
        List<TileStockInDetail> details = stockIn.getDetails();
        for (TileStockInDetail detail : details)
        {
            tileStockService.addStock(detail.getGoodsId(), stockIn.getWarehouseId(), Long.valueOf(detail.getQuantity()));
            
            // 添加库存记录
            TileStockRecord record = new TileStockRecord();
            record.setOperType("1"); // 入库
            record.setGoodsId(detail.getGoodsId());
            record.setWarehouseId(stockIn.getWarehouseId());
            record.setQuantity(detail.getQuantity());
            record.setSourceId(stockIn.getInId());
            record.setSourceType("1"); // 入库单
            record.setSourceCode(stockIn.getInCode());
            record.setOperTime(DateUtils.getTime());
            record.setOperBy(SecurityUtils.getUsername());
            record.setRemark(stockIn.getRemark());
            tileStockRecordService.insertTileStockRecord(record);
        }
        // 更新入库单状态为已入库
        TileStockIn updateStockIn = new TileStockIn();
        updateStockIn.setInId(inId);
        updateStockIn.setStatus("2");
        updateStockIn.setUpdateTime(DateUtils.getNowDate());
        updateStockIn.setUpdateBy(SecurityUtils.getUsername());
        return tileStockInMapper.updateTileStockIn(updateStockIn);
    }

    /**
     * 取消入库单
     * 
     * @param inId 入库单ID
     * @return 结果
     */
    @Override
    public int cancelTileStockIn(Long inId)
    {
        // 查询入库单
        TileStockIn stockIn = selectTileStockInByInId(inId);
        if (stockIn == null)
        {
            throw new RuntimeException("入库单不存在");
        }
        if (!"1".equals(stockIn.getStatus()))
        {
            throw new RuntimeException("入库单状态不正确");
        }
        // 更新入库单状态为已取消
        TileStockIn updateStockIn = new TileStockIn();
        updateStockIn.setInId(inId);
        updateStockIn.setStatus("3");
        updateStockIn.setUpdateTime(DateUtils.getNowDate());
        updateStockIn.setUpdateBy(SecurityUtils.getUsername());
        return tileStockInMapper.updateTileStockIn(updateStockIn);
    }

    /**
     * 生成入库单号
     * 格式：IN + yyyyMMdd + 4位流水号
     */
    private String generateInCode()
    {
        return "IN" + DateUtils.dateTimeNow() + StringUtils.padl(1, 4);
    }
}
