package com.ruoyi.system.tile.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入库单对象 tile_stock_in
 * 
 * @author ruoyi
 */
public class TileStockIn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库单ID */
    private Long inId;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String inCode;

    /** 仓库ID */
    @Excel(name = "仓库ID")
    private Long warehouseId;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 入库类型（1采购入库 2调拨入库） */
    @Excel(name = "入库类型", readConverterExp = "1=采购入库,2=调拨入库")
    private String inType;

    /** 状态（1待入库 2已入库 3已取消） */
    @Excel(name = "状态", readConverterExp = "1=待入库,2=已入库,3=已取消")
    private String status;

    /** 入库总金额 */
    @Excel(name = "入库总金额")
    private BigDecimal totalAmount;

    /** 入库单明细列表 */
    private List<TileStockInDetail> details;

    public void setInId(Long inId) 
    {
        this.inId = inId;
    }

    public Long getInId() 
    {
        return inId;
    }

    public void setInCode(String inCode) 
    {
        this.inCode = inCode;
    }

    public String getInCode() 
    {
        return inCode;
    }

    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setInType(String inType) 
    {
        this.inType = inType;
    }

    public String getInType() 
    {
        return inType;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    public List<TileStockInDetail> getDetails() 
    {
        return details;
    }

    public void setDetails(List<TileStockInDetail> details) 
    {
        this.details = details;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("inId", getInId())
            .append("inCode", getInCode())
            .append("warehouseId", getWarehouseId())
            .append("supplierId", getSupplierId())
            .append("inType", getInType())
            .append("status", getStatus())
            .append("totalAmount", getTotalAmount())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("details", getDetails())
            .toString();
    }
}
