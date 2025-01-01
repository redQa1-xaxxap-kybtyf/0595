package com.ruoyi.system.tile.controller;

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
import com.ruoyi.system.tile.domain.TileStockOut;
import com.ruoyi.system.tile.service.ITileStockOutService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库单Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/stock/out")
public class TileStockOutController extends BaseController
{
    @Autowired
    private ITileStockOutService tileStockOutService;

    /**
     * 查询出库单列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileStockOut tileStockOut)
    {
        startPage();
        List<TileStockOut> list = tileStockOutService.selectTileStockOutList(tileStockOut);
        return getDataTable(list);
    }

    /**
     * 导出出库单列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:export')")
    @Log(title = "出库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileStockOut tileStockOut)
    {
        List<TileStockOut> list = tileStockOutService.selectTileStockOutList(tileStockOut);
        ExcelUtil<TileStockOut> util = new ExcelUtil<TileStockOut>(TileStockOut.class);
        util.exportExcel(response, list, "出库单数据");
    }

    /**
     * 获取出库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:query')")
    @GetMapping(value = "/{outId}")
    public AjaxResult getInfo(@PathVariable("outId") Long outId)
    {
        return success(tileStockOutService.selectTileStockOutByOutId(outId));
    }

    /**
     * 新增出库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:add')")
    @Log(title = "出库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileStockOut tileStockOut)
    {
        return toAjax(tileStockOutService.insertTileStockOut(tileStockOut));
    }

    /**
     * 修改出库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileStockOut tileStockOut)
    {
        return toAjax(tileStockOutService.updateTileStockOut(tileStockOut));
    }

    /**
     * 删除出库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:remove')")
    @Log(title = "出库单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{outIds}")
    public AjaxResult remove(@PathVariable Long[] outIds)
    {
        return toAjax(tileStockOutService.deleteTileStockOutByOutIds(outIds));
    }

    /**
     * 提交出库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{outId}")
    public AjaxResult submit(@PathVariable("outId") Long outId)
    {
        return toAjax(tileStockOutService.submitTileStockOut(outId));
    }

    /**
     * 取消出库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:edit')")
    @Log(title = "出库单", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{outId}")
    public AjaxResult cancel(@PathVariable("outId") Long outId)
    {
        return toAjax(tileStockOutService.cancelTileStockOut(outId));
    }
}
