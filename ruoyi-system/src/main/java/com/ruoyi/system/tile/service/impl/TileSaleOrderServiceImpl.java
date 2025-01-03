package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileSaleOrderMapper;
import com.ruoyi.system.tile.domain.TileSaleOrder;
import com.ruoyi.system.tile.service.ITileSaleOrderService;

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
    @Override
    public int insertTileSaleOrder(TileSaleOrder tileSaleOrder)
    {
        return tileSaleOrderMapper.insertTileSaleOrder(tileSaleOrder);
    }

    /**
     * 修改销售订单
     * 
     * @param tileSaleOrder 销售订单
     * @return 结果
     */
    @Override
    public int updateTileSaleOrder(TileSaleOrder tileSaleOrder)
    {
        return tileSaleOrderMapper.updateTileSaleOrder(tileSaleOrder);
    }

    /**
     * 批量删除销售订单
     * 
     * @param orderIds 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteTileSaleOrderByOrderIds(Long[] orderIds)
    {
        return tileSaleOrderMapper.deleteTileSaleOrderByOrderIds(orderIds);
    }

    /**
     * 删除销售订单信息
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteTileSaleOrderByOrderId(Long orderId)
    {
        return tileSaleOrderMapper.deleteTileSaleOrderByOrderId(orderId);
    }

    /**
     * 审核销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    @Override
    public int auditTileSaleOrder(Long orderId)
    {
        TileSaleOrder order = new TileSaleOrder();
        order.setOrderId(orderId);
        order.setOrderStatus("2"); // 已审核
        return tileSaleOrderMapper.updateTileSaleOrder(order);
    }

    /**
     * 取消销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    @Override
    public int cancelTileSaleOrder(Long orderId)
    {
        TileSaleOrder order = new TileSaleOrder();
        order.setOrderId(orderId);
        order.setOrderStatus("5"); // 已取消
        return tileSaleOrderMapper.updateTileSaleOrder(order);
    }
}
