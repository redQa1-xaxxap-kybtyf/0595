package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileUsage;

/**
 * 用途管理 服务层
 * 
 * @author ruoyi
 */
public interface ITileUsageService
{
    /**
     * 查询用途列表
     * 
     * @param usage 用途信息
     * @return 用途集合
     */
    public List<TileUsage> selectTileUsageList(TileUsage usage);

    /**
     * 通过用途ID查询用途
     * 
     * @param usageId 用途ID
     * @return 用途信息
     */
    public TileUsage selectTileUsageById(Long usageId);

    /**
     * 校验用途名称是否唯一
     * 
     * @param usageName 用途名称
     * @return 结果
     */
    public boolean checkUsageNameUnique(String usageName);

    /**
     * 新增用途信息
     * 
     * @param usage 用途信息
     * @return 结果
     */
    public int insertTileUsage(TileUsage usage);

    /**
     * 修改用途信息
     * 
     * @param usage 用途信息
     * @return 结果
     */
    public int updateTileUsage(TileUsage usage);

    /**
     * 删除用途信息
     * 
     * @param usageId 用途ID
     * @return 结果
     */
    public int deleteTileUsageById(Long usageId);

    /**
     * 批量删除用途信息
     * 
     * @param usageIds 需要删除的用途ID
     * @return 结果
     */
    public int deleteTileUsageByIds(Long[] usageIds);
}
