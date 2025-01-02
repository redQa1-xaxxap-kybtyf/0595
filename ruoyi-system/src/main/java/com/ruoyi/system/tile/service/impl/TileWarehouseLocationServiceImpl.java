package com.ruoyi.system.tile.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileWarehouseLocationMapper;
import com.ruoyi.system.tile.domain.TileWarehouseLocation;
import com.ruoyi.system.tile.service.ITileWarehouseLocationService;

/**
 * 货位Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@Service
public class TileWarehouseLocationServiceImpl implements ITileWarehouseLocationService 
{
    @Autowired
    private TileWarehouseLocationMapper tileWarehouseLocationMapper;

    /**
     * 查询货位
     * 
     * @param locationId 货位主键
     * @return 货位
     */
    @Override
    public TileWarehouseLocation selectTileWarehouseLocationByLocationId(Long locationId)
    {
        return tileWarehouseLocationMapper.selectTileWarehouseLocationByLocationId(locationId);
    }

    /**
     * 查询货位列表
     * 
     * @param tileWarehouseLocation 货位
     * @return 货位
     */
    @Override
    public List<TileWarehouseLocation> selectTileWarehouseLocationList(TileWarehouseLocation tileWarehouseLocation)
    {
        return tileWarehouseLocationMapper.selectTileWarehouseLocationList(tileWarehouseLocation);
    }

    /**
     * 根据仓库ID查询货位列表
     * 
     * @param warehouseId 仓库ID
     * @return 货位集合
     */
    @Override
    public List<TileWarehouseLocation> selectTileWarehouseLocationByWarehouseId(Long warehouseId)
    {
        return tileWarehouseLocationMapper.selectTileWarehouseLocationByWarehouseId(warehouseId);
    }

    /**
     * 新增货位
     * 
     * @param tileWarehouseLocation 货位
     * @return 结果
     */
    @Override
    public int insertTileWarehouseLocation(TileWarehouseLocation tileWarehouseLocation)
    {
        tileWarehouseLocation.setCreateTime(DateUtils.getNowDate());
        return tileWarehouseLocationMapper.insertTileWarehouseLocation(tileWarehouseLocation);
    }

    /**
     * 修改货位
     * 
     * @param tileWarehouseLocation 货位
     * @return 结果
     */
    @Override
    public int updateTileWarehouseLocation(TileWarehouseLocation tileWarehouseLocation)
    {
        tileWarehouseLocation.setUpdateTime(DateUtils.getNowDate());
        return tileWarehouseLocationMapper.updateTileWarehouseLocation(tileWarehouseLocation);
    }

    /**
     * 批量删除货位
     * 
     * @param locationIds 需要删除的货位主键
     * @return 结果
     */
    @Override
    public int deleteTileWarehouseLocationByLocationIds(Long[] locationIds)
    {
        return tileWarehouseLocationMapper.deleteTileWarehouseLocationByLocationIds(locationIds);
    }

    /**
     * 删除货位信息
     * 
     * @param locationId 货位主键
     * @return 结果
     */
    @Override
    public int deleteTileWarehouseLocationByLocationId(Long locationId)
    {
        return tileWarehouseLocationMapper.deleteTileWarehouseLocationByLocationId(locationId);
    }
}
