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
import com.ruoyi.system.tile.domain.TileUsage;
import com.ruoyi.system.tile.service.ITileUsageService;

/**
 * 用途管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/usage")
public class TileUsageController extends BaseController
{
    @Autowired
    private ITileUsageService tileUsageService;

    /**
     * 查询用途列表
     */
    @PreAuthorize("@ss.hasPermi('tile:usage:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileUsage usage)
    {
        startPage();
        List<TileUsage> list = tileUsageService.selectTileUsageList(usage);
        return getDataTable(list);
    }

    /**
     * 导出用途列表
     */
    @PreAuthorize("@ss.hasPermi('tile:usage:export')")
    @Log(title = "用途", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileUsage usage)
    {
        List<TileUsage> list = tileUsageService.selectTileUsageList(usage);
        ExcelUtil<TileUsage> util = new ExcelUtil<TileUsage>(TileUsage.class);
        util.exportExcel(response, list, "用途数据");
    }

    /**
     * 获取用途详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:usage:query')")
    @GetMapping(value = "/{usageId}")
    public AjaxResult getInfo(@PathVariable("usageId") Long usageId)
    {
        return success(tileUsageService.selectTileUsageById(usageId));
    }

    /**
     * 新增用途
     */
    @PreAuthorize("@ss.hasPermi('tile:usage:add')")
    @Log(title = "用途", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TileUsage usage)
    {
        if (!tileUsageService.checkUsageNameUnique(usage.getUsageName()))
        {
            return error("新增用途'" + usage.getUsageName() + "'失败，用途名称已存在");
        }
        usage.setCreateBy(getUsername());
        return toAjax(tileUsageService.insertTileUsage(usage));
    }

    /**
     * 修改用途
     */
    @PreAuthorize("@ss.hasPermi('tile:usage:edit')")
    @Log(title = "用途", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TileUsage usage)
    {
        TileUsage oldUsage = tileUsageService.selectTileUsageById(usage.getUsageId());
        if (!oldUsage.getUsageName().equals(usage.getUsageName()) && !tileUsageService.checkUsageNameUnique(usage.getUsageName()))
        {
            return error("修改用途'" + usage.getUsageName() + "'失败，用途名称已存在");
        }
        usage.setUpdateBy(getUsername());
        return toAjax(tileUsageService.updateTileUsage(usage));
    }

    /**
     * 删除用途
     */
    @PreAuthorize("@ss.hasPermi('tile:usage:remove')")
    @Log(title = "用途", businessType = BusinessType.DELETE)
    @DeleteMapping("/{usageIds}")
    public AjaxResult remove(@PathVariable Long[] usageIds)
    {
        return toAjax(tileUsageService.deleteTileUsageByIds(usageIds));
    }
}
