package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileUsageMapper;
import com.ruoyi.system.tile.domain.TileUsage;
import com.ruoyi.system.tile.service.ITileUsageService;

/**
 * 用途Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class TileUsageServiceImpl implements ITileUsageService
{
    @Autowired
    private TileUsageMapper tileUsageMapper;

    /**
     * 查询用途列表
     * 
     * @param usage 用途信息
     * @return 用途集合
     */
    @Override
    public List<TileUsage> selectTileUsageList(TileUsage usage)
    {
        return tileUsageMapper.selectTileUsageList(usage);
    }

    /**
     * 通过用途ID查询用途
     * 
     * @param usageId 用途ID
     * @return 用途信息
     */
    @Override
    public TileUsage selectTileUsageById(Long usageId)
    {
        return tileUsageMapper.selectTileUsageById(usageId);
    }

    /**
     * 校验用途名称是否唯一
     * 
     * @param usageName 用途名称
     * @return 结果
     */
    @Override
    public boolean checkUsageNameUnique(String usageName)
    {
        return tileUsageMapper.checkUsageNameUnique(usageName) == null;
    }

    /**
     * 新增用途信息
     * 
     * @param usage 用途信息
     * @return 结果
     */
    @Override
    public int insertTileUsage(TileUsage usage)
    {
        return tileUsageMapper.insertTileUsage(usage);
    }

    /**
     * 修改用途信息
     * 
     * @param usage 用途信息
     * @return 结果
     */
    @Override
    public int updateTileUsage(TileUsage usage)
    {
        return tileUsageMapper.updateTileUsage(usage);
    }

    /**
     * 删除用途信息
     * 
     * @param usageId 用途ID
     * @return 结果
     */
    @Override
    public int deleteTileUsageById(Long usageId)
    {
        return tileUsageMapper.deleteTileUsageById(usageId);
    }

    /**
     * 批量删除用途信息
     * 
     * @param usageIds 需要删除的用途ID
     * @return 结果
     */
    @Override
    public int deleteTileUsageByIds(Long[] usageIds)
    {
        return tileUsageMapper.deleteTileUsageByIds(usageIds);
    }
}
