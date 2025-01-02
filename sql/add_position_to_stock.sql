-- 添加仓位ID字段
ALTER TABLE tile_stock ADD COLUMN position_id bigint COMMENT '仓位ID';
