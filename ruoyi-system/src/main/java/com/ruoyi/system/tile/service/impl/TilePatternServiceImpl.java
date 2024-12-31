package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.tile.mapper.TilePatternMapper;
import com.ruoyi.system.tile.domain.TilePattern;
import com.ruoyi.system.tile.service.ITilePatternService;

/**
 * 图案管理 服务层实现
 * 
 * @author ruoyi
 */
@Service
public class TilePatternServiceImpl implements ITilePatternService
{
    @Autowired
    private TilePatternMapper tilePatternMapper;

    /**
     * 查询图案列表
     * 
     * @param pattern 图案信息
     * @return 图案集合
     */
    @Override
    public List<TilePattern> selectTilePatternList(TilePattern pattern)
    {
        return tilePatternMapper.selectTilePatternList(pattern);
    }

    /**
     * 通过图案ID查询图案
     * 
     * @param patternId 图案ID
     * @return 图案信息
     */
    @Override
    public TilePattern selectTilePatternById(Long patternId)
    {
        return tilePatternMapper.selectTilePatternById(patternId);
    }

    /**
     * 校验图案名称是否唯一
     * 
     * @param patternName 图案名称
     * @return 结果
     */
    @Override
    public boolean checkPatternNameUnique(String patternName)
    {
        return tilePatternMapper.checkPatternNameUnique(patternName) == null;
    }

    /**
     * 新增图案信息
     * 
     * @param pattern 图案信息
     * @return 结果
     */
    @Override
    public int insertTilePattern(TilePattern pattern)
    {
        return tilePatternMapper.insertTilePattern(pattern);
    }

    /**
     * 修改图案信息
     * 
     * @param pattern 图案信息
     * @return 结果
     */
    @Override
    public int updateTilePattern(TilePattern pattern)
    {
        return tilePatternMapper.updateTilePattern(pattern);
    }

    /**
     * 删除图案信息
     * 
     * @param patternId 图案ID
     * @return 结果
     */
    @Override
    public int deleteTilePatternById(Long patternId)
    {
        return tilePatternMapper.deleteTilePatternById(patternId);
    }

    /**
     * 批量删除图案信息
     * 
     * @param patternIds 需要删除的图案ID
     * @return 结果
     */
    @Override
    public int deleteTilePatternByIds(Long[] patternIds)
    {
        return tilePatternMapper.deleteTilePatternByIds(patternIds);
    }
}
