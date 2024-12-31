package com.ruoyi.system.tile.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图案对象 tile_pattern
 * 
 * @author ruoyi
 */
public class TilePattern extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图案ID */
    @Excel(name = "图案ID")
    private Long patternId;

    /** 图案名称 */
    @Excel(name = "图案名称")
    @NotBlank(message = "图案名称不能为空")
    @Size(min = 0, max = 50, message = "图案名称长度不能超过50个字符")
    private String patternName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setPatternId(Long patternId) 
    {
        this.patternId = patternId;
    }

    public Long getPatternId() 
    {
        return patternId;
    }

    public void setPatternName(String patternName) 
    {
        this.patternName = patternName;
    }

    public String getPatternName() 
    {
        return patternName;
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
            .append("patternId", getPatternId())
            .append("patternName", getPatternName())
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
