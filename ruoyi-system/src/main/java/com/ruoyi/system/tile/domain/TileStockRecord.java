package com.ruoyi.system.tile.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 库存记录对象 tile_stock_record
 * 
 * @author ruoyi
 */
public class TileStockRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 操作类型（1入库 2出库） */
    @Excel(name = "操作类型", readConverterExp = "1=入库,2=出库")
    private String operType;

    /** 商品ID */
    private Long goodsId;

    /** 商品名称 */
    @Excel(name = "商品")
    private String goodsName;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库名称 */
    @Excel(name = "仓库")
    private String warehouseName;

    /** 数量 */
    @Excel(name = "数量")
    private Long quantity;

    /** 来源单据ID */
    private Long sourceId;

    /** 来源单据类型（1入库单 2出库单） */
    @Excel(name = "单据类型", readConverterExp = "1=入库单,2=出库单")
    private String sourceType;

    /** 来源单据编号 */
    @Excel(name = "单据编号")
    private String sourceCode;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private String operTime;

    /** 操作人员 */
    @Excel(name = "操作人员")
    private String operBy;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }

    public void setOperType(String operType) 
    {
        this.operType = operType;
    }

    public String getOperType() 
    {
        return operType;
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

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setWarehouseName(String warehouseName) 
    {
        this.warehouseName = warehouseName;
    }

    public String getWarehouseName() 
    {
        return warehouseName;
    }

    public void setQuantity(Long quantity) 
    {
        this.quantity = quantity;
    }

    public Long getQuantity() 
    {
        return quantity;
    }

    public void setSourceId(Long sourceId) 
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId() 
    {
        return sourceId;
    }

    public void setSourceType(String sourceType) 
    {
        this.sourceType = sourceType;
    }

    public String getSourceType() 
    {
        return sourceType;
    }

    public void setSourceCode(String sourceCode) 
    {
        this.sourceCode = sourceCode;
    }

    public String getSourceCode() 
    {
        return sourceCode;
    }

    public void setOperTime(String operTime) 
    {
        this.operTime = operTime;
    }

    public String getOperTime() 
    {
        return operTime;
    }

    public void setOperBy(String operBy) 
    {
        this.operBy = operBy;
    }

    public String getOperBy() 
    {
        return operBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("operType", getOperType())
            .append("goodsId", getGoodsId())
            .append("goodsName", getGoodsName())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("quantity", getQuantity())
            .append("sourceId", getSourceId())
            .append("sourceType", getSourceType())
            .append("sourceCode", getSourceCode())
            .append("operTime", getOperTime())
            .append("operBy", getOperBy())
            .append("remark", getRemark())
            .toString();
    }
}
