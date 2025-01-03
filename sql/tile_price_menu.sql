-- 供应商价格管理菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(803, '供应商价格', '8', '3', 'price', 'tile/partner/price/index', 1, 0, 'C', '0', '0', 'tile:price:list', 'money', 'admin', sysdate(), '', null, '供应商价格管理菜单');

-- 供应商价格管理按钮
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80301, '价格查询', 803, '1', '#', '', 1, 0, 'F', '0', '0', 'tile:price:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80302, '价格新增', 803, '2', '#', '', 1, 0, 'F', '0', '0', 'tile:price:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80303, '价格修改', 803, '3', '#', '', 1, 0, 'F', '0', '0', 'tile:price:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80304, '价格删除', 803, '4', '#', '', 1, 0, 'F', '0', '0', 'tile:price:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(80305, '价格导出', 803, '5', '#', '', 1, 0, 'F', '0', '0', 'tile:price:export',       '#', 'admin', sysdate(), '', null, '');
