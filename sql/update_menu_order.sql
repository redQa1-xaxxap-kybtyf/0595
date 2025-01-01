-- 修改仓库表结构
ALTER TABLE tile_warehouse DROP COLUMN warehouse_type;
ALTER TABLE tile_warehouse DROP COLUMN warehouse_code;
ALTER TABLE tile_warehouse DROP COLUMN area;
ALTER TABLE tile_warehouse DROP COLUMN manager;
ALTER TABLE tile_warehouse DROP COLUMN phone;
ALTER TABLE tile_warehouse ADD COLUMN locations varchar(500) DEFAULT NULL COMMENT '仓位名称（逗号分隔）';

update sys_menu set order_num = 3 where menu_id = 706;
update sys_menu set order_num = 4 where menu_id = 712;
