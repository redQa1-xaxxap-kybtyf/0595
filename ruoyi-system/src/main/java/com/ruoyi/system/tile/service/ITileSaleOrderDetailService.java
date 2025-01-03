package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSaleOrderDetail;

/**
 * 销售订单明细Service接口
 * 
 * @author ruoyi
 */
public interface ITileSaleOrderDetailService 
{
    /**
     * 查询销售订单明细
     * 
     * @param detailId 销售订单明细主键
     * @return 销售订单明细
     */
    public TileSaleOrderDetail selectTileSaleOrderDetailByDetailId(Long detailId);

    /**
     * 查询销售订单明细列表
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 销售订单明细集合
     */
    public List<TileSaleOrderDetail> selectTileSaleOrderDetailList(TileSaleOrderDetail tileSaleOrderDetail);

    /**
     * 根据订单ID查询明细
     * 
     * @param orderId 订单ID
     * @return 销售订单明细集合
     */
    public List<TileSaleOrderDetail> selectTileSaleOrderDetailByOrderId(Long orderId);

    /**
     * 新增销售订单明细
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 结果
     */
    public int insertTileSaleOrderDetail(TileSaleOrderDetail tileSaleOrderDetail);

    /**
     * 批量新增销售订单明细
     * 
     * @param tileSaleOrderDetails 销售订单明细列表
     * @return 结果
     */
    public int insertTileSaleOrderDetails(List<TileSaleOrderDetail> tileSaleOrderDetails);

    /**
     * 修改销售订单明细
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 结果
     */
    public int updateTileSaleOrderDetail(TileSaleOrderDetail tileSaleOrderDetail);

    /**
     * 批量删除销售订单明细
     * 
     * @param detailIds 需要删除的销售订单明细主键集合
     * @return 结果
     */
    public int deleteTileSaleOrderDetailByDetailIds(Long[] detailIds);

    /**
     * 删除销售订单明细信息
     * 
     * @param detailId 销售订单明细主键
     * @return 结果
     */
    public int deleteTileSaleOrderDetailByDetailId(Long detailId);

    /**
     * 通过订单ID删除销售订单明细
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteTileSaleOrderDetailByOrderId(Long orderId);
}
