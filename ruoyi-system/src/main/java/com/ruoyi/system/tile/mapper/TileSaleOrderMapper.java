package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSaleOrder;

/**
 * 销售订单Mapper接口
 * 
 * @author ruoyi
 */
public interface TileSaleOrderMapper 
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
     * 删除销售订单
     * 
     * @param orderId 销售订单主键
     * @return 结果
     */
    public int deleteTileSaleOrderByOrderId(Long orderId);

    /**
     * 批量删除销售订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileSaleOrderByOrderIds(Long[] orderIds);
}
