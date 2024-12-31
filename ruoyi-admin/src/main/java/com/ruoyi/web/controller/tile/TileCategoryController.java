package com.ruoyi.web.controller.tile;

import java.util.List;
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
import com.ruoyi.system.tile.domain.TileCategory;
import com.ruoyi.system.tile.service.ITileCategoryService;

/**
 * 瓷砖分类管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/category")
public class TileCategoryController extends BaseController
{
    @Autowired
    private ITileCategoryService tileCategoryService;

    /**
     * 获取瓷砖分类列表
     */
    @PreAuthorize("@ss.hasPermi('tile:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileCategory category)
    {
        List<TileCategory> list = tileCategoryService.selectTileCategoryList(category);
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(200);
        rspData.setRows(list);
        rspData.setTotal(list.size());
        return rspData;
    }

    /**
     * 获取瓷砖分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return success(tileCategoryService.selectTileCategoryById(categoryId));
    }

    /**
     * 新增瓷砖分类
     */
    @PreAuthorize("@ss.hasPermi('tile:category:add')")
    @Log(title = "瓷砖分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TileCategory category)
    {
        return toAjax(tileCategoryService.insertTileCategory(category));
    }

    /**
     * 修改瓷砖分类
     */
    @PreAuthorize("@ss.hasPermi('tile:category:edit')")
    @Log(title = "瓷砖分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TileCategory category)
    {
        return toAjax(tileCategoryService.updateTileCategory(category));
    }

    /**
     * 删除瓷砖分类
     */
    @PreAuthorize("@ss.hasPermi('tile:category:remove')")
    @Log(title = "瓷砖分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryId}")
    public AjaxResult remove(@PathVariable Long categoryId)
    {
        return toAjax(tileCategoryService.deleteTileCategoryById(categoryId));
    }
}
