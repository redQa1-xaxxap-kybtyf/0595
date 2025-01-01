package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileSurfaceMapper;
import com.ruoyi.system.tile.domain.TileSurface;
import com.ruoyi.system.tile.service.ITileSurfaceService;

/**
 * 表面工艺Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class TileSurfaceServiceImpl implements ITileSurfaceService
{
    @Autowired
    private TileSurfaceMapper tileSurfaceMapper;

    /**
     * 查询表面处理列表
     * 
     * @param surface 表面处理信息
     * @return 表面处理集合
     */
    @Override
    public List<TileSurface> selectTileSurfaceList(TileSurface surface)
    {
        return tileSurfaceMapper.selectTileSurfaceList(surface);
    }

    /**
     * 通过表面处理ID查询表面处理
     * 
     * @param surfaceId 表面处理ID
     * @return 表面处理信息
     */
    @Override
    public TileSurface selectTileSurfaceById(Long surfaceId)
    {
        return tileSurfaceMapper.selectTileSurfaceById(surfaceId);
    }

    /**
     * 校验表面处理名称是否唯一
     * 
     * @param surfaceName 表面处理名称
     * @return 结果
     */
    @Override
    public boolean checkSurfaceNameUnique(String surfaceName)
    {
        return tileSurfaceMapper.checkSurfaceNameUnique(surfaceName) == null;
    }

    /**
     * 新增表面处理信息
     * 
     * @param surface 表面处理信息
     * @return 结果
     */
    @Override
    public int insertTileSurface(TileSurface surface)
    {
        return tileSurfaceMapper.insertTileSurface(surface);
    }

    /**
     * 修改表面处理信息
     * 
     * @param surface 表面处理信息
     * @return 结果
     */
    @Override
    public int updateTileSurface(TileSurface surface)
    {
        return tileSurfaceMapper.updateTileSurface(surface);
    }

    /**
     * 删除表面处理信息
     * 
     * @param surfaceId 表面处理ID
     * @return 结果
     */
    @Override
    public int deleteTileSurfaceById(Long surfaceId)
    {
        return tileSurfaceMapper.deleteTileSurfaceById(surfaceId);
    }

    /**
     * 批量删除表面处理信息
     * 
     * @param surfaceIds 需要删除的表面处理ID
     * @return 结果
     */
    @Override
    public int deleteTileSurfaceByIds(Long[] surfaceIds)
    {
        return tileSurfaceMapper.deleteTileSurfaceByIds(surfaceIds);
    }
}
