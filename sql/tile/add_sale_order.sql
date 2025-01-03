-- 创建销售订单主表
drop table if exists tile_sale_order;
create table tile_sale_order (
  order_id           bigint(20)      not null auto_increment    comment '订单ID',
  order_no           varchar(32)     not null                   comment '订单编号',
  customer_id        bigint(20)      not null                   comment '客户ID',
  order_date         datetime        not null                   comment '订单日期',
  total_amount       decimal(10,2)   not null                   comment '订单总金额',
  order_status       char(1)         not null                   comment '订单状态（1待审核 2已审核 3已出库 4已完成 5已取消）',
  payment_status     char(1)         default '1'                comment '支付状态（1未支付 2部分支付 3已支付）',
  payment_amount     decimal(10,2)   default 0                  comment '已支付金额',
  delivery_address   varchar(200)                               comment '收货地址',
  delivery_phone     varchar(11)                                comment '收货电话',
  delivery_contact   varchar(50)                                comment '收货联系人',
  remark            varchar(500)                                comment '备注',
  status            char(1)          default '0'                comment '状态（0正常 1停用）',
  del_flag          char(1)          default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)      default ''                 comment '创建者',
  create_time       datetime                                    comment '创建时间',
  update_by         varchar(64)      default ''                 comment '更新者',
  update_time       datetime                                    comment '更新时间',
  primary key (order_id)
) engine=innodb auto_increment=200 comment = '销售订单表';

-- 创建销售订单明细表
drop table if exists tile_sale_order_detail;
create table tile_sale_order_detail (
  detail_id         bigint(20)      not null auto_increment    comment '明细ID',
  order_id          bigint(20)      not null                   comment '订单ID',
  product_id        bigint(20)      not null                   comment '产品ID',
  quantity          decimal(10,2)   not null                   comment '数量',
  unit_price        decimal(10,2)   not null                   comment '单价',
  amount            decimal(10,2)   not null                   comment '金额',
  remark            varchar(500)                               comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (detail_id)
) engine=innodb auto_increment=200 comment = '销售订单明细表';

-- 添加销售订单菜单
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('902', '销售订单', '9', 2, 'order', 'tile/sale/order/index', '', 1, 0, 'C', '0', '0', 'tile:order:list', 'shopping', 'admin', sysdate(), '', null, '销售订单菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('90201', '销售订单查询', @parentId, 1,  '', '', '', 1, 0, 'F', '0', '0', 'tile:order:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('90202', '销售订单新增', @parentId, 2,  '', '', '', 1, 0, 'F', '0', '0', 'tile:order:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('90203', '销售订单修改', @parentId, 3,  '', '', '', 1, 0, 'F', '0', '0', 'tile:order:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('90204', '销售订单删除', @parentId, 4,  '', '', '', 1, 0, 'F', '0', '0', 'tile:order:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('90205', '销售订单导出', @parentId, 5,  '', '', '', 1, 0, 'F', '0', '0', 'tile:order:export',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) 
values('90206', '销售订单审核', @parentId, 6,  '', '', '', 1, 0, 'F', '0', '0', 'tile:order:audit',        '#', 'admin', sysdate(), '', null, '');

-- 添加字典数据
insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) 
values ('订单状态', 'tile_order_status', '0', 'admin', sysdate(), '', null, '订单状态列表');

insert into sys_dict_type (dict_name, dict_type, status, create_by, create_time, update_by, update_time, remark) 
values ('支付状态', 'tile_payment_status', '0', 'admin', sysdate(), '', null, '支付状态列表');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (1, '待审核', '1', 'tile_order_status',   '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '待审核');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (2, '已审核', '2', 'tile_order_status',   '',   'info',    'N', '0', 'admin', sysdate(), '', null, '已审核');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (3, '已出库', '3', 'tile_order_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '已出库');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (4, '已完成', '4', 'tile_order_status',   '',   'success', 'N', '0', 'admin', sysdate(), '', null, '已完成');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (5, '已取消', '5', 'tile_order_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '已取消');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (1, '未支付', '1', 'tile_payment_status', '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '未支付');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (2, '部分支付', '2', 'tile_payment_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '部分支付');

insert into sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, update_by, update_time, remark) 
values (3, '已支付', '3', 'tile_payment_status', '',   'success', 'N', '0', 'admin', sysdate(), '', null, '已支付');
