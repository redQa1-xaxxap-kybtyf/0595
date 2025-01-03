package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileSupplierPrice;

/**
 * 供应商价格Mapper接口
 * 
 * @author ruoyi
 * @date 2025-01-03
 */
public interface TileSupplierPriceMapper 
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
     * 修改供应商价格
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 结果
     */
    public int updateTileSupplierPrice(TileSupplierPrice tileSupplierPrice);

    /**
     * 删除供应商价格
     * 
     * @param priceId 供应商价格主键
     * @return 结果
     */
    public int deleteTileSupplierPriceByPriceId(Long priceId);

    /**
     * 批量删除供应商价格
     * 
     * @param priceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileSupplierPriceByPriceIds(Long[] priceIds);

    /**
     * 根据供应商ID查询价格列表
     * 
     * @param supplierId 供应商ID
     * @return 价格列表
     */
    public List<TileSupplierPrice> selectTileSupplierPriceBySupplierId(Long supplierId);
}
