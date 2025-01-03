-- 修改入库单表
alter table tile_stock_in
  add column sale_order_id bigint(20) default null comment '关联销售单ID' after supplier_id,
  add column customer_id bigint(20) default null comment '关联客户ID' after sale_order_id,
  modify column in_type char(1) not null comment '入库类型（1采购入库 2退货入库）',
  modify column supplier_id bigint(20) default null comment '供应商ID';

-- 修改入库单明细表
alter table tile_stock_in_detail
  add column batch_no varchar(32) not null comment '批次号',
  add column production_date varchar(10) default null comment '生产日期',
  add column location_id bigint(20) not null comment '仓位ID',
  add column unit char(1) not null default '1' comment '单位（1片 2箱）',
  modify column price decimal(10,2) default null comment '入库单价',
  modify column amount decimal(10,2) default null comment '入库金额';

-- 更新字典数据
delete from sys_dict_data where dict_type = 'tile_stock_in_type';
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (1, '采购入库', '1', 'tile_stock_in_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '采购入库');
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (2, '退货入库', '2', 'tile_stock_in_type', '', 'success', 'N', '0', 'admin', sysdate(), '退货入库');

-- 添加单位字典类型
insert into sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
values ('入库单位', 'tile_stock_in_unit', '0', 'admin', sysdate(), '入库单位（1片 2箱）');

-- 添加单位字典数据
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (1, '片', '1', 'tile_stock_in_unit', '', 'default', 'Y', '0', 'admin', sysdate(), '片');
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (2, '箱', '2', 'tile_stock_in_unit', '', 'default', 'N', '0', 'admin', sysdate(), '箱');
