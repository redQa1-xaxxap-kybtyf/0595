package com.ruoyi.system.tile.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单明细对象 tile_stock_in_detail
 * 
 * @author ruoyi
 */
public class TileStockInDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 明细ID */
    private Long detailId;

    /** 入库单ID */
    @Excel(name = "入库单ID")
    private Long inId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long goodsId;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Integer quantity;

    /** 入库单价 */
    @Excel(name = "入库单价")
    private BigDecimal price;

    /** 入库金额 */
    @Excel(name = "入库金额")
    private BigDecimal amount;

    /** 批次号 */
    @Excel(name = "批次号")
    private String batchNo;

    /** 生产日期 */
    @Excel(name = "生产日期")
    private String productionDate;

    /** 货位ID */
    @Excel(name = "货位ID")
    private Long locationId;

    /** 货位名称 */
    private String locationName;

    /** 单位（1片 2箱） */
    @Excel(name = "单位", readConverterExp = "1=片,2=箱")
    private String unit;

    /** 商品信息 */
    private TileGoods goods;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }

    public void setInId(Long inId) 
    {
        this.inId = inId;
    }

    public Long getInId() 
    {
        return inId;
    }

    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }

    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }

    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }

    public void setAmount(BigDecimal amount) 
    {
        this.amount = amount;
    }

    public BigDecimal getAmount() 
    {
        return amount;
    }

    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }

    public void setProductionDate(String productionDate) 
    {
        this.productionDate = productionDate;
    }

    public String getProductionDate() 
    {
        return productionDate;
    }

    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }

    public TileGoods getGoods() 
    {
        return goods;
    }

    public void setGoods(TileGoods goods) 
    {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("inId", getInId())
            .append("goodsId", getGoodsId())
            .append("quantity", getQuantity())
            .append("price", getPrice())
            .append("amount", getAmount())
            .append("batchNo", getBatchNo())
            .append("productionDate", getProductionDate())
            .append("locationId", getLocationId())
            .append("locationName", getLocationName())
            .append("unit", getUnit())
            .append("remark", getRemark())
            .append("goods", getGoods())
            .toString();
    }
}
