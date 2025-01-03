-- 供应商表
create table tile_supplier (
  supplier_id           bigint(20)      not null auto_increment    comment '供应商ID',
  supplier_name         varchar(50)     not null                   comment '供应商名称',
  contact_name         varchar(50)                                comment '联系人姓名',
  contact_phone        varchar(20)                                comment '联系电话',
  address             varchar(200)                               comment '地址',
  status              char(1)         default '0'                comment '状态（0正常 1停用）',
  del_flag            char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  remark              varchar(500)    default null               comment '备注',
  primary key (supplier_id)
) engine=innodb auto_increment=1 comment = '供应商表';

-- 供应商商品价格表
create table tile_supplier_goods_price (
  price_id            bigint(20)      not null auto_increment    comment '价格ID',
  supplier_id         bigint(20)      not null                   comment '供应商ID',
  goods_name          varchar(100)    not null                   comment '商品名称',
  price               decimal(10,2)   not null                   comment '价格',
  unit_type          char(1)         not null                   comment '计价单位（1按片 2按件 3按平方米）',
  status              char(1)         default '0'                comment '状态（0正常 1停用）',
  del_flag            char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  remark              varchar(500)    default null               comment '备注',
  primary key (price_id)
) engine=innodb auto_increment=1 comment = '供应商商品价格表';

-- 添加外键约束
alter table tile_supplier_goods_price add constraint fk_supplier_goods_price_supplier foreign key (supplier_id) references tile_supplier (supplier_id);

-- 为现有的供应商价格表添加计价单位字段
ALTER TABLE tile_supplier_goods_price ADD COLUMN unit_type char(1) NOT NULL DEFAULT '1' COMMENT '计价单位（1按片 2按件 3按平方米）' AFTER price;

-- 更新现有记录的计价单位为"按片"
UPDATE tile_supplier_goods_price SET unit_type = '1' WHERE unit_type IS NULL;
