package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileSupplierMapper;
import com.ruoyi.system.tile.domain.TileSupplier;
import com.ruoyi.system.tile.service.ITileSupplierService;
import com.ruoyi.common.utils.DateUtils;

/**
 * 供应商管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-02
 */
@Service
public class TileSupplierServiceImpl implements ITileSupplierService 
{
    @Autowired
    private TileSupplierMapper tileSupplierMapper;

    /**
     * 查询供应商
     * 
     * @param supplierId 供应商主键
     * @return 供应商
     */
    @Override
    public TileSupplier selectTileSupplierBySupplierId(Long supplierId)
    {
        return tileSupplierMapper.selectTileSupplierBySupplierId(supplierId);
    }

    /**
     * 查询供应商列表
     * 
     * @param tileSupplier 供应商
     * @return 供应商
     */
    @Override
    public List<TileSupplier> selectTileSupplierList(TileSupplier tileSupplier)
    {
        return tileSupplierMapper.selectTileSupplierList(tileSupplier);
    }

    /**
     * 新增供应商
     * 
     * @param tileSupplier 供应商
     * @return 结果
     */
    @Override
    public int insertTileSupplier(TileSupplier tileSupplier)
    {
        tileSupplier.setCreateTime(DateUtils.getNowDate());
        return tileSupplierMapper.insertTileSupplier(tileSupplier);
    }

    /**
     * 修改供应商
     * 
     * @param tileSupplier 供应商
     * @return 结果
     */
    @Override
    public int updateTileSupplier(TileSupplier tileSupplier)
    {
        return tileSupplierMapper.updateTileSupplier(tileSupplier);
    }

    /**
     * 批量删除供应商
     * 
     * @param supplierIds 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteTileSupplierBySupplierIds(Long[] supplierIds)
    {
        return tileSupplierMapper.deleteTileSupplierBySupplierIds(supplierIds);
    }

    /**
     * 删除供应商信息
     * 
     * @param supplierId 供应商主键
     * @return 结果
     */
    @Override
    public int deleteTileSupplierBySupplierId(Long supplierId)
    {
        return tileSupplierMapper.deleteTileSupplierBySupplierId(supplierId);
    }
}
