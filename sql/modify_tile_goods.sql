-- 修改 goods_name 字段，允许为空
ALTER TABLE tile_goods MODIFY COLUMN goods_name varchar(100) DEFAULT NULL;
