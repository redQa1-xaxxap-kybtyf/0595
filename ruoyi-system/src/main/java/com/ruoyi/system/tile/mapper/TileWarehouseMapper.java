package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileWarehouse;

/**
 * 仓库Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
public interface TileWarehouseMapper 
{
    /**
     * 查询仓库
     * 
     * @param warehouseId 仓库主键
     * @return 仓库
     */
    public TileWarehouse selectTileWarehouseByWarehouseId(Long warehouseId);

    /**
     * 查询仓库列表
     * 
     * @param tileWarehouse 仓库
     * @return 仓库集合
     */
    public List<TileWarehouse> selectTileWarehouseList(TileWarehouse tileWarehouse);

    /**
     * 新增仓库
     * 
     * @param tileWarehouse 仓库
     * @return 结果
     */
    public int insertTileWarehouse(TileWarehouse tileWarehouse);

    /**
     * 修改仓库
     * 
     * @param tileWarehouse 仓库
     * @return 结果
     */
    public int updateTileWarehouse(TileWarehouse tileWarehouse);

    /**
     * 删除仓库
     * 
     * @param warehouseId 仓库主键
     * @return 结果
     */
    public int deleteTileWarehouseByWarehouseId(Long warehouseId);

    /**
     * 批量删除仓库
     * 
     * @param warehouseIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileWarehouseByWarehouseIds(Long[] warehouseIds);
}
