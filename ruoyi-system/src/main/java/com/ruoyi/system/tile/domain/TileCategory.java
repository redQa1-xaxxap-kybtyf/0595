package com.ruoyi.system.tile.domain;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 瓷砖分类对象 tile_category
 *
 * @author ruoyi
 */
public class TileCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分类ID */
    private Long categoryId;

    /** 父分类ID */
    private Long parentId;

    /** 祖级列表 */
    private String ancestors;

    /** 分类名称 */
    @NotBlank(message = "分类名称不能为空")
    @Size(min = 0, max = 30, message = "分类名称长度不能超过30个字符")
    private String categoryName;

    /** 显示顺序 */
    @NotNull(message = "显示顺序不能为空")
    private Integer orderNum;

    /** 层级 */
    @NotNull(message = "层级不能为空")
    private Integer level;

    /** 显示状态（0显示 1隐藏） */
    private String visible;

    /** 分类图片 */
    private String categoryImage;

    /** 关联商品数量 */
    private Integer goodsCount;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 父分类名称 */
    private String parentName;

    /** 子分类 */
    private List<TileCategory> children = new ArrayList<>();

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Integer getOrderNum()
    {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum)
    {
        this.orderNum = orderNum;
    }

    public Integer getLevel()
    {
        return level;
    }

    public void setLevel(Integer level)
    {
        this.level = level;
    }

    public String getVisible()
    {
        return visible;
    }

    public void setVisible(String visible)
    {
        this.visible = visible;
    }

    public String getCategoryImage()
    {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage)
    {
        this.categoryImage = categoryImage;
    }

    public Integer getGoodsCount()
    {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount)
    {
        this.goodsCount = goodsCount;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getParentName()
    {
        return parentName;
    }

    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public List<TileCategory> getChildren()
    {
        return children;
    }

    public void setChildren(List<TileCategory> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("categoryId", getCategoryId())
            .append("parentId", getParentId())
            .append("categoryName", getCategoryName())
            .append("ancestors", getAncestors())
            .append("orderNum", getOrderNum())
            .append("level", getLevel())
            .append("visible", getVisible())
            .append("categoryImage", getCategoryImage())
            .append("goodsCount", getGoodsCount())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
