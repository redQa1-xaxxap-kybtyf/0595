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
import com.ruoyi.system.tile.domain.TileWarehouse;
import com.ruoyi.system.tile.service.ITileWarehouseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 仓库Controller
 * 
 * @author ruoyi
 * @date 2025-01-01
 */
@RestController
@RequestMapping("/tile/warehouse")
public class TileWarehouseController extends BaseController
{
    @Autowired
    private ITileWarehouseService tileWarehouseService;

    /**
     * 查询仓库列表
     */
    @PreAuthorize("@ss.hasPermi('tile:warehouse:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileWarehouse tileWarehouse)
    {
        startPage();
        List<TileWarehouse> list = tileWarehouseService.selectTileWarehouseList(tileWarehouse);
        return getDataTable(list);
    }

    /**
     * 导出仓库列表
     */
    @PreAuthorize("@ss.hasPermi('tile:warehouse:export')")
    @Log(title = "仓库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileWarehouse tileWarehouse)
    {
        List<TileWarehouse> list = tileWarehouseService.selectTileWarehouseList(tileWarehouse);
        ExcelUtil<TileWarehouse> util = new ExcelUtil<TileWarehouse>(TileWarehouse.class);
        util.exportExcel(response, list, "仓库数据");
    }

    /**
     * 获取仓库详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:warehouse:query')")
    @GetMapping(value = "/{warehouseId}")
    public AjaxResult getInfo(@PathVariable("warehouseId") Long warehouseId)
    {
        return success(tileWarehouseService.selectTileWarehouseByWarehouseId(warehouseId));
    }

    /**
     * 新增仓库
     */
    @PreAuthorize("@ss.hasPermi('tile:warehouse:add')")
    @Log(title = "仓库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileWarehouse tileWarehouse)
    {
        return toAjax(tileWarehouseService.insertTileWarehouse(tileWarehouse));
    }

    /**
     * 修改仓库
     */
    @PreAuthorize("@ss.hasPermi('tile:warehouse:edit')")
    @Log(title = "仓库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileWarehouse tileWarehouse)
    {
        return toAjax(tileWarehouseService.updateTileWarehouse(tileWarehouse));
    }

    /**
     * 删除仓库
     */
    @PreAuthorize("@ss.hasPermi('tile:warehouse:remove')")
    @Log(title = "仓库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{warehouseIds}")
    public AjaxResult remove(@PathVariable Long[] warehouseIds)
    {
        return toAjax(tileWarehouseService.deleteTileWarehouseByWarehouseIds(warehouseIds));
    }
}
