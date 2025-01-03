package com.ruoyi.web.controller.tile;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.tile.domain.TileCustomer;
import com.ruoyi.system.tile.service.ITileCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户管理Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/customer")
public class TileCustomerController extends BaseController
{
    @Autowired
    private ITileCustomerService tileCustomerService;

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('tile:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileCustomer tileCustomer)
    {
        startPage();
        List<TileCustomer> list = tileCustomerService.selectTileCustomerList(tileCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户列表
     */
    @PreAuthorize("@ss.hasPermi('tile:customer:export')")
    @Log(title = "客户管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileCustomer tileCustomer)
    {
        List<TileCustomer> list = tileCustomerService.selectTileCustomerList(tileCustomer);
        ExcelUtil<TileCustomer> util = new ExcelUtil<TileCustomer>(TileCustomer.class);
        util.exportExcel(response, list, "客户数据");
    }

    /**
     * 获取客户详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:customer:query')")
    @GetMapping(value = "/{customerId}")
    public AjaxResult getInfo(@PathVariable("customerId") Long customerId)
    {
        return success(tileCustomerService.selectTileCustomerByCustomerId(customerId));
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('tile:customer:add')")
    @Log(title = "客户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileCustomer tileCustomer)
    {
        return toAjax(tileCustomerService.insertTileCustomer(tileCustomer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('tile:customer:edit')")
    @Log(title = "客户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileCustomer tileCustomer)
    {
        return toAjax(tileCustomerService.updateTileCustomer(tileCustomer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('tile:customer:remove')")
    @Log(title = "客户管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{customerIds}")
    public AjaxResult remove(@PathVariable Long[] customerIds)
    {
        return toAjax(tileCustomerService.deleteTileCustomerByCustomerIds(customerIds));
    }
}
