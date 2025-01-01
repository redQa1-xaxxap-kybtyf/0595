-- 入库单表
drop table if exists tile_stock_in;
create table tile_stock_in (
  in_id           bigint(20)      not null auto_increment    comment '入库单ID',
  in_code         varchar(32)     not null                   comment '入库单号',
  warehouse_id    bigint(20)      not null                   comment '仓库ID',
  supplier_id     bigint(20)      not null                   comment '供应商ID',
  in_type         char(1)         not null                   comment '入库类型（1采购入库 2调拨入库）',
  status          char(1)         not null                   comment '状态（1待入库 2已入库 3已取消）',
  total_amount    decimal(10,2)   default 0                  comment '入库总金额',
  create_by       varchar(64)     default ''                 comment '创建者',
  create_time     datetime                                   comment '创建时间',
  update_by       varchar(64)     default ''                 comment '更新者',
  update_time     datetime                                   comment '更新时间',
  remark          varchar(500)    default null               comment '备注',
  primary key (in_id)
) engine=innodb auto_increment=200 comment = '入库单表';

-- 入库单明细表
drop table if exists tile_stock_in_detail;
create table tile_stock_in_detail (
  detail_id       bigint(20)      not null auto_increment    comment '明细ID',
  in_id           bigint(20)      not null                   comment '入库单ID',
  goods_id        bigint(20)      not null                   comment '商品ID',
  quantity        int(11)         not null                   comment '入库数量',
  price           decimal(10,2)   not null                   comment '入库单价',
  amount          decimal(10,2)   not null                   comment '入库金额',
  remark          varchar(500)    default null               comment '备注',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '入库单明细表';

-- 入库管理菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(702, '入库管理', 7, 2, 'stock/in', 'tile/stock/in/index', 1, 0, 'C', '0', '0', 'tile:stock:in:list', 'warehouse', 'admin', sysdate(), '', null, '入库管理菜单');

-- 按钮权限
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7021, '入库查询', 702, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:in:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7022, '入库新增', 702, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:in:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7023, '入库修改', 702, 3, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:in:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7024, '入库删除', 702, 4, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:in:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7025, '入库导出', 702, 5, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:in:export', '#', 'admin', sysdate(), '', null, '');

-- 字典类型
insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
values ('入库类型', 'tile_stock_in_type', '0', 'admin', sysdate(), '入库类型');

insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
values ('入库状态', 'tile_stock_in_status', '0', 'admin', sysdate(), '入库状态');

-- 字典数据
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (1, '采购入库', '1', 'tile_stock_in_type', '', 'primary', 'N', '0', 'admin', sysdate(), '采购入库');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (2, '调拨入库', '2', 'tile_stock_in_type', '', 'success', 'N', '0', 'admin', sysdate(), '调拨入库');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (1, '待入库', '1', 'tile_stock_in_status', '', 'info', 'N', '0', 'admin', sysdate(), '待入库');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (2, '已入库', '2', 'tile_stock_in_status', '', 'success', 'N', '0', 'admin', sysdate(), '已入库');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (3, '已取消', '3', 'tile_stock_in_status', '', 'danger', 'N', '0', 'admin', sysdate(), '已取消');
