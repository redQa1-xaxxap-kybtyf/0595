-- 删除供应商价格相关文件
-- 1. 删除前端文件
-- /ruoyi-ui/src/views/tile/partner/price/index.vue
-- /ruoyi-ui/src/api/tile/price.js

-- 2. 删除后端文件
-- /ruoyi-system/src/main/java/com/ruoyi/system/tile/mapper/TileSupplierGoodsPriceMapper.java
-- /ruoyi-system/src/main/java/com/ruoyi/system/tile/service/impl/TileSupplierGoodsPriceServiceImpl.java
-- /ruoyi-system/src/main/java/com/ruoyi/system/tile/service/ITileSupplierGoodsPriceService.java
-- /ruoyi-system/src/main/java/com/ruoyi/system/tile/domain/TileSupplierGoodsPrice.java
-- /ruoyi-system/src/main/resources/mapper/tile/TileSupplierGoodsPriceMapper.xml
-- /ruoyi-admin/src/main/java/com/ruoyi/web/controller/tile/TileSupplierGoodsPriceController.java

-- 3. 删除菜单
delete from sys_menu where menu_id in (802, 80201, 80202, 80203, 80204, 80205);
