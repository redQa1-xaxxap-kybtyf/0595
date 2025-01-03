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
import com.ruoyi.system.tile.domain.TileSaleOrderDetail;
import com.ruoyi.system.tile.service.ITileSaleOrderDetailService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单明细Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/sale/order/detail")
public class TileSaleOrderDetailController extends BaseController
{
    @Autowired
    private ITileSaleOrderDetailService tileSaleOrderDetailService;

    /**
     * 查询销售订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileSaleOrderDetail tileSaleOrderDetail)
    {
        startPage();
        List<TileSaleOrderDetail> list = tileSaleOrderDetailService.selectTileSaleOrderDetailList(tileSaleOrderDetail);
        return getDataTable(list);
    }

    /**
     * 导出销售订单明细列表
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:export')")
    @Log(title = "销售订单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileSaleOrderDetail tileSaleOrderDetail)
    {
        List<TileSaleOrderDetail> list = tileSaleOrderDetailService.selectTileSaleOrderDetailList(tileSaleOrderDetail);
        ExcelUtil<TileSaleOrderDetail> util = new ExcelUtil<TileSaleOrderDetail>(TileSaleOrderDetail.class);
        util.exportExcel(response, list, "销售订单明细数据");
    }

    /**
     * 获取销售订单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:query')")
    @GetMapping(value = "/{detailId}")
    public AjaxResult getInfo(@PathVariable("detailId") Long detailId)
    {
        return success(tileSaleOrderDetailService.selectTileSaleOrderDetailByDetailId(detailId));
    }

    /**
     * 根据订单ID查询明细列表
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:query')")
    @GetMapping(value = "/order/{orderId}")
    public AjaxResult getDetailsByOrderId(@PathVariable("orderId") Long orderId)
    {
        return success(tileSaleOrderDetailService.selectTileSaleOrderDetailByOrderId(orderId));
    }

    /**
     * 新增销售订单明细
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:add')")
    @Log(title = "销售订单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileSaleOrderDetail tileSaleOrderDetail)
    {
        return toAjax(tileSaleOrderDetailService.insertTileSaleOrderDetail(tileSaleOrderDetail));
    }

    /**
     * 修改销售订单明细
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:edit')")
    @Log(title = "销售订单明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileSaleOrderDetail tileSaleOrderDetail)
    {
        return toAjax(tileSaleOrderDetailService.updateTileSaleOrderDetail(tileSaleOrderDetail));
    }

    /**
     * 删除销售订单明细
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:remove')")
    @Log(title = "销售订单明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailIds}")
    public AjaxResult remove(@PathVariable Long[] detailIds)
    {
        return toAjax(tileSaleOrderDetailService.deleteTileSaleOrderDetailByDetailIds(detailIds));
    }
}
