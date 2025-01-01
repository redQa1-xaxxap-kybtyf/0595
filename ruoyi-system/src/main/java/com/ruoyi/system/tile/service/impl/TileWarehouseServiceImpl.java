package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileWarehouseMapper;
import com.ruoyi.system.tile.domain.TileWarehouse;
import com.ruoyi.system.tile.service.ITileWarehouseService;

/**
 * 仓库Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Service
public class TileWarehouseServiceImpl implements ITileWarehouseService 
{
    @Autowired
    private TileWarehouseMapper tileWarehouseMapper;

    /**
     * 查询仓库
     * 
     * @param warehouseId 仓库主键
     * @return 仓库
     */
    @Override
    public TileWarehouse selectTileWarehouseByWarehouseId(Long warehouseId)
    {
        return tileWarehouseMapper.selectTileWarehouseByWarehouseId(warehouseId);
    }

    /**
     * 查询仓库列表
     * 
     * @param tileWarehouse 仓库
     * @return 仓库
     */
    @Override
    public List<TileWarehouse> selectTileWarehouseList(TileWarehouse tileWarehouse)
    {
        return tileWarehouseMapper.selectTileWarehouseList(tileWarehouse);
    }

    /**
     * 新增仓库
     * 
     * @param tileWarehouse 仓库
     * @return 结果
     */
    @Override
    public int insertTileWarehouse(TileWarehouse tileWarehouse)
    {
        return tileWarehouseMapper.insertTileWarehouse(tileWarehouse);
    }

    /**
     * 修改仓库
     * 
     * @param tileWarehouse 仓库
     * @return 结果
     */
    @Override
    public int updateTileWarehouse(TileWarehouse tileWarehouse)
    {
        return tileWarehouseMapper.updateTileWarehouse(tileWarehouse);
    }

    /**
     * 批量删除仓库
     * 
     * @param warehouseIds 需要删除的仓库主键
     * @return 结果
     */
    @Override
    public int deleteTileWarehouseByWarehouseIds(Long[] warehouseIds)
    {
        return tileWarehouseMapper.deleteTileWarehouseByWarehouseIds(warehouseIds);
    }

    /**
     * 删除仓库信息
     * 
     * @param warehouseId 仓库主键
     * @return 结果
     */
    @Override
    public int deleteTileWarehouseByWarehouseId(Long warehouseId)
    {
        return tileWarehouseMapper.deleteTileWarehouseByWarehouseId(warehouseId);
    }
}
