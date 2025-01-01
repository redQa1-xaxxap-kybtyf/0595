-- 创建库存管理表
drop table if exists tile_stock;
create table tile_stock (
  stock_id           bigint(20)      not null auto_increment    comment '库存ID',
  goods_id           bigint(20)      not null                   comment '商品ID',
  warehouse_id       bigint(20)      not null                   comment '仓库ID',
  quantity           int(11)         not null default 0         comment '库存数量',
  min_stock          int(11)         default 0                  comment '最小库存',
  max_stock          int(11)         default 0                  comment '最大库存',
  status             char(1)         default '0'                comment '状态（0正常 1停用）',
  del_flag           char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by          varchar(64)     default ''                 comment '创建者',
  create_time        datetime                                   comment '创建时间',
  update_by          varchar(64)     default ''                 comment '更新者',
  update_time        datetime                                   comment '更新时间',
  remark             varchar(500)    default null               comment '备注',
  primary key (stock_id)
) engine=innodb auto_increment=200 comment = '库存管理表';

-- 初始化菜单数据
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7, '库存管理', 0, 1, 'stock', null, 1, 0, 'M', '0', '0', '', 'build', 'admin', sysdate(), '', null, '库存管理菜单');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(701, '库存列表', 7, 1, 'stock', 'tile/stock/index', 1, 0, 'C', '0', '0', 'tile:stock:list', '#', 'admin', sysdate(), '', null, '');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7011, '库存管理查询', 701, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7012, '库存管理新增', 701, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7013, '库存管理修改', 701, 3, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7014, '库存管理删除', 701, 4, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(7015, '库存管理导出', 701, 5, '#', '', 1, 0, 'F', '0', '0', 'tile:stock:export', '#', 'admin', sysdate(), '', null, '');
