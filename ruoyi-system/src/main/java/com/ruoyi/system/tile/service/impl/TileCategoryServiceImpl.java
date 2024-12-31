package com.ruoyi.system.tile.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileCategoryMapper;
import com.ruoyi.system.tile.domain.TileCategory;
import com.ruoyi.system.tile.service.ITileCategoryService;

/**
 * 瓷砖分类管理 服务实现
 * 
 * @author ruoyi
 */
@Service
public class TileCategoryServiceImpl implements ITileCategoryService
{
    @Autowired
    private TileCategoryMapper tileCategoryMapper;

    /**
     * 查询瓷砖分类列表
     * 
     * @param category 瓷砖分类信息
     * @return 瓷砖分类集合
     */
    @Override
    public List<TileCategory> selectTileCategoryList(TileCategory category)
    {
        List<TileCategory> categories = tileCategoryMapper.selectTileCategoryList(category);
        
        // 如果是搜索操作，需要包含父节点
        if (category != null && (category.getCategoryName() != null || category.getVisible() != null))
        {
            List<TileCategory> result = new ArrayList<>();
            for (TileCategory item : categories)
            {
                // 添加当前节点
                result.add(item);
                // 添加父节点链
                addParentChain(result, item);
            }
            return buildTree(result);
        }
        
        return buildTree(categories);
    }

    /**
     * 构建树形结构
     */
    private List<TileCategory> buildTree(List<TileCategory> categories) {
        List<TileCategory> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        
        for (TileCategory item : categories) {
            tempList.add(item.getCategoryId());
        }
        
        for (TileCategory item : categories) {
            // 如果是顶级节点或父节点不在当前列表中，则直接添加
            if (item.getParentId() == 0L || !tempList.contains(item.getParentId())) {
                recursionFn(categories, item);
                returnList.add(item);
            }
        }
        
        if (returnList.isEmpty()) {
            returnList = categories;
        }
        
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<TileCategory> list, TileCategory t) {
        // 得到子节点列表
        List<TileCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (TileCategory tChild : childList) {
            // 判断是否有子节点
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<TileCategory> getChildList(List<TileCategory> list, TileCategory t) {
        List<TileCategory> tlist = new ArrayList<>();
        for (TileCategory n : list) {
            if (n.getParentId().longValue() == t.getCategoryId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<TileCategory> list, TileCategory t) {
        return !getChildList(list, t).isEmpty();
    }

    /**
     * 添加父节点链
     */
    private void addParentChain(List<TileCategory> result, TileCategory category)
    {
        if (category.getParentId() != null && category.getParentId() != 0L)
        {
            TileCategory parent = tileCategoryMapper.selectTileCategoryById(category.getParentId());
            if (parent != null && !containsCategory(result, parent.getCategoryId()))
            {
                result.add(parent);
                addParentChain(result, parent);
            }
        }
    }

    /**
     * 检查列表中是否已包含指定分类
     */
    private boolean containsCategory(List<TileCategory> categories, Long categoryId)
    {
        return categories.stream().anyMatch(c -> c.getCategoryId().equals(categoryId));
    }

    /**
     * 查询瓷砖分类信息
     * 
     * @param categoryId 瓷砖分类ID
     * @return 瓷砖分类信息
     */
    @Override
    public TileCategory selectTileCategoryById(Long categoryId)
    {
        return tileCategoryMapper.selectTileCategoryById(categoryId);
    }

    /**
     * 新增瓷砖分类
     * 
     * @param category 瓷砖分类信息
     * @return 结果
     */
    @Override
    public int insertTileCategory(TileCategory category)
    {
        TileCategory info = tileCategoryMapper.selectTileCategoryById(category.getParentId());
        // 如果父节点不为0，设置祖级列表
        if (info != null)
        {
            category.setAncestors(info.getAncestors() + "," + category.getParentId());
        }
        else
        {
            category.setAncestors("0");
        }
        return tileCategoryMapper.insertTileCategory(category);
    }

    /**
     * 修改瓷砖分类
     * 
     * @param category 瓷砖分类信息
     * @return 结果
     */
    @Override
    public int updateTileCategory(TileCategory category)
    {
        TileCategory newParentCategory = tileCategoryMapper.selectTileCategoryById(category.getParentId());
        TileCategory oldCategory = tileCategoryMapper.selectTileCategoryById(category.getCategoryId());
        if (newParentCategory != null && oldCategory != null)
        {
            String newAncestors = newParentCategory.getAncestors() + "," + category.getParentId();
            String oldAncestors = oldCategory.getAncestors();
            category.setAncestors(newAncestors);
            updateChildrenAncestors(category, newAncestors, oldAncestors);
        }
        return tileCategoryMapper.updateTileCategory(category);
    }

    /**
     * 修改子元素关系
     * 
     * @param category 分类
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    private void updateChildrenAncestors(TileCategory category, String newAncestors, String oldAncestors)
    {
        List<TileCategory> children = tileCategoryMapper.selectChildrenCategoryById(category.getCategoryId());
        for (TileCategory child : children)
        {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
            tileCategoryMapper.updateTileCategory(child);
        }
    }

    /**
     * 删除瓷砖分类信息
     * 
     * @param categoryId 瓷砖分类ID
     * @return 结果
     */
    @Override
    public int deleteTileCategoryById(Long categoryId)
    {
        return tileCategoryMapper.deleteTileCategoryById(categoryId);
    }
}
