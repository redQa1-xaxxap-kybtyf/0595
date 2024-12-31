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
import com.ruoyi.system.tile.domain.TileMaterial;
import com.ruoyi.system.tile.service.ITileMaterialService;

/**
 * 材质管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/material")
public class TileMaterialController extends BaseController
{
    @Autowired
    private ITileMaterialService tileMaterialService;

    /**
     * 查询材质列表
     */
    @PreAuthorize("@ss.hasPermi('tile:material:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileMaterial material)
    {
        startPage();
        List<TileMaterial> list = tileMaterialService.selectTileMaterialList(material);
        return getDataTable(list);
    }

    /**
     * 导出材质列表
     */
    @PreAuthorize("@ss.hasPermi('tile:material:export')")
    @Log(title = "材质", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileMaterial material)
    {
        List<TileMaterial> list = tileMaterialService.selectTileMaterialList(material);
        ExcelUtil<TileMaterial> util = new ExcelUtil<TileMaterial>(TileMaterial.class);
        util.exportExcel(response, list, "材质数据");
    }

    /**
     * 获取材质详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:material:query')")
    @GetMapping(value = "/{materialId}")
    public AjaxResult getInfo(@PathVariable("materialId") Long materialId)
    {
        return success(tileMaterialService.selectTileMaterialById(materialId));
    }

    /**
     * 新增材质
     */
    @PreAuthorize("@ss.hasPermi('tile:material:add')")
    @Log(title = "材质", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TileMaterial material)
    {
        if (!tileMaterialService.checkMaterialNameUnique(material.getMaterialName()))
        {
            return error("新增材质'" + material.getMaterialName() + "'失败，材质名称已存在");
        }
        material.setCreateBy(getUsername());
        return toAjax(tileMaterialService.insertTileMaterial(material));
    }

    /**
     * 修改材质
     */
    @PreAuthorize("@ss.hasPermi('tile:material:edit')")
    @Log(title = "材质", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TileMaterial material)
    {
        TileMaterial oldMaterial = tileMaterialService.selectTileMaterialById(material.getMaterialId());
        if (!oldMaterial.getMaterialName().equals(material.getMaterialName()) && !tileMaterialService.checkMaterialNameUnique(material.getMaterialName()))
        {
            return error("修改材质'" + material.getMaterialName() + "'失败，材质名称已存在");
        }
        material.setUpdateBy(getUsername());
        return toAjax(tileMaterialService.updateTileMaterial(material));
    }

    /**
     * 删除材质
     */
    @PreAuthorize("@ss.hasPermi('tile:material:remove')")
    @Log(title = "材质", businessType = BusinessType.DELETE)
    @DeleteMapping("/{materialIds}")
    public AjaxResult remove(@PathVariable Long[] materialIds)
    {
        return toAjax(tileMaterialService.deleteTileMaterialByIds(materialIds));
    }
}
