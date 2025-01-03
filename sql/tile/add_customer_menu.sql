-- 菜单 SQL
-- 一级菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(9, '销售管理', 0, 3, 'sale', null, 1, 0, 'M', '0', '0', '', 'shopping', 'admin', sysdate(), '', null, '销售管理目录');

-- 二级菜单：客户管理
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(901, '客户管理', 9, 1, 'customer', 'tile/sale/customer/index', 1, 0, 'C', '0', '0', 'tile:customer:list', 'peoples', 'admin', sysdate(), '', null, '客户管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(90101, '客户查询', @parentId, 1,  '#', '', 1, 0, 'F', '0', '0', 'tile:customer:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(90102, '客户新增', @parentId, 2,  '#', '', 1, 0, 'F', '0', '0', 'tile:customer:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(90103, '客户修改', @parentId, 3,  '#', '', 1, 0, 'F', '0', '0', 'tile:customer:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(90104, '客户删除', @parentId, 4,  '#', '', 1, 0, 'F', '0', '0', 'tile:customer:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(90105, '客户导出', @parentId, 5,  '#', '', 1, 0, 'F', '0', '0', 'tile:customer:export',       '#', 'admin', sysdate(), '', null, '');

-- 客户类型字典
insert into sys_dict_type(dict_id, dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark)
values(12, '客户类型', 'tile_customer_type', '0', 'admin', sysdate(), '', null, '客户类型列表');

insert into sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)
values(1, '零售客户', '1', 'tile_customer_type', null, 'default', 'Y', '0', 'admin', sysdate(), '', null, '零售客户');

insert into sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark)
values(2, '批发客户', '2', 'tile_customer_type', null, 'default', 'N', '0', 'admin', sysdate(), '', null, '批发客户');

-- 客户表
drop table if exists tile_customer;
create table tile_customer (
  customer_id      bigint(20)      not null auto_increment    comment '客户ID',
  customer_name    varchar(50)     not null                   comment '客户名称',
  customer_type    char(1)         not null                   comment '客户类型（1零售 2批发）',
  contact_name     varchar(30)     default null               comment '联系人姓名',
  phone           varchar(11)      default null               comment '联系电话',
  address         varchar(200)     default null               comment '地址',
  status          char(1)          default '0'                comment '状态（0正常 1停用）',
  del_flag        char(1)          default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by       varchar(64)      default ''                 comment '创建者',
  create_time     datetime                                    comment '创建时间',
  update_by       varchar(64)      default ''                 comment '更新者',
  update_time     datetime                                    comment '更新时间',
  remark          varchar(500)     default null               comment '备注',
  primary key (customer_id)
) engine=innodb auto_increment=100 comment = '客户表';
