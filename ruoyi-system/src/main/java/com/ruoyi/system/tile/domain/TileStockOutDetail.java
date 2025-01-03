package com.ruoyi.system.tile.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库单明细对象 tile_stock_out_detail
 * 
 * @author ruoyi
 */
public class TileStockOutDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long detailId;

    /** 出库单ID */
    private Long outId;

    /** 商品ID */
    @Excel(name = "商品")
    private Long goodsId;

    /** 商品名称 */
    @Excel(name = "商品")
    private String goodsName;

    /** 货位ID */
    @Excel(name = "货位ID")
    private Long locationId;

    /** 货位名称 */
    private String locationName;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }

    public void setOutId(Long outId) 
    {
        this.outId = outId;
    }

    public Long getOutId() 
    {
        return outId;
    }

    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }

    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }

    public void setLocationName(String locationName) 
    {
        this.locationName = locationName;
    }

    public String getLocationName() 
    {
        return locationName;
    }

    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("outId", getOutId())
            .append("goodsId", getGoodsId())
            .append("goodsName", getGoodsName())
            .append("locationId", getLocationId())
            .append("locationName", getLocationName())
            .append("quantity", getQuantity())
            .toString();
    }
}
