package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSurface;

/**
 * 表面处理管理 服务层
 * 
 * @author ruoyi
 */
public interface ITileSurfaceService
{
    /**
     * 查询表面处理列表
     * 
     * @param surface 表面处理信息
     * @return 表面处理集合
     */
    public List<TileSurface> selectTileSurfaceList(TileSurface surface);

    /**
     * 通过表面处理ID查询表面处理
     * 
     * @param surfaceId 表面处理ID
     * @return 表面处理信息
     */
    public TileSurface selectTileSurfaceById(Long surfaceId);

    /**
     * 校验表面处理名称是否唯一
     * 
     * @param surfaceName 表面处理名称
     * @return 结果
     */
    public boolean checkSurfaceNameUnique(String surfaceName);

    /**
     * 新增表面处理信息
     * 
     * @param surface 表面处理信息
     * @return 结果
     */
    public int insertTileSurface(TileSurface surface);

    /**
     * 修改表面处理信息
     * 
     * @param surface 表面处理信息
     * @return 结果
     */
    public int updateTileSurface(TileSurface surface);

    /**
     * 删除表面处理信息
     * 
     * @param surfaceId 表面处理ID
     * @return 结果
     */
    public int deleteTileSurfaceById(Long surfaceId);

    /**
     * 批量删除表面处理信息
     * 
     * @param surfaceIds 需要删除的表面处理ID
     * @return 结果
     */
    public int deleteTileSurfaceByIds(Long[] surfaceIds);
}
