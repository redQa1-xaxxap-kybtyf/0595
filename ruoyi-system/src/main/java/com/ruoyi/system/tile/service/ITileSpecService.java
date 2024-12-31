package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSpec;

/**
 * 规格尺寸管理 服务层
 * 
 * @author ruoyi
 */
public interface ITileSpecService
{
    /**
     * 查询规格尺寸列表
     * 
     * @param spec 规格尺寸信息
     * @return 规格尺寸集合
     */
    public List<TileSpec> selectTileSpecList(TileSpec spec);

    /**
     * 通过规格ID查询规格
     * 
     * @param specId 规格ID
     * @return 规格尺寸信息
     */
    public TileSpec selectTileSpecById(Long specId);

    /**
     * 校验规格名称是否唯一
     * 
     * @param specName 规格名称
     * @return 结果
     */
    public boolean checkSpecNameUnique(String specName);

    /**
     * 新增规格尺寸信息
     * 
     * @param spec 规格尺寸信息
     * @return 结果
     */
    public int insertTileSpec(TileSpec spec);

    /**
     * 修改规格尺寸信息
     * 
     * @param spec 规格尺寸信息
     * @return 结果
     */
    public int updateTileSpec(TileSpec spec);

    /**
     * 删除规格尺寸信息
     * 
     * @param specId 规格ID
     * @return 结果
     */
    public int deleteTileSpecById(Long specId);

    /**
     * 批量删除规格尺寸信息
     * 
     * @param specIds 需要删除的规格ID
     * @return 结果
     */
    public int deleteTileSpecByIds(Long[] specIds);
}
