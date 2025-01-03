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
import com.ruoyi.system.tile.domain.TileSaleOrder;
import com.ruoyi.system.tile.service.ITileSaleOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/sale/order")
public class TileSaleOrderController extends BaseController
{
    @Autowired
    private ITileSaleOrderService tileSaleOrderService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileSaleOrder tileSaleOrder)
    {
        startPage();
        List<TileSaleOrder> list = tileSaleOrderService.selectTileSaleOrderList(tileSaleOrder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TileSaleOrder tileSaleOrder)
    {
        List<TileSaleOrder> list = tileSaleOrderService.selectTileSaleOrderList(tileSaleOrder);
        ExcelUtil<TileSaleOrder> util = new ExcelUtil<TileSaleOrder>(TileSaleOrder.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(tileSaleOrderService.selectTileSaleOrderByOrderId(orderId));
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TileSaleOrder tileSaleOrder)
    {
        return toAjax(tileSaleOrderService.insertTileSaleOrder(tileSaleOrder));
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TileSaleOrder tileSaleOrder)
    {
        return toAjax(tileSaleOrderService.updateTileSaleOrder(tileSaleOrder));
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(tileSaleOrderService.deleteTileSaleOrderByOrderIds(orderIds));
    }

    /**
     * 审核销售订单
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:audit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping("/audit/{orderId}")
    public AjaxResult audit(@PathVariable("orderId") Long orderId)
    {
        return toAjax(tileSaleOrderService.auditTileSaleOrder(orderId));
    }

    /**
     * 取消销售订单
     */
    @PreAuthorize("@ss.hasPermi('tile:sale:order:cancel')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping("/cancel/{orderId}")
    public AjaxResult cancel(@PathVariable("orderId") Long orderId)
    {
        return toAjax(tileSaleOrderService.cancelTileSaleOrder(orderId));
    }
}
