package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.tile.domain.TileSaleOrder;
import com.ruoyi.system.tile.domain.TileSaleOrderDetail;
import com.ruoyi.system.tile.mapper.TileSaleOrderMapper;
import com.ruoyi.system.tile.service.ITileSaleOrderService;
import com.ruoyi.system.tile.service.ITileSaleOrderDetailService;
import com.ruoyi.system.tile.service.ITileStockService;

/**
 * 销售订单Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileSaleOrderServiceImpl implements ITileSaleOrderService
{
    @Autowired
    private TileSaleOrderMapper tileSaleOrderMapper;

    @Autowired
    private ITileSaleOrderDetailService tileSaleOrderDetailService;

    @Autowired
    private ITileStockService tileStockService;

    /**
     * 查询销售订单
     * 
     * @param orderId 销售订单主键
     * @return 销售订单
     */
    @Override
    public TileSaleOrder selectTileSaleOrderByOrderId(Long orderId)
    {
        return tileSaleOrderMapper.selectTileSaleOrderByOrderId(orderId);
    }

    /**
     * 查询销售订单列表
     * 
     * @param tileSaleOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<TileSaleOrder> selectTileSaleOrderList(TileSaleOrder tileSaleOrder)
    {
        return tileSaleOrderMapper.selectTileSaleOrderList(tileSaleOrder);
    }

    /**
     * 新增销售订单
     * 
     * @param tileSaleOrder 销售订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertTileSaleOrder(TileSaleOrder tileSaleOrder)
    {
        tileSaleOrder.setCreateTime(DateUtils.getNowDate());
        tileSaleOrder.setCreateBy(SecurityUtils.getUsername());
        tileSaleOrder.setOrderNo(generateOrderNo());
        int rows = tileSaleOrderMapper.insertTileSaleOrder(tileSaleOrder);
        if (tileSaleOrder.getDetails() != null && !tileSaleOrder.getDetails().isEmpty())
        {
            for (TileSaleOrderDetail detail : tileSaleOrder.getDetails())
            {
                detail.setOrderId(tileSaleOrder.getOrderId());
            }
            tileSaleOrderDetailService.insertTileSaleOrderDetails(tileSaleOrder.getDetails());
        }
        return rows;
    }

    /**
     * 修改销售订单
     * 
     * @param tileSaleOrder 销售订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateTileSaleOrder(TileSaleOrder tileSaleOrder)
    {
        tileSaleOrder.setUpdateTime(DateUtils.getNowDate());
        tileSaleOrder.setUpdateBy(SecurityUtils.getUsername());
        tileSaleOrderDetailService.deleteTileSaleOrderDetailByOrderId(tileSaleOrder.getOrderId());
        if (tileSaleOrder.getDetails() != null && !tileSaleOrder.getDetails().isEmpty())
        {
            for (TileSaleOrderDetail detail : tileSaleOrder.getDetails())
            {
                detail.setOrderId(tileSaleOrder.getOrderId());
            }
            tileSaleOrderDetailService.insertTileSaleOrderDetails(tileSaleOrder.getDetails());
        }
        return tileSaleOrderMapper.updateTileSaleOrder(tileSaleOrder);
    }

    /**
     * 批量删除销售订单
     * 
     * @param orderIds 需要删除的销售订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTileSaleOrderByOrderIds(Long[] orderIds)
    {
        for (Long orderId : orderIds)
        {
            tileSaleOrderDetailService.deleteTileSaleOrderDetailByOrderId(orderId);
        }
        return tileSaleOrderMapper.deleteTileSaleOrderByOrderIds(orderIds);
    }

    /**
     * 删除销售订单信息
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteTileSaleOrderByOrderId(Long orderId)
    {
        tileSaleOrderDetailService.deleteTileSaleOrderDetailByOrderId(orderId);
        return tileSaleOrderMapper.deleteTileSaleOrderByOrderId(orderId);
    }

    /**
     * 审核销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int auditTileSaleOrder(Long orderId)
    {
        TileSaleOrder order = tileSaleOrderMapper.selectTileSaleOrderByOrderId(orderId);
        if (order == null)
        {
            throw new RuntimeException("销售订单不存在");
        }
        if (order.getOrderStatus() != null && !TileSaleOrder.STATUS_PENDING.equals(order.getOrderStatus()))
        {
            throw new RuntimeException("销售订单状态不正确");
        }
        List<TileSaleOrderDetail> details = tileSaleOrderDetailService.selectTileSaleOrderDetailByOrderId(orderId);
        if (details == null || details.isEmpty())
        {
            throw new RuntimeException("销售订单明细不存在");
        }
        // 扣减库存
        for (TileSaleOrderDetail detail : details)
        {
            tileStockService.subtractStock(detail.getProductId(), detail.getWarehouseId(), detail.getQuantity().longValue());
        }
        // 更新订单状态为已审核
        order.setOrderStatus(TileSaleOrder.STATUS_APPROVED);
        order.setAuditTime(DateUtils.getNowDate());
        order.setAuditBy(SecurityUtils.getUsername());
        return tileSaleOrderMapper.updateTileSaleOrder(order);
    }

    /**
     * 取消销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int cancelTileSaleOrder(Long orderId)
    {
        TileSaleOrder order = tileSaleOrderMapper.selectTileSaleOrderByOrderId(orderId);
        if (order == null)
        {
            throw new RuntimeException("销售订单不存在");
        }
        if (order.getOrderStatus() != null && !TileSaleOrder.STATUS_PENDING.equals(order.getOrderStatus()))
        {
            throw new RuntimeException("销售订单状态不正确");
        }
        // 更新订单状态为已取消
        order.setOrderStatus(TileSaleOrder.STATUS_SHIPPED);
        order.setUpdateTime(DateUtils.getNowDate());
        order.setUpdateBy(SecurityUtils.getUsername());
        return tileSaleOrderMapper.updateTileSaleOrder(order);
    }

    /**
     * 生成订单编号
     * 
     * @return 订单编号
     */
    private String generateOrderNo()
    {
        return "SO" + DateUtils.dateTimeNow() + String.format("%04d", (int)(Math.random() * 10000));
    }
}
