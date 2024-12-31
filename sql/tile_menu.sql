-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(603, '商品管理', '6', '3', 'goods',  'tile/goods/index',  1, 0, 'C', '0', '0', 'tile:goods:list', 'goods', 'admin', sysdate(), '', null, '商品管理菜单');

-- 商品管理按钮
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6031, '商品查询', 603, '1',  '#', '', 1, 0, 'F', '0', '0', 'tile:goods:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6032, '商品新增', 603, '2',  '#', '', 1, 0, 'F', '0', '0', 'tile:goods:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6033, '商品修改', 603, '3',  '#', '', 1, 0, 'F', '0', '0', 'tile:goods:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6034, '商品删除', 603, '4',  '#', '', 1, 0, 'F', '0', '0', 'tile:goods:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(6035, '商品导出', 603, '5',  '#', '', 1, 0, 'F', '0', '0', 'tile:goods:export',       '#', 'admin', sysdate(), '', null, '');
