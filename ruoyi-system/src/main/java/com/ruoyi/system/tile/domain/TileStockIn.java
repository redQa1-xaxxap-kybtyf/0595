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

    /** 仓库名称 */
    private String warehouseName;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 入库类型（1采购入库 2退货入库） */
    @Excel(name = "入库类型", readConverterExp = "1=采购入库,2=退货入库")
    private String inType;

    /** 关联销售单ID */
    @Excel(name = "关联销售单ID")
    private Long saleOrderId;

    /** 关联客户ID */
    @Excel(name = "关联客户ID")
    private Long customerId;

    /** 客户名称 */
    private String customerName;

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

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setInType(String inType) 
    {
        this.inType = inType;
    }

    public String getInType() 
    {
        return inType;
    }

    public void setSaleOrderId(Long saleOrderId) 
    {
        this.saleOrderId = saleOrderId;
    }

    public Long getSaleOrderId() 
    {
        return saleOrderId;
    }

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
            .append("warehouseName", getWarehouseName())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("inType", getInType())
            .append("saleOrderId", getSaleOrderId())
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
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
