package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSaleOrder;

/**
 * 销售订单Service接口
 * 
 * @author ruoyi
 */
public interface ITileSaleOrderService 
{
    /**
     * 查询销售订单
     * 
     * @param orderId 销售订单主键
     * @return 销售订单
     */
    public TileSaleOrder selectTileSaleOrderByOrderId(Long orderId);

    /**
     * 查询销售订单列表
     * 
     * @param tileSaleOrder 销售订单
     * @return 销售订单集合
     */
    public List<TileSaleOrder> selectTileSaleOrderList(TileSaleOrder tileSaleOrder);

    /**
     * 新增销售订单
     * 
     * @param tileSaleOrder 销售订单
     * @return 结果
     */
    public int insertTileSaleOrder(TileSaleOrder tileSaleOrder);

    /**
     * 修改销售订单
     * 
     * @param tileSaleOrder 销售订单
     * @return 结果
     */
    public int updateTileSaleOrder(TileSaleOrder tileSaleOrder);

    /**
     * 批量删除销售订单
     * 
     * @param orderIds 需要删除的销售订单主键集合
     * @return 结果
     */
    public int deleteTileSaleOrderByOrderIds(Long[] orderIds);

    /**
     * 删除销售订单信息
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    public int deleteTileSaleOrderByOrderId(Long orderId);

    /**
     * 审核销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    public int auditTileSaleOrder(Long orderId);

    /**
     * 取消销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    public int cancelTileSaleOrder(Long orderId);
}
