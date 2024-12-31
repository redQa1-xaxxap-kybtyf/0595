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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.tile.domain.TileGoods;
import com.ruoyi.system.tile.service.ITileGoodsService;

/**
 * 瓷砖商品管理 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/tile/goods")
public class TileGoodsController extends BaseController
{
    @Autowired
    private ITileGoodsService tileGoodsService;

    /**
     * 获取瓷砖商品列表
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(TileGoods goods)
    {
        startPage();
        List<TileGoods> list = tileGoodsService.selectTileGoodsList(goods);
        return getDataTable(list);
    }

    /**
     * 导出瓷砖商品列表
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:export')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TileGoods goods)
    {
        List<TileGoods> list = tileGoodsService.selectTileGoodsList(goods);
        ExcelUtil<TileGoods> util = new ExcelUtil<TileGoods>(TileGoods.class);
        return util.exportExcel(list, "瓷砖商品数据");
    }

    /**
     * 获取瓷砖商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") Long goodsId)
    {
        return success(tileGoodsService.selectTileGoodsById(goodsId));
    }

    /**
     * 新增瓷砖商品
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:add')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TileGoods goods)
    {
        if (!tileGoodsService.checkGoodsCodeUnique(goods))
        {
            return error("新增商品'" + goods.getGoodsName() + "'失败，商品编号已存在");
        }
        return toAjax(tileGoodsService.insertTileGoods(goods));
    }

    /**
     * 修改瓷砖商品
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:edit')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TileGoods goods)
    {
        if (!tileGoodsService.checkGoodsCodeUnique(goods))
        {
            return error("修改商品'" + goods.getGoodsName() + "'失败，商品编号已存在");
        }
        return toAjax(tileGoodsService.updateTileGoods(goods));
    }

    /**
     * 删除瓷砖商品
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:remove')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable Long[] goodsIds)
    {
        return toAjax(tileGoodsService.deleteTileGoodsByIds(goodsIds));
    }

    /**
     * 更新商品状态
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:edit')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult updateStatus(@RequestBody TileGoods goods)
    {
        return toAjax(tileGoodsService.updateTileGoodsStatus(goods));
    }

    /**
     * 更新商品库存
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:edit')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.UPDATE)
    @PutMapping("/stock/{goodsId}/{stock}")
    public AjaxResult updateStock(@PathVariable("goodsId") Long goodsId, @PathVariable("stock") Integer stock)
    {
        return toAjax(tileGoodsService.updateTileGoodsStock(goodsId, stock));
    }

    /**
     * 更新商品销量
     */
    @PreAuthorize("@ss.hasPermi('tile:goods:edit')")
    @Log(title = "瓷砖商品管理", businessType = BusinessType.UPDATE)
    @PutMapping("/sales/{goodsId}/{sales}")
    public AjaxResult updateSales(@PathVariable("goodsId") Long goodsId, @PathVariable("sales") Integer sales)
    {
        return toAjax(tileGoodsService.updateTileGoodsSales(goodsId, sales));
    }
}
