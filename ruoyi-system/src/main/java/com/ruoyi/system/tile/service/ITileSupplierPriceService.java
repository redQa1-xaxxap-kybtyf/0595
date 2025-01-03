package com.ruoyi.system.tile.service;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSupplierPrice;

/**
 * 供应商价格Service接口
 * 
 * @author ruoyi
 * @date 2025-01-03
 */
public interface ITileSupplierPriceService 
{
    /**
     * 查询供应商价格
     * 
     * @param priceId 供应商价格主键
     * @return 供应商价格
     */
    public TileSupplierPrice selectTileSupplierPriceByPriceId(Long priceId);

    /**
     * 查询供应商价格列表
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 供应商价格集合
     */
    public List<TileSupplierPrice> selectTileSupplierPriceList(TileSupplierPrice tileSupplierPrice);

    /**
     * 新增供应商价格
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 结果
     */
    public int insertTileSupplierPrice(TileSupplierPrice tileSupplierPrice);

    /**
     * 批量新增供应商价格
     * 
     * @param prices 供应商价格列表
     * @return 结果
     */
    public int insertTileSupplierPrices(List<TileSupplierPrice> prices);

    /**
     * 修改供应商价格
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 结果
     */
    public int updateTileSupplierPrice(TileSupplierPrice tileSupplierPrice);

    /**
     * 批量修改供应商价格
     * 
     * @param prices 供应商价格列表
     * @return 结果
     */
    public int updateTileSupplierPrices(List<TileSupplierPrice> prices);

    /**
     * 批量删除供应商价格
     * 
     * @param priceIds 需要删除的供应商价格主键集合
     * @return 结果
     */
    public int deleteTileSupplierPriceByPriceIds(Long[] priceIds);

    /**
     * 删除供应商价格信息
     * 
     * @param priceId 供应商价格主键
     * @return 结果
     */
    public int deleteTileSupplierPriceByPriceId(Long priceId);

    /**
     * 根据供应商ID查询价格列表
     * 
     * @param supplierId 供应商ID
     * @return 价格列表
     */
    public List<TileSupplierPrice> selectTileSupplierPriceBySupplierId(Long supplierId);
}
