package com.ruoyi.system.tile.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.system.tile.domain.TileCategory;

/**
 * 瓷砖分类管理 数据层
 * 
 * @author ruoyi
 */
public interface TileCategoryMapper
{
    /**
     * 查询瓷砖分类列表
     * 
     * @param category 瓷砖分类信息
     * @return 瓷砖分类集合
     */
    public List<TileCategory> selectTileCategoryList(TileCategory category);

    /**
     * 通过分类ID查询分类
     * 
     * @param categoryId 分类ID
     * @return 瓷砖分类信息
     */
    public TileCategory selectTileCategoryById(Long categoryId);

    /**
     * 校验分类名称是否唯一
     * 
     * @param categoryName 分类名称
     * @param parentId 父分类ID
     * @return 结果
     */
    public TileCategory checkCategoryNameUnique(@Param("categoryName") String categoryName, @Param("parentId") Long parentId);

    /**
     * 新增瓷砖分类信息
     * 
     * @param category 瓷砖分类信息
     * @return 结果
     */
    public int insertTileCategory(TileCategory category);

    /**
     * 修改瓷砖分类信息
     * 
     * @param category 瓷砖分类信息
     * @return 结果
     */
    public int updateTileCategory(TileCategory category);

    /**
     * 删除瓷砖分类管理信息
     * 
     * @param categoryId 瓷砖分类ID
     * @return 结果
     */
    public int deleteTileCategoryById(Long categoryId);

    /**
     * 查询子分类
     * 
     * @param categoryId 分类ID
     * @return 子分类列表
     */
    public List<TileCategory> selectChildrenCategoryById(Long categoryId);
}
