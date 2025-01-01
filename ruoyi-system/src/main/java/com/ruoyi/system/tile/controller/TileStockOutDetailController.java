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
import com.ruoyi.system.tile.domain.TileStockOutDetail;
import com.ruoyi.system.tile.service.ITileStockOutDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 出库单明细Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/stock/out/detail")
public class TileStockOutDetailController extends BaseController
{
    @Autowired
    private ITileStockOutDetailService tileStockOutDetailService;

    /**
     * 查询出库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileStockOutDetail tileStockOutDetail)
    {
        startPage();
        List<TileStockOutDetail> list = tileStockOutDetailService.selectTileStockOutDetailList(tileStockOutDetail);
        return getDataTable(list);
    }

    /**
     * 导出出库单明细列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:export')")
    @Log(title = "出库单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileStockOutDetail tileStockOutDetail)
    {
        List<TileStockOutDetail> list = tileStockOutDetailService.selectTileStockOutDetailList(tileStockOutDetail);
        ExcelUtil<TileStockOutDetail> util = new ExcelUtil<TileStockOutDetail>(TileStockOutDetail.class);
        util.exportExcel(response, list, "出库单明细数据");
    }

    /**
     * 获取出库单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(tileStockOutDetailService.selectTileStockOutDetailByDetailId(detailId));
    }

    /**
     * 根据出库单ID查询明细
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:query')")
    @GetMapping(value = "/out/{outId}")
    public AjaxResult getDetailsByOutId(@PathVariable("outId") Long outId)
    {
        return success(tileStockOutDetailService.selectTileStockOutDetailByOutId(outId));
    }

    /**
     * 新增出库单明细
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:add')")
    @Log(title = "出库单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileStockOutDetail tileStockOutDetail)
    {
        return toAjax(tileStockOutDetailService.insertTileStockOutDetail(tileStockOutDetail));
    }

    /**
     * 修改出库单明细
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:edit')")
    @Log(title = "出库单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileStockOutDetail tileStockOutDetail)
    {
        return toAjax(tileStockOutDetailService.updateTileStockOutDetail(tileStockOutDetail));
    }

    /**
     * 删除出库单明细
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:out:remove')")
    @Log(title = "出库单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(tileStockOutDetailService.deleteTileStockOutDetailByDetailIds(detailIds));
    }
}
