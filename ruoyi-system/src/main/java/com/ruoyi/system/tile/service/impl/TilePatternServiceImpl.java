package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TilePatternMapper;
import com.ruoyi.system.tile.domain.TilePattern;
import com.ruoyi.system.tile.service.ITilePatternService;

/**
 * 花型Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class TilePatternServiceImpl implements ITilePatternService
{
    @Autowired
    private TilePatternMapper tilePatternMapper;

    /**
     * 查询花型列表
     * 
     * @param pattern 花型信息
     * @return 花型集合
     */
    @Override
    public List<TilePattern> selectTilePatternList(TilePattern pattern)
    {
        return tilePatternMapper.selectTilePatternList(pattern);
    }

    /**
     * 通过花型ID查询花型
     * 
     * @param patternId 花型ID
     * @return 花型信息
     */
    @Override
    public TilePattern selectTilePatternById(Long patternId)
    {
        return tilePatternMapper.selectTilePatternById(patternId);
    }

    /**
     * 校验花型名称是否唯一
     * 
     * @param patternName 花型名称
     * @return 结果
     */
    @Override
    public boolean checkPatternNameUnique(String patternName)
    {
        return tilePatternMapper.checkPatternNameUnique(patternName) == null;
    }

    /**
     * 新增花型信息
     * 
     * @param pattern 花型信息
     * @return 结果
     */
    @Override
    public int insertTilePattern(TilePattern pattern)
    {
        return tilePatternMapper.insertTilePattern(pattern);
    }

    /**
     * 修改花型信息
     * 
     * @param pattern 花型信息
     * @return 结果
     */
    @Override
    public int updateTilePattern(TilePattern pattern)
    {
        return tilePatternMapper.updateTilePattern(pattern);
    }

    /**
     * 删除花型信息
     * 
     * @param patternId 花型ID
     * @return 结果
     */
    @Override
    public int deleteTilePatternById(Long patternId)
    {
        return tilePatternMapper.deleteTilePatternById(patternId);
    }

    /**
     * 批量删除花型信息
     * 
     * @param patternIds 需要删除的花型ID
     * @return 结果
     */
    @Override
    public int deleteTilePatternByIds(Long[] patternIds)
    {
        return tilePatternMapper.deleteTilePatternByIds(patternIds);
    }
}
