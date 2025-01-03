package com.ruoyi.system.tile.domain;

import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商价格对象 tile_supplier_price
 * 
 * @author ruoyi
 * @date 2025-01-03
 */
public class TileSupplierPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 价格ID */
    private Long priceId;

    /** 供应商ID */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 片价格 */
    @Excel(name = "片价格")
    private BigDecimal piecePrice;

    /** 平方价格 */
    @Excel(name = "平方价格")
    private BigDecimal squarePrice;

    /** 件价格 */
    @Excel(name = "件价格")
    private BigDecimal unitPrice;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 品类名称 */
    @Excel(name = "品类名称")
    private String categoryName;

    /** 品类规格 */
    @Excel(name = "品类规格")
    private String specification;

    /** 品类型号 */
    @Excel(name = "品类型号")
    private String model;

    /** 品类列表 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TileSupplierPrice> categoryList;

    public void setPriceId(Long priceId) 
    {
        this.priceId = priceId;
    }

    public Long getPriceId() 
    {
        return priceId;
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

    public void setPiecePrice(BigDecimal piecePrice) 
    {
        this.piecePrice = piecePrice;
    }

    public BigDecimal getPiecePrice() 
    {
        return piecePrice;
    }

    public void setSquarePrice(BigDecimal squarePrice) 
    {
        this.squarePrice = squarePrice;
    }

    public BigDecimal getSquarePrice() 
    {
        return squarePrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() 
    {
        return unitPrice;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<TileSupplierPrice> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<TileSupplierPrice> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("priceId", getPriceId())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("piecePrice", getPiecePrice())
            .append("squarePrice", getSquarePrice())
            .append("unitPrice", getUnitPrice())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("categoryName", getCategoryName())
            .append("specification", getSpecification())
            .append("model", getModel())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("categoryList", getCategoryList())
            .toString();
    }
}
