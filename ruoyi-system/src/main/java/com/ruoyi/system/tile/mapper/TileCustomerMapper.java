package com.ruoyi.system.tile.mapper;

import java.util.List;
import com.ruoyi.system.tile.domain.TileCustomer;

/**
 * 客户管理Mapper接口
 * 
 * @author ruoyi
 */
public interface TileCustomerMapper 
{
    /**
     * 查询客户
     * 
     * @param customerId 客户主键
     * @return 客户
     */
    public TileCustomer selectTileCustomerByCustomerId(Long customerId);

    /**
     * 查询客户列表
     * 
     * @param tileCustomer 客户
     * @return 客户集合
     */
    public List<TileCustomer> selectTileCustomerList(TileCustomer tileCustomer);

    /**
     * 新增客户
     * 
     * @param tileCustomer 客户
     * @return 结果
     */
    public int insertTileCustomer(TileCustomer tileCustomer);

    /**
     * 修改客户
     * 
     * @param tileCustomer 客户
     * @return 结果
     */
    public int updateTileCustomer(TileCustomer tileCustomer);

    /**
     * 删除客户
     * 
     * @param customerId 客户主键
     * @return 结果
     */
    public int deleteTileCustomerByCustomerId(Long customerId);

    /**
     * 批量删除客户
     * 
     * @param customerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTileCustomerByCustomerIds(Long[] customerIds);
}
