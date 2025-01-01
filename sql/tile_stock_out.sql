-- 出库单表
drop table if exists tile_stock_out;
create table tile_stock_out (
  out_id           bigint(20)      not null auto_increment    comment '出库单ID',
  out_code         varchar(32)     not null                   comment '出库单号',
  warehouse_id     bigint(20)      not null                   comment '仓库ID',
  status           char(1)         not null                   comment '状态（1待出库 2已出库 3已取消）',
  remark           varchar(500)    default null               comment '备注',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  primary key (out_id)
) engine=innodb auto_increment=200 comment = '出库单表';

-- 出库单明细表
drop table if exists tile_stock_out_detail;
create table tile_stock_out_detail (
  detail_id        bigint(20)      not null auto_increment    comment '明细ID',
  out_id           bigint(20)      not null                   comment '出库单ID',
  goods_id         bigint(20)      not null                   comment '商品ID',
  quantity         int(11)         not null                   comment '数量',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '出库单明细表';

-- 初始化-出库单菜单数据
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(706, '出库管理', 7, 3, 'stock/out', 'tile/stock/out/index', 1, 0, 'C', '0', '0', 'tile:stock:out:list', 'form', 'admin', sysdate(), '', null, '出库管理菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(707, '出库管理查询', 706, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:out:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(708, '出库管理新增', 706, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:out:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(709, '出库管理修改', 706, 3, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:out:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(710, '出库管理删除', 706, 4, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:out:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(711, '出库管理导出', 706, 5, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:out:export', '#', 'admin', sysdate(), '', null, '');

-- 初始化-字典数据
insert into sys_dict_data values(51, 1,  '待出库', '1', 'tile_stock_out_status', '',   '', 'default', 'N', '0', 'admin', sysdate(), '', null, '待出库状态');
insert into sys_dict_data values(52, 2,  '已出库', '2', 'tile_stock_out_status', '',   '', 'success', 'N', '0', 'admin', sysdate(), '', null, '已出库状态');
insert into sys_dict_data values(53, 3,  '已取消', '3', 'tile_stock_out_status', '',   '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '已取消状态');
