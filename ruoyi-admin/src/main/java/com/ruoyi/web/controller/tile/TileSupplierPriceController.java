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
import com.ruoyi.system.tile.domain.TileSupplierPrice;
import com.ruoyi.system.tile.service.ITileSupplierPriceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商价格Controller
 * 
 * @author ruoyi
 * @date 2025-01-03
 */
@RestController
@RequestMapping("/tile/price")
public class TileSupplierPriceController extends BaseController
{
    @Autowired
    private ITileSupplierPriceService tileSupplierPriceService;

    /**
     * 查询供应商价格列表
     */
    @PreAuthorize("@ss.hasPermi('tile:price:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileSupplierPrice tileSupplierPrice)
    {
        startPage();
        List<TileSupplierPrice> list = tileSupplierPriceService.selectTileSupplierPriceList(tileSupplierPrice);
        return getDataTable(list);
    }

    /**
     * 导出供应商价格列表
     */
    @PreAuthorize("@ss.hasPermi('tile:price:export')")
    @Log(title = "供应商价格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileSupplierPrice tileSupplierPrice)
    {
        List<TileSupplierPrice> list = tileSupplierPriceService.selectTileSupplierPriceList(tileSupplierPrice);
        ExcelUtil<TileSupplierPrice> util = new ExcelUtil<TileSupplierPrice>(TileSupplierPrice.class);
        util.exportExcel(response, list, "供应商价格数据");
    }

    /**
     * 获取供应商价格详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:price:query')")
    @GetMapping(value = "/{priceId}")
    public AjaxResult getInfo(@PathVariable("priceId") Long priceId)
    {
        return success(tileSupplierPriceService.selectTileSupplierPriceByPriceId(priceId));
    }

    /**
     * 获取供应商的价格列表
     */
    @PreAuthorize("@ss.hasPermi('tile:price:query')")
    @GetMapping(value = "/supplier/{supplierId}")
    public AjaxResult getSupplierPrices(@PathVariable("supplierId") Long supplierId)
    {
        return success(tileSupplierPriceService.selectTileSupplierPriceBySupplierId(supplierId));
    }

    /**
     * 新增供应商价格
     */
    @PreAuthorize("@ss.hasPermi('tile:price:add')")
    @Log(title = "供应商价格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileSupplierPrice tileSupplierPrice)
    {
        return toAjax(tileSupplierPriceService.insertTileSupplierPrice(tileSupplierPrice));
    }

    /**
     * 批量新增供应商价格
     */
    @PreAuthorize("@ss.hasPermi('tile:price:add')")
    @Log(title = "供应商价格", businessType = BusinessType.INSERT)
    @PostMapping("/batch")
    public AjaxResult addBatch(@RequestBody List<TileSupplierPrice> prices)
    {
        return toAjax(tileSupplierPriceService.insertTileSupplierPrices(prices));
    }

    /**
     * 修改供应商价格
     */
    @PreAuthorize("@ss.hasPermi('tile:price:edit')")
    @Log(title = "供应商价格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody List<TileSupplierPrice> prices)
    {
        return toAjax(tileSupplierPriceService.updateTileSupplierPrices(prices));
    }

    /**
     * 删除供应商价格
     */
    @PreAuthorize("@ss.hasPermi('tile:price:remove')")
    @Log(title = "供应商价格", businessType = BusinessType.DELETE)
	@DeleteMapping("/{priceIds}")
    public AjaxResult remove(@PathVariable Long[] priceIds)
    {
        return toAjax(tileSupplierPriceService.deleteTileSupplierPriceByPriceIds(priceIds));
    }
}
