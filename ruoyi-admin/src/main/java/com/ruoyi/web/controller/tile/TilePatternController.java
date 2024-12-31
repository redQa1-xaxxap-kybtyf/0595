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
import com.ruoyi.system.tile.domain.TilePattern;
import com.ruoyi.system.tile.service.ITilePatternService;

/**
 * 图案管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/pattern")
public class TilePatternController extends BaseController
{
    @Autowired
    private ITilePatternService tilePatternService;

    /**
     * 查询图案列表
     */
    @PreAuthorize("@ss.hasPermi('tile:pattern:list')")
    @GetMapping("/list")
    public TableDataInfo list(TilePattern pattern)
    {
        startPage();
        List<TilePattern> list = tilePatternService.selectTilePatternList(pattern);
        return getDataTable(list);
    }

    /**
     * 导出图案列表
     */
    @PreAuthorize("@ss.hasPermi('tile:pattern:export')")
    @Log(title = "图案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TilePattern pattern)
    {
        List<TilePattern> list = tilePatternService.selectTilePatternList(pattern);
        ExcelUtil<TilePattern> util = new ExcelUtil<TilePattern>(TilePattern.class);
        util.exportExcel(response, list, "图案数据");
    }

    /**
     * 获取图案详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:pattern:query')")
    @GetMapping(value = "/{patternId}")
    public AjaxResult getInfo(@PathVariable("patternId") Long patternId)
    {
        return success(tilePatternService.selectTilePatternById(patternId));
    }

    /**
     * 新增图案
     */
    @PreAuthorize("@ss.hasPermi('tile:pattern:add')")
    @Log(title = "图案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TilePattern pattern)
    {
        if (!tilePatternService.checkPatternNameUnique(pattern.getPatternName()))
        {
            return error("新增图案'" + pattern.getPatternName() + "'失败，图案名称已存在");
        }
        pattern.setCreateBy(getUsername());
        return toAjax(tilePatternService.insertTilePattern(pattern));
    }

    /**
     * 修改图案
     */
    @PreAuthorize("@ss.hasPermi('tile:pattern:edit')")
    @Log(title = "图案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TilePattern pattern)
    {
        TilePattern oldPattern = tilePatternService.selectTilePatternById(pattern.getPatternId());
        if (!oldPattern.getPatternName().equals(pattern.getPatternName()) && !tilePatternService.checkPatternNameUnique(pattern.getPatternName()))
        {
            return error("修改图案'" + pattern.getPatternName() + "'失败，图案名称已存在");
        }
        pattern.setUpdateBy(getUsername());
        return toAjax(tilePatternService.updateTilePattern(pattern));
    }

    /**
     * 删除图案
     */
    @PreAuthorize("@ss.hasPermi('tile:pattern:remove')")
    @Log(title = "图案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{patternIds}")
    public AjaxResult remove(@PathVariable Long[] patternIds)
    {
        return toAjax(tilePatternService.deleteTilePatternByIds(patternIds));
    }
}
