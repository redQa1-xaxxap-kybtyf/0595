package com.ruoyi.system.tile.service.impl;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.tile.mapper.TileSupplierPriceMapper;
import com.ruoyi.system.tile.domain.TileSupplierPrice;
import com.ruoyi.system.tile.service.ITileSupplierPriceService;

/**
 * 供应商价格Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-01-03
 */
@Service
public class TileSupplierPriceServiceImpl implements ITileSupplierPriceService 
{
    @Autowired
    private TileSupplierPriceMapper tileSupplierPriceMapper;

    /**
     * 查询供应商价格
     * 
     * @param priceId 供应商价格主键
     * @return 供应商价格
     */
    @Override
    public TileSupplierPrice selectTileSupplierPriceByPriceId(Long priceId)
    {
        return tileSupplierPriceMapper.selectTileSupplierPriceByPriceId(priceId);
    }

    /**
     * 查询供应商价格列表
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 供应商价格集合
     */
    @Override
    public List<TileSupplierPrice> selectTileSupplierPriceList(TileSupplierPrice tileSupplierPrice)
    {
        // 1. 获取原始数据列表
        List<TileSupplierPrice> list = tileSupplierPriceMapper.selectTileSupplierPriceList(tileSupplierPrice);
        
        // 2. 按供应商ID分组
        Map<Long, List<TileSupplierPrice>> groupedPrices = list.stream()
            .collect(Collectors.groupingBy(TileSupplierPrice::getSupplierId));
        
        // 3. 转换为前端需要的格式
        List<TileSupplierPrice> result = new ArrayList<>();
        
        groupedPrices.forEach((supplierId, prices) -> {
            if (!prices.isEmpty()) {
                // 创建供应商组
                TileSupplierPrice supplierGroup = new TileSupplierPrice();
                TileSupplierPrice firstPrice = prices.get(0);
                
                // 设置供应商信息
                supplierGroup.setSupplierId(supplierId);
                supplierGroup.setSupplierName(firstPrice.getSupplierName());
                
                // 设置品类列表
                List<TileSupplierPrice> categories = prices.stream()
                    .map(price -> {
                        TileSupplierPrice category = new TileSupplierPrice();
                        category.setPriceId(price.getPriceId());
                        category.setSupplierId(price.getSupplierId());
                        category.setCategoryName(price.getCategoryName());
                        category.setSpecification(price.getSpecification());
                        category.setModel(price.getModel());
                        category.setPiecePrice(price.getPiecePrice());
                        category.setSquarePrice(price.getSquarePrice());
                        category.setUnitPrice(price.getUnitPrice());
                        category.setStatus(price.getStatus());
                        category.setDelFlag(price.getDelFlag());
                        category.setCreateTime(price.getCreateTime());
                        category.setUpdateTime(price.getUpdateTime());
                        category.setRemark(price.getRemark());
                        return category;
                    })
                    .sorted((a, b) -> {
                        if (a.getCreateTime() == null && b.getCreateTime() == null) {
                            return 0;
                        }
                        if (a.getCreateTime() == null) {
                            return 1;
                        }
                        if (b.getCreateTime() == null) {
                            return -1;
                        }
                        return b.getCreateTime().compareTo(a.getCreateTime());
                    })
                    .collect(Collectors.toList());
                
                supplierGroup.setCategoryList(categories);
                result.add(supplierGroup);
            }
        });
        
        // 4. 按供应商名称排序
        result.sort((a, b) -> {
            if (a.getSupplierName() == null && b.getSupplierName() == null) {
                return 0;
            }
            if (a.getSupplierName() == null) {
                return 1;
            }
            if (b.getSupplierName() == null) {
                return -1;
            }
            return a.getSupplierName().compareTo(b.getSupplierName());
        });
        
        return result;
    }

    /**
     * 新增供应商价格
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 结果
     */
    @Override
    public int insertTileSupplierPrice(TileSupplierPrice tileSupplierPrice)
    {
        return tileSupplierPriceMapper.insertTileSupplierPrice(tileSupplierPrice);
    }

    /**
     * 批量新增供应商价格
     * 
     * @param prices 供应商价格列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTileSupplierPrices(List<TileSupplierPrice> prices)
    {
        if (prices == null || prices.isEmpty()) {
            return 0;
        }

        try {
            // 检查是否有重复的品类
            Set<String> uniqueKeys = new HashSet<>();
            for (TileSupplierPrice price : prices) {
                String key = String.format("%d-%s-%s-%s", 
                    price.getSupplierId(), 
                    price.getCategoryName(), 
                    price.getSpecification(), 
                    price.getModel());
                if (!uniqueKeys.add(key)) {
                    throw new RuntimeException("同一供应商下不能有重复的品类、规格和型号组合");
                }
            }

            // 插入新的价格记录
            int rows = 0;
            for (TileSupplierPrice price : prices) {
                price.setCreateTime(DateUtils.getNowDate());
                price.setUpdateTime(DateUtils.getNowDate());
                rows += tileSupplierPriceMapper.insertTileSupplierPrice(price);
            }
            return rows;
        } catch (RuntimeException e) {
            throw e;  // 直接抛出运行时异常
        } catch (Exception e) {
            throw new RuntimeException("新增供应商价格失败：" + e.getMessage());
        }
    }

    /**
     * 修改供应商价格
     * 
     * @param tileSupplierPrice 供应商价格
     * @return 结果
     */
    @Override
    public int updateTileSupplierPrice(TileSupplierPrice tileSupplierPrice)
    {
        return tileSupplierPriceMapper.updateTileSupplierPrice(tileSupplierPrice);
    }

    /**
     * 批量删除供应商价格
     * 
     * @param priceIds 需要删除的供应商价格主键
     * @return 结果
     */
    @Override
    public int deleteTileSupplierPriceByPriceIds(Long[] priceIds)
    {
        return tileSupplierPriceMapper.deleteTileSupplierPriceByPriceIds(priceIds);
    }

    /**
     * 删除供应商价格信息
     * 
     * @param priceId 供应商价格主键
     * @return 结果
     */
    @Override
    public int deleteTileSupplierPriceByPriceId(Long priceId)
    {
        return tileSupplierPriceMapper.deleteTileSupplierPriceByPriceId(priceId);
    }

    /**
     * 根据供应商ID查询价格列表
     * 
     * @param supplierId 供应商ID
     * @return 价格列表
     */
    @Override
    public List<TileSupplierPrice> selectTileSupplierPriceBySupplierId(Long supplierId)
    {
        return tileSupplierPriceMapper.selectTileSupplierPriceBySupplierId(supplierId);
    }

    /**
     * 批量修改供应商价格
     * 
     * @param prices 供应商价格列表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateTileSupplierPrices(List<TileSupplierPrice> prices)
    {
        if (prices == null || prices.isEmpty()) {
            return 0;
        }

        try {
            // 按供应商ID分组
            Map<Long, List<TileSupplierPrice>> supplierPrices = prices.stream()
                .collect(Collectors.groupingBy(TileSupplierPrice::getSupplierId));

            int rows = 0;
            // 对每个供应商分别处理
            for (Map.Entry<Long, List<TileSupplierPrice>> entry : supplierPrices.entrySet()) {
                Long supplierId = entry.getKey();
                List<TileSupplierPrice> priceList = entry.getValue();

                // 检查新记录中是否有重复的品类组合
                Set<String> newKeys = new HashSet<>();
                for (TileSupplierPrice price : priceList) {
                    String key = String.format("%s-%s-%s", 
                        price.getCategoryName(), 
                        price.getSpecification(), 
                        price.getModel());
                    if (!newKeys.add(key)) {
                        throw new RuntimeException("同一供应商下不能有重复的品类、规格和型号组合");
                    }
                }

                // 获取这个供应商的所有现有记录
                TileSupplierPrice query = new TileSupplierPrice();
                query.setSupplierId(supplierId);
                List<TileSupplierPrice> oldPrices = selectTileSupplierPriceList(query);

                // 将现有记录转换为Map，key为品类组合，value为记录
                Map<String, TileSupplierPrice> oldPriceMap = oldPrices.stream()
                    .collect(Collectors.toMap(
                        p -> String.format("%s-%s-%s", 
                            p.getCategoryName(), 
                            p.getSpecification(), 
                            p.getModel()),
                        p -> p
                    ));

                // 更新或插入记录
                for (TileSupplierPrice price : priceList) {
                    String key = String.format("%s-%s-%s", 
                        price.getCategoryName(), 
                        price.getSpecification(), 
                        price.getModel());
                    
                    TileSupplierPrice oldPrice = oldPriceMap.get(key);
                    if (oldPrice != null) {
                        // 更新已存在的记录
                        price.setPriceId(oldPrice.getPriceId());
                        price.setUpdateTime(DateUtils.getNowDate());
                        if (oldPrice.getCreateTime() != null) {
                            price.setCreateTime(oldPrice.getCreateTime());
                        }
                        rows += tileSupplierPriceMapper.updateTileSupplierPrice(price);
                        oldPriceMap.remove(key); // 从旧记录map中移除已处理的记录
                    } else {
                        // 插入新记录
                        price.setCreateTime(DateUtils.getNowDate());
                        price.setUpdateTime(DateUtils.getNowDate());
                        rows += tileSupplierPriceMapper.insertTileSupplierPrice(price);
                    }
                }

                // 删除不再使用的旧记录
                if (!oldPriceMap.isEmpty()) {
                    Long[] priceIds = oldPriceMap.values().stream()
                        .map(TileSupplierPrice::getPriceId)
                        .toArray(Long[]::new);
                    deleteTileSupplierPriceByPriceIds(priceIds);
                }
            }

            return rows;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("更新供应商价格失败：" + e.getMessage());
        }
    }
}
