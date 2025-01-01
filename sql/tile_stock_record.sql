-- 库存记录表
drop table if exists tile_stock_record;
create table tile_stock_record (
  record_id        bigint(20)      not null auto_increment    comment '记录ID',
  oper_type        char(1)         not null                   comment '操作类型（1入库 2出库）',
  goods_id         bigint(20)      not null                   comment '商品ID',
  warehouse_id     bigint(20)      not null                   comment '仓库ID',
  quantity         int(11)         not null                   comment '数量',
  source_id        bigint(20)      not null                   comment '来源单据ID',
  source_type      char(1)         not null                   comment '来源单据类型（1入库单 2出库单）',
  source_code      varchar(32)     not null                   comment '来源单据编号',
  oper_time        datetime                                   comment '操作时间',
  oper_by          varchar(64)     default ''                 comment '操作人员',
  remark           varchar(500)    default null               comment '备注',
  primary key (record_id)
) engine=innodb auto_increment=100 comment = '库存记录表';

-- 初始化-库存记录菜单数据
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(712, '库存记录', 7, 4, 'stock/record', 'tile/stock/record/index', 1, 0, 'C', '0', '0', 'tile:stock:record:list', 'time', 'admin', sysdate(), '', null, '库存记录菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(713, '库存记录查询', 712, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:record:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(714, '库存记录导出', 712, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:record:export', '#', 'admin', sysdate(), '', null, '');

-- 初始化-字典类型
insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark) 
values ('库存操作类型', 'tile_stock_oper_type', '0', 'admin', sysdate(), '库存操作类型');

-- 初始化-字典数据
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (1, '入库', '1', 'tile_stock_oper_type', '', 'success', 'N', '0', 'admin', sysdate(), '入库操作');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (2, '出库', '2', 'tile_stock_oper_type', '', 'danger', 'N', '0', 'admin', sysdate(), '出库操作');
