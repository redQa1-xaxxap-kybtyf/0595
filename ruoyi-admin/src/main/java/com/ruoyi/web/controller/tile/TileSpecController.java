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
import com.ruoyi.system.tile.domain.TileSpec;
import com.ruoyi.system.tile.service.ITileSpecService;

/**
 * 规格尺寸管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/spec")
public class TileSpecController extends BaseController
{
    @Autowired
    private ITileSpecService tileSpecService;

    /**
     * 查询规格尺寸列表
     */
    @PreAuthorize("@ss.hasPermi('tile:spec:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileSpec spec)
    {
        startPage();
        List<TileSpec> list = tileSpecService.selectTileSpecList(spec);
        return getDataTable(list);
    }

    /**
     * 导出规格尺寸列表
     */
    @PreAuthorize("@ss.hasPermi('tile:spec:export')")
    @Log(title = "规格尺寸", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileSpec spec)
    {
        List<TileSpec> list = tileSpecService.selectTileSpecList(spec);
        ExcelUtil<TileSpec> util = new ExcelUtil<TileSpec>(TileSpec.class);
        util.exportExcel(response, list, "规格尺寸数据");
    }

    /**
     * 获取规格尺寸详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:spec:query')")
    @GetMapping(value = "/{specId}")
    public AjaxResult getInfo(@PathVariable("specId") Long specId)
    {
        return success(tileSpecService.selectTileSpecById(specId));
    }

    /**
     * 新增规格尺寸
     */
    @PreAuthorize("@ss.hasPermi('tile:spec:add')")
    @Log(title = "规格尺寸", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TileSpec spec)
    {
        if (!tileSpecService.checkSpecNameUnique(spec.getSpecName()))
        {
            return error("新增规格'" + spec.getSpecName() + "'失败，规格名称已存在");
        }
        spec.setCreateBy(getUsername());
        return toAjax(tileSpecService.insertTileSpec(spec));
    }

    /**
     * 修改规格尺寸
     */
    @PreAuthorize("@ss.hasPermi('tile:spec:edit')")
    @Log(title = "规格尺寸", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TileSpec spec)
    {
        TileSpec oldSpec = tileSpecService.selectTileSpecById(spec.getSpecId());
        if (!oldSpec.getSpecName().equals(spec.getSpecName()) && !tileSpecService.checkSpecNameUnique(spec.getSpecName()))
        {
            return error("修改规格'" + spec.getSpecName() + "'失败，规格名称已存在");
        }
        spec.setUpdateBy(getUsername());
        return toAjax(tileSpecService.updateTileSpec(spec));
    }

    /**
     * 删除规格尺寸
     */
    @PreAuthorize("@ss.hasPermi('tile:spec:remove')")
    @Log(title = "规格尺寸", businessType = BusinessType.DELETE)
    @DeleteMapping("/{specIds}")
    public AjaxResult remove(@PathVariable Long[] specIds)
    {
        return toAjax(tileSpecService.deleteTileSpecByIds(specIds));
    }
}
