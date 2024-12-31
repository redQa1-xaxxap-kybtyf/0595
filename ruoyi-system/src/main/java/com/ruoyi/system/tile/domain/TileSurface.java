package com.ruoyi.system.tile.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 表面处理对象 tile_surface
 * 
 * @author ruoyi
 */
public class TileSurface extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 表面处理ID */
    @Excel(name = "表面处理ID")
    private Long surfaceId;

    /** 表面处理名称 */
    @Excel(name = "表面处理名称")
    @NotBlank(message = "表面处理名称不能为空")
    @Size(min = 0, max = 50, message = "表面处理名称长度不能超过50个字符")
    private String surfaceName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    public void setSurfaceId(Long surfaceId) 
    {
        this.surfaceId = surfaceId;
    }

    public Long getSurfaceId() 
    {
        return surfaceId;
    }

    public void setSurfaceName(String surfaceName) 
    {
        this.surfaceName = surfaceName;
    }

    public String getSurfaceName() 
    {
        return surfaceName;
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
            .append("surfaceId", getSurfaceId())
            .append("surfaceName", getSurfaceName())
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
