package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileMaterialMapper;
import com.ruoyi.system.tile.domain.TileMaterial;
import com.ruoyi.system.tile.service.ITileMaterialService;

/**
 * 材质Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-01
 */
@Service
public class TileMaterialServiceImpl implements ITileMaterialService
{
    @Autowired
    private TileMaterialMapper tileMaterialMapper;

    /**
     * 查询材质列表
     * 
     * @param material 材质信息
     * @return 材质集合
     */
    @Override
    public List<TileMaterial> selectTileMaterialList(TileMaterial material)
    {
        return tileMaterialMapper.selectTileMaterialList(material);
    }

    /**
     * 通过材质ID查询材质
     * 
     * @param materialId 材质ID
     * @return 材质信息
     */
    @Override
    public TileMaterial selectTileMaterialById(Long materialId)
    {
        return tileMaterialMapper.selectTileMaterialById(materialId);
    }

    /**
     * 校验材质名称是否唯一
     * 
     * @param materialName 材质名称
     * @return 结果
     */
    @Override
    public boolean checkMaterialNameUnique(String materialName)
    {
        return tileMaterialMapper.checkMaterialNameUnique(materialName) == null;
    }

    /**
     * 新增材质信息
     * 
     * @param material 材质信息
     * @return 结果
     */
    @Override
    public int insertTileMaterial(TileMaterial material)
    {
        return tileMaterialMapper.insertTileMaterial(material);
    }

    /**
     * 修改材质信息
     * 
     * @param material 材质信息
     * @return 结果
     */
    @Override
    public int updateTileMaterial(TileMaterial material)
    {
        return tileMaterialMapper.updateTileMaterial(material);
    }

    /**
     * 删除材质信息
     * 
     * @param materialId 材质ID
     * @return 结果
     */
    @Override
    public int deleteTileMaterialById(Long materialId)
    {
        return tileMaterialMapper.deleteTileMaterialById(materialId);
    }

    /**
     * 批量删除材质信息
     * 
     * @param materialIds 需要删除的材质ID
     * @return 结果
     */
    @Override
    public int deleteTileMaterialByIds(Long[] materialIds)
    {
        return tileMaterialMapper.deleteTileMaterialByIds(materialIds);
    }
}
