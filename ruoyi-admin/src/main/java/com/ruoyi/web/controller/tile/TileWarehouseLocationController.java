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
import com.ruoyi.system.tile.domain.TileWarehouseLocation;
import com.ruoyi.system.tile.service.ITileWarehouseLocationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 货位Controller
 * 
 * @author ruoyi
 * @date 2024-01-02
 */
@RestController
@RequestMapping("/tile/location")
public class TileWarehouseLocationController extends BaseController
{
    @Autowired
    private ITileWarehouseLocationService tileWarehouseLocationService;

    /**
     * 查询货位列表
     */
    @PreAuthorize("@ss.hasPermi('tile:location:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileWarehouseLocation tileWarehouseLocation)
    {
        startPage();
        List<TileWarehouseLocation> list = tileWarehouseLocationService.selectTileWarehouseLocationList(tileWarehouseLocation);
        return getDataTable(list);
    }

    /**
     * 根据仓库ID获取货位列表
     */
    @PreAuthorize("@ss.hasPermi('tile:location:list')")
    @GetMapping("/list/{warehouseId}")
    public AjaxResult listByWarehouseId(@PathVariable("warehouseId") Long warehouseId)
    {
        List<TileWarehouseLocation> list = tileWarehouseLocationService.selectTileWarehouseLocationByWarehouseId(warehouseId);
        return success(list);
    }

    /**
     * 导出货位列表
     */
    @PreAuthorize("@ss.hasPermi('tile:location:export')")
    @Log(title = "货位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileWarehouseLocation tileWarehouseLocation)
    {
        List<TileWarehouseLocation> list = tileWarehouseLocationService.selectTileWarehouseLocationList(tileWarehouseLocation);
        ExcelUtil<TileWarehouseLocation> util = new ExcelUtil<TileWarehouseLocation>(TileWarehouseLocation.class);
        util.exportExcel(response, list, "货位数据");
    }

    /**
     * 获取货位详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:location:query')")
    @GetMapping(value = "/{locationId}")
    public AjaxResult getInfo(@PathVariable("locationId") Long locationId)
    {
        return success(tileWarehouseLocationService.selectTileWarehouseLocationByLocationId(locationId));
    }

    /**
     * 新增货位
     */
    @PreAuthorize("@ss.hasPermi('tile:location:add')")
    @Log(title = "货位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileWarehouseLocation tileWarehouseLocation)
    {
        return toAjax(tileWarehouseLocationService.insertTileWarehouseLocation(tileWarehouseLocation));
    }

    /**
     * 修改货位
     */
    @PreAuthorize("@ss.hasPermi('tile:location:edit')")
    @Log(title = "货位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileWarehouseLocation tileWarehouseLocation)
    {
        return toAjax(tileWarehouseLocationService.updateTileWarehouseLocation(tileWarehouseLocation));
    }

    /**
     * 删除货位
     */
    @PreAuthorize("@ss.hasPermi('tile:location:remove')")
    @Log(title = "货位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{locationIds}")
    public AjaxResult remove(@PathVariable Long[] locationIds)
    {
        return toAjax(tileWarehouseLocationService.deleteTileWarehouseLocationByLocationIds(locationIds));
    }
}
