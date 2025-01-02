-- 菜单 SQL
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (704, '货位管理', 7, 6, 'stock/location', 'tile/stock/location/index', 1, 0, 'C', '0', '0', 'tile:location:list', '#', 'admin', sysdate(), '', null, '货位管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (7041, '货位查询', 704, 1, '#', '', 1, 0, 'F', '0', '0', 'tile:location:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (7042, '货位新增', 704, 2, '#', '', 1, 0, 'F', '0', '0', 'tile:location:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (7043, '货位修改', 704, 3, '#', '', 1, 0, 'F', '0', '0', 'tile:location:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (7044, '货位删除', 704, 4, '#', '', 1, 0, 'F', '0', '0', 'tile:location:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (7045, '货位导出', 704, 5, '#', '', 1, 0, 'F', '0', '0', 'tile:location:export', '#', 'admin', sysdate(), '', null, '');
