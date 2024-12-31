-- ----------------------------
-- 瓷砖商品表
-- ----------------------------
drop table if exists tile_goods;
create table tile_goods (
  goods_id          bigint(20)      not null auto_increment    comment '商品ID',
  goods_code        varchar(50)     not null                   comment '商品编号',
  goods_name        varchar(100)    not null                   comment '商品名称',
  category_id       bigint(20)      not null                   comment '商品分类ID',
  spec_id           bigint(20)                                 comment '规格尺寸ID',
  material_id       bigint(20)                                 comment '材质ID',
  surface_id        bigint(20)                                 comment '表面处理ID',
  usage_id          bigint(20)                                 comment '用途ID',
  pattern_id        bigint(20)                                 comment '图案ID',
  goods_image       varchar(200)    default ''                 comment '商品主图',
  goods_images      varchar(1000)   default ''                 comment '商品图片集',
  goods_desc        varchar(500)    default ''                 comment '商品描述',
  price             decimal(10,2)   default 0.00               comment '商品价格',
  stock             int(11)         default 0                  comment '库存数量',
  sales             int(11)         default 0                  comment '销量',
  status            char(1)         default '0'                comment '商品状态（0上架 1下架）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (goods_id)
) engine=innodb auto_increment=100 comment = '瓷砖商品表';

-- ----------------------------
-- 添加外键约束
-- ----------------------------
alter table tile_goods add constraint fk_goods_category foreign key (category_id) references tile_category (category_id);

-- ----------------------------
-- 创建索引
-- ----------------------------
create index idx_goods_code on tile_goods (goods_code);
create index idx_goods_name on tile_goods (goods_name);
create index idx_category_id on tile_goods (category_id);
create index idx_status on tile_goods (status);
