package com.ruoyi.system.tile.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用途对象 tile_usage
 * 
 * @author ruoyi
 */
public class TileUsage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用途ID */
    @Excel(name = "用途ID")
    private Long usageId;

    /** 用途名称 */
    @Excel(name = "用途名称")
    @NotBlank(message = "用途名称不能为空")
    @Size(min = 0, max = 50, message = "用途名称长度不能超过50个字符")
    private String usageName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setUsageId(Long usageId) 
    {
        this.usageId = usageId;
    }

    public Long getUsageId() 
    {
        return usageId;
    }

    public void setUsageName(String usageName) 
    {
        this.usageName = usageName;
    }

    public String getUsageName() 
    {
        return usageName;
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
            .append("usageId", getUsageId())
            .append("usageName", getUsageName())
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
