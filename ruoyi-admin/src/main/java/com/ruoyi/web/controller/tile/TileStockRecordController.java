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
import com.ruoyi.system.tile.domain.TileStockRecord;
import com.ruoyi.system.tile.service.ITileStockRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 库存记录Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/stock/record")
public class TileStockRecordController extends BaseController
{
    @Autowired
    private ITileStockRecordService tileStockRecordService;

    /**
     * 查询库存记录列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileStockRecord tileStockRecord)
    {
        startPage();
        List<TileStockRecord> list = tileStockRecordService.selectTileStockRecordList(tileStockRecord);
        return getDataTable(list);
    }

    /**
     * 导出库存记录列表
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:export')")
    @Log(title = "库存记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileStockRecord tileStockRecord)
    {
        List<TileStockRecord> list = tileStockRecordService.selectTileStockRecordList(tileStockRecord);
        ExcelUtil<TileStockRecord> util = new ExcelUtil<TileStockRecord>(TileStockRecord.class);
        util.exportExcel(response, list, "库存记录数据");
    }

    /**
     * 获取库存记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(tileStockRecordService.selectTileStockRecordByRecordId(recordId));
    }

    /**
     * 新增库存记录
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:add')")
    @Log(title = "库存记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileStockRecord tileStockRecord)
    {
        return toAjax(tileStockRecordService.insertTileStockRecord(tileStockRecord));
    }

    /**
     * 修改库存记录
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:edit')")
    @Log(title = "库存记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileStockRecord tileStockRecord)
    {
        return toAjax(tileStockRecordService.updateTileStockRecord(tileStockRecord));
    }

    /**
     * 删除库存记录
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:remove')")
    @Log(title = "库存记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(tileStockRecordService.deleteTileStockRecordByRecordIds(recordIds));
    }

    /**
     * 清空库存记录
     */
    @PreAuthorize("@ss.hasPermi('tile:stock:record:remove')")
    @Log(title = "库存记录", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean()
    {
        tileStockRecordService.cleanTileStockRecord();
        return success();
    }
}
