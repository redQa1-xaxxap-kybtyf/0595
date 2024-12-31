package com.ruoyi.system.tile.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 规格尺寸对象 tile_spec
 * 
 * @author ruoyi
 */
public class TileSpec extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 规格ID */
    @Excel(name = "规格ID")
    private Long specId;

    /** 规格名称 */
    @Excel(name = "规格名称")
    @NotBlank(message = "规格名称不能为空")
    @Size(min = 0, max = 50, message = "规格名称长度不能超过50个字符")
    private String specName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setSpecId(Long specId) 
    {
        this.specId = specId;
    }

    public Long getSpecId() 
    {
        return specId;
    }

    public void setSpecName(String specName) 
    {
        this.specName = specName;
    }

    public String getSpecName() 
    {
        return specName;
    }

    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("specId", getSpecId())
            .append("specName", getSpecName())
            .append("orderNum", getOrderNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
