package com.ruoyi.system.tile.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 出库单对象 tile_stock_out
 * 
 * @author ruoyi
 */
public class TileStockOut extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库单ID */
    private Long outId;

    /** 出库单号 */
    @Excel(name = "出库单号")
    private String outCode;

    /** 仓库ID */
    @Excel(name = "仓库")
    private Long warehouseId;

    /** 仓库名称 */
    @Excel(name = "仓库")
    private String warehouseName;

    /** 状态（1待出库 2已出库 3已取消） */
    @Excel(name = "状态", readConverterExp = "1=待出库,2=已出库,3=已取消")
    private String status;

    /** 出库单明细 */
    private List<TileStockOutDetail> details;

    public void setOutId(Long outId) 
    {
        this.outId = outId;
    }

    public Long getOutId() 
    {
        return outId;
    }

    public void setOutCode(String outCode) 
    {
        this.outCode = outCode;
    }

    public String getOutCode() 
    {
        return outCode;
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

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public List<TileStockOutDetail> getDetails()
    {
        return details;
    }

    public void setDetails(List<TileStockOutDetail> details)
    {
        this.details = details;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("outId", getOutId())
            .append("outCode", getOutCode())
            .append("warehouseId", getWarehouseId())
            .append("warehouseName", getWarehouseName())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("details", getDetails())
            .toString();
    }
}
