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
import com.ruoyi.system.tile.domain.TileStockIn;
import com.ruoyi.system.tile.service.ITileStockInService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入库单Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/stock/in")
public class TileStockInController extends BaseController
{
    @Autowired
    private ITileStockInService tileStockInService;

    /**
     * 查询入库单列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileStockIn tileStockIn)
    {
        startPage();
        List<TileStockIn> list = tileStockInService.selectTileStockInList(tileStockIn);
        return getDataTable(list);
    }

    /**
     * 导出入库单列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:export')")
    @Log(title = "入库单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileStockIn tileStockIn)
    {
        List<TileStockIn> list = tileStockInService.selectTileStockInList(tileStockIn);
        ExcelUtil<TileStockIn> util = new ExcelUtil<TileStockIn>(TileStockIn.class);
        util.exportExcel(response, list, "入库单数据");
    }

    /**
     * 获取入库单详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:query')")
    @GetMapping(value = "/{inId}")
    public AjaxResult getInfo(@PathVariable("inId") Long inId)
    {
        return success(tileStockInService.selectTileStockInByInId(inId));
    }

    /**
     * 新增入库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:add')")
    @Log(title = "入库单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileStockIn tileStockIn)
    {
        return toAjax(tileStockInService.insertTileStockIn(tileStockIn));
    }

    /**
     * 修改入库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileStockIn tileStockIn)
    {
        return toAjax(tileStockInService.updateTileStockIn(tileStockIn));
    }

    /**
     * 删除入库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:remove')")
    @Log(title = "入库单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{inIds}")
    public AjaxResult remove(@PathVariable Long[] inIds)
    {
        return toAjax(tileStockInService.deleteTileStockInByInIds(inIds));
    }

    /**
     * 提交入库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping("/submit/{inId}")
    public AjaxResult submit(@PathVariable("inId") Long inId)
    {
        return toAjax(tileStockInService.submitTileStockIn(inId));
    }

    /**
     * 取消入库单
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:in:edit')")
    @Log(title = "入库单", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{inId}")
    public AjaxResult cancel(@PathVariable("inId") Long inId)
    {
        return toAjax(tileStockInService.cancelTileStockIn(inId));
    }
}
