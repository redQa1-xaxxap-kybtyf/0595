package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileWarehouseLocation;

/**
 * 货位Service接口
 * 
 * @author ruoyi
 * @date 2024-01-02
 */
public interface ITileWarehouseLocationService 
{
    /**
     * 查询货位
     * 
     * @param locationId 货位主键
     * @return 货位
     */
    public TileWarehouseLocation selectTileWarehouseLocationByLocationId(Long locationId);

    /**
     * 查询货位列表
     * 
     * @param tileWarehouseLocation 货位
     * @return 货位集合
     */
    public List<TileWarehouseLocation> selectTileWarehouseLocationList(TileWarehouseLocation tileWarehouseLocation);

    /**
     * 根据仓库ID查询货位列表
     * 
     * @param warehouseId 仓库ID
     * @return 货位集合
     */
    public List<TileWarehouseLocation> selectTileWarehouseLocationByWarehouseId(Long warehouseId);

    /**
     * 新增货位
     * 
     * @param tileWarehouseLocation 货位
     * @return 结果
     */
    public int insertTileWarehouseLocation(TileWarehouseLocation tileWarehouseLocation);

    /**
     * 修改货位
     * 
     * @param tileWarehouseLocation 货位
     * @return 结果
     */
    public int updateTileWarehouseLocation(TileWarehouseLocation tileWarehouseLocation);

    /**
     * 批量删除货位
     * 
     * @param locationIds 需要删除的货位主键集合
     * @return 结果
     */
    public int deleteTileWarehouseLocationByLocationIds(Long[] locationIds);

    /**
     * 删除货位信息
     * 
     * @param locationId 货位主键
     * @return 结果
     */
    public int deleteTileWarehouseLocationByLocationId(Long locationId);
}
