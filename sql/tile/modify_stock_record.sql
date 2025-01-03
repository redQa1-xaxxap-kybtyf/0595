-- 修改库存记录表
alter table tile_stock_record
  add column batch_no varchar(32) default null comment '批次号',
  add column location_id bigint(20) default null comment '仓位ID';
