package com.ruoyi.system.tile.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.tile.mapper.TileCustomerMapper;
import com.ruoyi.system.tile.domain.TileCustomer;
import com.ruoyi.system.tile.service.ITileCustomerService;

/**
 * 客户管理Service业务层处理
 * 
 * @author ruoyi
 */
@Service
public class TileCustomerServiceImpl implements ITileCustomerService 
{
    @Autowired
    private TileCustomerMapper tileCustomerMapper;

    /**
     * 查询客户
     * 
     * @param customerId 客户主键
     * @return 客户
     */
    @Override
    public TileCustomer selectTileCustomerByCustomerId(Long customerId)
    {
        return tileCustomerMapper.selectTileCustomerByCustomerId(customerId);
    }

    /**
     * 查询客户列表
     * 
     * @param tileCustomer 客户
     * @return 客户
     */
    @Override
    public List<TileCustomer> selectTileCustomerList(TileCustomer tileCustomer)
    {
        return tileCustomerMapper.selectTileCustomerList(tileCustomer);
    }

    /**
     * 新增客户
     * 
     * @param tileCustomer 客户
     * @return 结果
     */
    @Override
    public int insertTileCustomer(TileCustomer tileCustomer)
    {
        return tileCustomerMapper.insertTileCustomer(tileCustomer);
    }

    /**
     * 修改客户
     * 
     * @param tileCustomer 客户
     * @return 结果
     */
    @Override
    public int updateTileCustomer(TileCustomer tileCustomer)
    {
        return tileCustomerMapper.updateTileCustomer(tileCustomer);
    }

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的客户主键
     * @return 结果
     */
    @Override
    public int deleteTileCustomerByCustomerIds(Long[] customerIds)
    {
        return tileCustomerMapper.deleteTileCustomerByCustomerIds(customerIds);
    }

    /**
     * 删除客户信息
     * 
     * @param customerId 客户主键
     * @return 结果
     */
    @Override
    public int deleteTileCustomerByCustomerId(Long customerId)
    {
        return tileCustomerMapper.deleteTileCustomerByCustomerId(customerId);
    }
}
