-- 修改表名
RENAME TABLE tile_warehouse_position TO tile_warehouse_location;

-- 修改字段名
ALTER TABLE tile_warehouse_location 
    CHANGE position_id location_id bigint NOT NULL AUTO_INCREMENT COMMENT '货位ID',
    CHANGE position_code location_code varchar(50) DEFAULT NULL COMMENT '货位编码',
    CHANGE position_name location_name varchar(50) DEFAULT NULL COMMENT '货位名称';

-- 修改主键名称
ALTER TABLE tile_warehouse_location 
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (location_id);

-- 更新表注释
ALTER TABLE tile_warehouse_location COMMENT '货位表';
