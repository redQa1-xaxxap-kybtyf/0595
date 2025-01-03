-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(8, '合作伙伴管理', '0', '2', 'partner', null, 1, 0, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', null, '合作伙伴管理目录');

-- 二级菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(801, '供应商管理', 8, '1', 'supplier', 'tile/partner/supplier/index', 1, 0, 'C', '0', '0', 'tile:supplier:list', 'tree', 'admin', sysdate(), '', null, '供应商管理菜单');

-- 菜单 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(802, '供应商价格', 8, 2, 'price', 'tile/partner/price/index', 1, 0, 'C', '0', '0', 'tile:price:list', 'money', 'admin', sysdate(), '', null, '供应商价格菜单');

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80101, '供应商查询', 801, '1',  '#', '', 1, 0, 'F', '0', '0', 'tile:supplier:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80102, '供应商新增', 801, '2',  '#', '', 1, 0, 'F', '0', '0', 'tile:supplier:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80103, '供应商修改', 801, '3',  '#', '', 1, 0, 'F', '0', '0', 'tile:supplier:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80104, '供应商删除', 801, '4',  '#', '', 1, 0, 'F', '0', '0', 'tile:supplier:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80105, '供应商导出', 801, '5',  '#', '', 1, 0, 'F', '0', '0', 'tile:supplier:export',       '#', 'admin', sysdate(), '', null, '');

-- 按钮权限
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80201, '供应商价格查询', 802, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:price:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80202, '供应商价格新增', 802, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:price:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80203, '供应商价格修改', 802, 3, '#', '', 1, 0, 'F', '0', '0', 'tile:price:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80204, '供应商价格删除', 802, 4, '#', '', 1, 0, 'F', '0', '0', 'tile:price:remove', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80205, '供应商价格导出', 802, 5, '#', '', 1, 0, 'F', '0', '0', 'tile:price:export', '#', 'admin', sysdate(), '', null, '');
