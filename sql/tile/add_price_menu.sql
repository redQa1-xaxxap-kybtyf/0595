-- 供应商价格菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values(802, '供应商价格', 8, 2, 'price', 'tile/partner/price/index', 1, 0, 'C', '0', '0', 'tile:price:list', 'money', 'admin', sysdate(), '', null, '供应商价格菜单');

-- 供应商价格按钮
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
