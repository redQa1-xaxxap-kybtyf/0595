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
            .append("remark", getRemark())
            .append("goods", getGoods())
            .toString();
    }
}
