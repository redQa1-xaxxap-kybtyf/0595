package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSupplier;

/**
 * 供应商Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-02
 */
public interface TileSupplierMapper 
{
    /**
     * 查询供应商
     * 
     * @param supplierId 供应商主键
     * @return 供应商
     */
    public TileSupplier selectTileSupplierBySupplierId(Long supplierId);

    /**
     * 查询供应商列表
     * 
     * @param tileSupplier 供应商
     * @return 供应商集合
     */
    public List<TileSupplier> selectTileSupplierList(TileSupplier tileSupplier);

    /**
     * 新增供应商
     * 
     * @param tileSupplier 供应商
     * @return 结果
     */
    public int insertTileSupplier(TileSupplier tileSupplier);

    /**
     * 修改供应商
     * 
     * @param tileSupplier 供应商
     * @return 结果
     */
    public int updateTileSupplier(TileSupplier tileSupplier);

    /**
     * 删除供应商
     * 
     * @param supplierId 供应商主键
     * @return 结果
     */
    public int deleteTileSupplierBySupplierId(Long supplierId);

    /**
     * 批量删除供应商
     * 
     * @param supplierIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileSupplierBySupplierIds(Long[] supplierIds);
}
