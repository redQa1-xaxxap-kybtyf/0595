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
import com.ruoyi.system.tile.domain.TileStock;
import com.ruoyi.system.tile.service.ITileStockService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存管理Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/stock")
public class TileStockController extends BaseController
{
    @Autowired
    private ITileStockService tileStockService;

    /**
     * 查询库存管理列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileStock tileStock)
    {
        startPage();
        List<TileStock> list = tileStockService.selectTileStockList(tileStock);
        return getDataTable(list);
    }

    /**
     * 导出库存管理列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:export')")
    @Log(title = "库存管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileStock tileStock)
    {
        List<TileStock> list = tileStockService.selectTileStockList(tileStock);
        ExcelUtil<TileStock> util = new ExcelUtil<TileStock>(TileStock.class);
        util.exportExcel(response, list, "库存管理数据");
    }

    /**
     * 获取库存管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:query')")
    @GetMapping(value = "/{stockId}")
    public AjaxResult getInfo(@PathVariable("stockId") Long stockId)
    {
        return success(tileStockService.selectTileStockByStockId(stockId));
    }

    /**
     * 新增库存管理
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:add')")
    @Log(title = "库存管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileStock tileStock)
    {
        return toAjax(tileStockService.insertTileStock(tileStock));
    }

    /**
     * 修改库存管理
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:edit')")
    @Log(title = "库存管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileStock tileStock)
    {
        return toAjax(tileStockService.updateTileStock(tileStock));
    }

    /**
     * 删除库存管理
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:remove')")
    @Log(title = "库存管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{stockIds}")
    public AjaxResult remove(@PathVariable Long[] stockIds)
    {
        return toAjax(tileStockService.deleteTileStockByStockIds(stockIds));
    }
}
