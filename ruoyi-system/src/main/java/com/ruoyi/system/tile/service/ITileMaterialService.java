package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileMaterial;

/**
 * 材质管理 服务层
 * 
 * @author ruoyi
 */
public interface ITileMaterialService
{
    /**
     * 查询材质列表
     * 
     * @param material 材质信息
     * @return 材质集合
     */
    public List<TileMaterial> selectTileMaterialList(TileMaterial material);

    /**
     * 通过材质ID查询材质
     * 
     * @param materialId 材质ID
     * @return 材质信息
     */
    public TileMaterial selectTileMaterialById(Long materialId);

    /**
     * 校验材质名称是否唯一
     * 
     * @param materialName 材质名称
     * @return 结果
     */
    public boolean checkMaterialNameUnique(String materialName);

    /**
     * 新增材质信息
     * 
     * @param material 材质信息
     * @return 结果
     */
    public int insertTileMaterial(TileMaterial material);

    /**
     * 修改材质信息
     * 
     * @param material 材质信息
     * @return 结果
     */
    public int updateTileMaterial(TileMaterial material);

    /**
     * 删除材质信息
     * 
     * @param materialId 材质ID
     * @return 结果
     */
    public int deleteTileMaterialById(Long materialId);

    /**
     * 批量删除材质信息
     * 
     * @param materialIds 需要删除的材质ID
     * @return 结果
     */
    public int deleteTileMaterialByIds(Long[] materialIds);
}
