package com.ruoyi.system.tile.domain;

import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 瓷砖商品对象 tile_goods
 *
 * @author ruoyi
 */
public class TileGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 商品ID */
    private Long goodsId;

    /** 商品编号 */
    @NotBlank(message = "商品编号不能为空")
    @Size(min = 0, max = 50, message = "商品编号长度不能超过50个字符")
    private String goodsCode;

    /** 商品名称 */
    @NotBlank(message = "商品名称不能为空")
    @Size(min = 0, max = 100, message = "商品名称长度不能超过100个字符")
    private String goodsName;

    /** 商品分类ID */
    @NotNull(message = "商品分类不能为空")
    private Long categoryId;

    /** 规格尺寸ID */
    private Long specId;

    /** 材质ID */
    private Long materialId;

    /** 表面处理ID */
    private Long surfaceId;

    /** 用途ID */
    private Long usageId;

    /** 图案ID */
    private Long patternId;

    /** 商品主图 */
    private String goodsImage;

    /** 商品图片集 */
    private String goodsImages;

    /** 商品描述 */
    @Size(min = 0, max = 500, message = "商品描述长度不能超过500个字符")
    private String goodsDesc;

    /** 商品价格 */
    private BigDecimal price;

    /** 库存数量 */
    private Integer stock;

    /** 销量 */
    private Integer sales;

    /** 商品状态（0上架 1下架） */
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 分类名称 */
    private String categoryName;

    /** 规格尺寸名称 */
    private String specName;

    /** 材质名称 */
    private String materialName;

    /** 表面处理名称 */
    private String surfaceName;

    /** 用途名称 */
    private String usageName;

    /** 图案名称 */
    private String patternName;

    public Long getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(Long goodsId)
    {
        this.goodsId = goodsId;
    }

    public String getGoodsCode()
    {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode)
    {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName()
    {
        return goodsName;
    }

    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }

    public Long getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(Long categoryId)
    {
        this.categoryId = categoryId;
    }

    public Long getSpecId()
    {
        return specId;
    }

    public void setSpecId(Long specId)
    {
        this.specId = specId;
    }

    public Long getMaterialId()
    {
        return materialId;
    }

    public void setMaterialId(Long materialId)
    {
        this.materialId = materialId;
    }

    public Long getSurfaceId()
    {
        return surfaceId;
    }

    public void setSurfaceId(Long surfaceId)
    {
        this.surfaceId = surfaceId;
    }

    public Long getUsageId()
    {
        return usageId;
    }

    public void setUsageId(Long usageId)
    {
        this.usageId = usageId;
    }

    public Long getPatternId()
    {
        return patternId;
    }

    public void setPatternId(Long patternId)
    {
        this.patternId = patternId;
    }

    public String getGoodsImage()
    {
        return goodsImage;
    }

    public void setGoodsImage(String goodsImage)
    {
        this.goodsImage = goodsImage;
    }

    public String getGoodsImages()
    {
        return goodsImages;
    }

    public void setGoodsImages(String goodsImages)
    {
        this.goodsImages = goodsImages;
    }

    public String getGoodsDesc()
    {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc)
    {
        this.goodsDesc = goodsDesc;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public Integer getStock()
    {
        return stock;
    }

    public void setStock(Integer stock)
    {
        this.stock = stock;
    }

    public Integer getSales()
    {
        return sales;
    }

    public void setSales(Integer sales)
    {
        this.sales = sales;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getSpecName()
    {
        return specName;
    }

    public void setSpecName(String specName)
    {
        this.specName = specName;
    }

    public String getMaterialName()
    {
        return materialName;
    }

    public void setMaterialName(String materialName)
    {
        this.materialName = materialName;
    }

    public String getSurfaceName()
    {
        return surfaceName;
    }

    public void setSurfaceName(String surfaceName)
    {
        this.surfaceName = surfaceName;
    }

    public String getUsageName()
    {
        return usageName;
    }

    public void setUsageName(String usageName)
    {
        this.usageName = usageName;
    }

    public String getPatternName()
    {
        return patternName;
    }

    public void setPatternName(String patternName)
    {
        this.patternName = patternName;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("goodsId", getGoodsId())
                .append("goodsCode", getGoodsCode())
                .append("goodsName", getGoodsName())
                .append("categoryId", getCategoryId())
                .append("specId", getSpecId())
                .append("materialId", getMaterialId())
                .append("surfaceId", getSurfaceId())
                .append("usageId", getUsageId())
                .append("patternId", getPatternId())
                .append("goodsImage", getGoodsImage())
                .append("goodsImages", getGoodsImages())
                .append("goodsDesc", getGoodsDesc())
                .append("price", getPrice())
                .append("stock", getStock())
                .append("sales", getSales())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
