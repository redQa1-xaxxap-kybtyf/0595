package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.domain.TileSaleOrderDetail;
import com.ruoyi.system.tile.mapper.TileSaleOrderDetailMapper;
import com.ruoyi.system.tile.service.ITileSaleOrderDetailService;

/**
 * 销售订单明细Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileSaleOrderDetailServiceImpl implements ITileSaleOrderDetailService 
{
    @Autowired
    private TileSaleOrderDetailMapper tileSaleOrderDetailMapper;

    /**
     * 查询销售订单明细
     * 
     * @param detailId 销售订单明细主键
     * @return 销售订单明细
     */
    @Override
    public TileSaleOrderDetail selectTileSaleOrderDetailByDetailId(Long detailId)
    {
        return tileSaleOrderDetailMapper.selectTileSaleOrderDetailByDetailId(detailId);
    }

    /**
     * 查询销售订单明细列表
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 销售订单明细
     */
    @Override
    public List<TileSaleOrderDetail> selectTileSaleOrderDetailList(TileSaleOrderDetail tileSaleOrderDetail)
    {
        return tileSaleOrderDetailMapper.selectTileSaleOrderDetailList(tileSaleOrderDetail);
    }

    /**
     * 根据订单ID查询明细
     * 
     * @param orderId 订单ID
     * @return 销售订单明细集合
     */
    @Override
    public List<TileSaleOrderDetail> selectTileSaleOrderDetailByOrderId(Long orderId)
    {
        return tileSaleOrderDetailMapper.selectTileSaleOrderDetailByOrderId(orderId);
    }

    /**
     * 新增销售订单明细
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 结果
     */
    @Override
    public int insertTileSaleOrderDetail(TileSaleOrderDetail tileSaleOrderDetail)
    {
        return tileSaleOrderDetailMapper.insertTileSaleOrderDetail(tileSaleOrderDetail);
    }

    /**
     * 批量新增销售订单明细
     * 
     * @param tileSaleOrderDetails 销售订单明细列表
     * @return 结果
     */
    @Override
    public int insertTileSaleOrderDetails(List<TileSaleOrderDetail> tileSaleOrderDetails)
    {
        return tileSaleOrderDetailMapper.batchInsertTileSaleOrderDetail(tileSaleOrderDetails);
    }

    /**
     * 修改销售订单明细
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 结果
     */
    @Override
    public int updateTileSaleOrderDetail(TileSaleOrderDetail tileSaleOrderDetail)
    {
        return tileSaleOrderDetailMapper.updateTileSaleOrderDetail(tileSaleOrderDetail);
    }

    /**
     * 批量删除销售订单明细
     * 
     * @param detailIds 需要删除的销售订单明细主键
     * @return 结果
     */
    @Override
    public int deleteTileSaleOrderDetailByDetailIds(Long[] detailIds)
    {
        return tileSaleOrderDetailMapper.deleteTileSaleOrderDetailByDetailIds(detailIds);
    }

    /**
     * 删除销售订单明细信息
     * 
     * @param detailId 销售订单明细主键
     * @return 结果
     */
    @Override
    public int deleteTileSaleOrderDetailByDetailId(Long detailId)
    {
        return tileSaleOrderDetailMapper.deleteTileSaleOrderDetailByDetailId(detailId);
    }

    /**
     * 通过订单ID删除销售订单明细
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    public int deleteTileSaleOrderDetailByOrderId(Long orderId)
    {
        return tileSaleOrderDetailMapper.deleteTileSaleOrderDetailByOrderId(orderId);
    }
}
