package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSupplier;

/**
 * 供应商管理Service接口
 * 
 * @author ruoyi
 * @date 2025-01-02
 */
public interface ITileSupplierService 
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
     * 批量删除供应商
     * 
     * @param supplierIds 需要删除的供应商主键集合
     * @return 结果
     */
    public int deleteTileSupplierBySupplierIds(Long[] supplierIds);

    /**
     * 删除供应商信息
     * 
     * @param supplierId 供应商主键
     * @return 结果
     */
    public int deleteTileSupplierBySupplierId(Long supplierId);
}
