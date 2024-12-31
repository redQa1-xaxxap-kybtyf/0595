package com.ruoyi.web.controller.tile;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.tile.domain.TileSurface;
import com.ruoyi.system.tile.service.ITileSurfaceService;

/**
 * 表面处理管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/surface")
public class TileSurfaceController extends BaseController
{
    @Autowired
    private ITileSurfaceService tileSurfaceService;

    /**
     * 查询表面处理列表
     */
    @PreAuthorize("@ss.hasPermi('tile:surface:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileSurface surface)
    {
        startPage();
        List<TileSurface> list = tileSurfaceService.selectTileSurfaceList(surface);
        return getDataTable(list);
    }

    /**
     * 导出表面处理列表
     */
    @PreAuthorize("@ss.hasPermi('tile:surface:export')")
    @Log(title = "表面处理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileSurface surface)
    {
        List<TileSurface> list = tileSurfaceService.selectTileSurfaceList(surface);
        ExcelUtil<TileSurface> util = new ExcelUtil<TileSurface>(TileSurface.class);
        util.exportExcel(response, list, "表面处理数据");
    }

    /**
     * 获取表面处理详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:surface:query')")
    @GetMapping(value = "/{surfaceId}")
    public AjaxResult getInfo(@PathVariable("surfaceId") Long surfaceId)
    {
        return success(tileSurfaceService.selectTileSurfaceById(surfaceId));
    }

    /**
     * 新增表面处理
     */
    @PreAuthorize("@ss.hasPermi('tile:surface:add')")
    @Log(title = "表面处理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TileSurface surface)
    {
        if (!tileSurfaceService.checkSurfaceNameUnique(surface.getSurfaceName()))
        {
            return error("新增表面处理'" + surface.getSurfaceName() + "'失败，表面处理名称已存在");
        }
        surface.setCreateBy(getUsername());
        return toAjax(tileSurfaceService.insertTileSurface(surface));
    }

    /**
     * 修改表面处理
     */
    @PreAuthorize("@ss.hasPermi('tile:surface:edit')")
    @Log(title = "表面处理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TileSurface surface)
    {
        TileSurface oldSurface = tileSurfaceService.selectTileSurfaceById(surface.getSurfaceId());
        if (!oldSurface.getSurfaceName().equals(surface.getSurfaceName()) && !tileSurfaceService.checkSurfaceNameUnique(surface.getSurfaceName()))
        {
            return error("修改表面处理'" + surface.getSurfaceName() + "'失败，表面处理名称已存在");
        }
        surface.setUpdateBy(getUsername());
        return toAjax(tileSurfaceService.updateTileSurface(surface));
    }

    /**
     * 删除表面处理
     */
    @PreAuthorize("@ss.hasPermi('tile:surface:remove')")
    @Log(title = "表面处理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{surfaceIds}")
    public AjaxResult remove(@PathVariable Long[] surfaceIds)
    {
        return toAjax(tileSurfaceService.deleteTileSurfaceByIds(surfaceIds));
    }
}
