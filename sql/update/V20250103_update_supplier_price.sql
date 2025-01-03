-- 修改供应商商品价格表结构
ALTER TABLE tile_supplier_goods_price 
  DROP COLUMN price,
  DROP COLUMN unit_type,
  ADD COLUMN piece_price decimal(10,2) DEFAULT NULL COMMENT '片价格',
  ADD COLUMN square_price decimal(10,2) DEFAULT NULL COMMENT '平方价格',
  ADD COLUMN unit_price decimal(10,2) DEFAULT NULL COMMENT '件价格';
