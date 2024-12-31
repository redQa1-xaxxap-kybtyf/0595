package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileCategory;

/**
 * 瓷砖分类管理Service接口
 * 
 * @author ruoyi
 */
public interface ITileCategoryService
{
    /**
     * 查询瓷砖分类列表
     * 
     * @param category 瓷砖分类信息
     * @return 瓷砖分类集合
     */
    public List<TileCategory> selectTileCategoryList(TileCategory category);

    /**
     * 查询瓷砖分类信息
     * 
     * @param categoryId 瓷砖分类ID
     * @return 瓷砖分类信息
     */
    public TileCategory selectTileCategoryById(Long categoryId);

    /**
     * 新增瓷砖分类
     * 
     * @param category 瓷砖分类信息
     * @return 结果
     */
    public int insertTileCategory(TileCategory category);

    /**
     * 修改瓷砖分类
     * 
     * @param category 瓷砖分类信息
     * @return 结果
     */
    public int updateTileCategory(TileCategory category);

    /**
     * 删除瓷砖分类信息
     * 
     * @param categoryId 瓷砖分类ID
     * @return 结果
     */
    public int deleteTileCategoryById(Long categoryId);
}
