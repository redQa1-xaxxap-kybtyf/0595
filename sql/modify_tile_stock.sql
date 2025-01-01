-- 移除最小库存和最大库存字段
ALTER TABLE tile_stock DROP COLUMN min_stock;
ALTER TABLE tile_stock DROP COLUMN max_stock;
ALTER TABLE tile_stock MODIFY COLUMN quantity bigint;
