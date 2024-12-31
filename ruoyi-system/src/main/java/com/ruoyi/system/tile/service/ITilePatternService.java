package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TilePattern;

/**
 * 图案管理 服务层
 * 
 * @author ruoyi
 */
public interface ITilePatternService
{
    /**
     * 查询图案列表
     * 
     * @param pattern 图案信息
     * @return 图案集合
     */
    public List<TilePattern> selectTilePatternList(TilePattern pattern);

    /**
     * 通过图案ID查询图案
     * 
     * @param patternId 图案ID
     * @return 图案信息
     */
    public TilePattern selectTilePatternById(Long patternId);

    /**
     * 校验图案名称是否唯一
     * 
     * @param patternName 图案名称
     * @return 结果
     */
    public boolean checkPatternNameUnique(String patternName);

    /**
     * 新增图案信息
     * 
     * @param pattern 图案信息
     * @return 结果
     */
    public int insertTilePattern(TilePattern pattern);

    /**
     * 修改图案信息
     * 
     * @param pattern 图案信息
     * @return 结果
     */
    public int updateTilePattern(TilePattern pattern);

    /**
     * 删除图案信息
     * 
     * @param patternId 图案ID
     * @return 结果
     */
    public int deleteTilePatternById(Long patternId);

    /**
     * 批量删除图案信息
     * 
     * @param patternIds 需要删除的图案ID
     * @return 结果
     */
    public int deleteTilePatternByIds(Long[] patternIds);
}
