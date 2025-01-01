-- 仓库表
drop table if exists tile_warehouse;
create table tile_warehouse (
  warehouse_id      bigint(20)      not null auto_increment    comment '仓库ID',
  warehouse_name    varchar(50)     not null                   comment '仓库名称',
  warehouse_code    varchar(32)     not null                   comment '仓库编码',
  warehouse_type    char(1)         not null                   comment '仓库类型（1主仓库 2分仓库）',
  address           varchar(200)    default ''                 comment '仓库地址',
  area             decimal(10,2)    default 0                  comment '仓库面积（平方米）',
  manager          varchar(64)      default ''                 comment '负责人',
  phone            varchar(11)      default ''                 comment '联系电话',
  status           char(1)          default '0'                comment '状态（0正常 1停用）',
  del_flag         char(1)          default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by        varchar(64)      default ''                 comment '创建者',
  create_time      datetime                                    comment '创建时间',
  update_by        varchar(64)      default ''                 comment '更新者',
  update_time      datetime                                    comment '更新时间',
  remark           varchar(500)     default null               comment '备注',
  primary key (warehouse_id)
) engine=innodb auto_increment=200 comment = '仓库表';

-- 初始化-仓库菜单数据
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(703, '仓库管理', 7, 5, 'stock/warehouse', 'tile/stock/warehouse/index', 1, 0, 'C', '0', '0', 'tile:warehouse:list', 'house', 'admin', sysdate(), '', null, '仓库管理菜单');

-- 按钮权限
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7031, '仓库查询', 703, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:warehouse:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7032, '仓库新增', 703, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:warehouse:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7033, '仓库修改', 703, 3, '#', '', 1, 0, 'F', '0', '0', 'tile:warehouse:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7034, '仓库删除', 703, 4, '#', '', 1, 0, 'F', '0', '0', 'tile:warehouse:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7035, '仓库导出', 703, 5, '#', '', 1, 0, 'F', '0', '0', 'tile:warehouse:export', '#', 'admin', sysdate(), '', null, '');

-- 字典类型
insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
values ('仓库类型', 'tile_warehouse_type', '0', 'admin', sysdate(), '仓库类型');

-- 字典数据
insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (1, '主仓库', '1', 'tile_warehouse_type', '', 'primary', 'N', '0', 'admin', sysdate(), '主仓库');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
values (2, '分仓库', '2', 'tile_warehouse_type', '', 'success', 'N', '0', 'admin', sysdate(), '分仓库');
