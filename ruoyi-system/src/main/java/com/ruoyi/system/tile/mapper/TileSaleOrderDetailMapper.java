package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSaleOrderDetail;

/**
 * 销售订单明细Mapper接口
 * 
 * @author ruoyi
 */
public interface TileSaleOrderDetailMapper 
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
     * 修改销售订单明细
     * 
     * @param tileSaleOrderDetail 销售订单明细
     * @return 结果
     */
    public int updateTileSaleOrderDetail(TileSaleOrderDetail tileSaleOrderDetail);

    /**
     * 删除销售订单明细
     * 
     * @param detailId 销售订单明细主键
     * @return 结果
     */
    public int deleteTileSaleOrderDetailByDetailId(Long detailId);

    /**
     * 批量删除销售订单明细
     * 
     * @param detailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileSaleOrderDetailByDetailIds(Long[] detailIds);

    /**
     * 批量新增销售订单明细
     * 
     * @param tileSaleOrderDetailList 销售订单明细列表
     * @return 结果
     */
    public int batchInsertTileSaleOrderDetail(List<TileSaleOrderDetail> tileSaleOrderDetailList);

    /**
     * 通过订单ID删除销售订单明细
     * 
     * @param orderId 订单ID
     * @return 结果
     */
    public int deleteTileSaleOrderDetailByOrderId(Long orderId);
}
