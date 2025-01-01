package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileSpecMapper;
import com.ruoyi.system.tile.domain.TileSpec;
import com.ruoyi.system.tile.service.ITileSpecService;

/**
 * 规格Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class TileSpecServiceImpl implements ITileSpecService
{
    @Autowired
    private TileSpecMapper tileSpecMapper;

    /**
     * 查询规格列表
     * 
     * @param spec 规格信息
     * @return 规格集合
     */
    @Override
    public List<TileSpec> selectTileSpecList(TileSpec spec)
    {
        return tileSpecMapper.selectTileSpecList(spec);
    }

    /**
     * 通过规格ID查询规格
     * 
     * @param specId 规格ID
     * @return 规格信息
     */
    @Override
    public TileSpec selectTileSpecById(Long specId)
    {
        return tileSpecMapper.selectTileSpecById(specId);
    }

    /**
     * 校验规格名称是否唯一
     * 
     * @param specName 规格名称
     * @return 结果
     */
    @Override
    public boolean checkSpecNameUnique(String specName)
    {
        return tileSpecMapper.checkSpecNameUnique(specName) == null;
    }

    /**
     * 新增规格信息
     * 
     * @param spec 规格信息
     * @return 结果
     */
    @Override
    public int insertTileSpec(TileSpec spec)
    {
        return tileSpecMapper.insertTileSpec(spec);
    }

    /**
     * 修改规格信息
     * 
     * @param spec 规格信息
     * @return 结果
     */
    @Override
    public int updateTileSpec(TileSpec spec)
    {
        return tileSpecMapper.updateTileSpec(spec);
    }

    /**
     * 删除规格信息
     * 
     * @param specId 规格ID
     * @return 结果
     */
    @Override
    public int deleteTileSpecById(Long specId)
    {
        return tileSpecMapper.deleteTileSpecById(specId);
    }

    /**
     * 批量删除规格信息
     * 
     * @param specIds 需要删除的规格ID
     * @return 结果
     */
    @Override
    public int deleteTileSpecByIds(Long[] specIds)
    {
        return tileSpecMapper.deleteTileSpecByIds(specIds);
    }
}
