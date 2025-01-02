-- ----------------------------
-- 1. 创建仓库位置表
-- ----------------------------
drop table if exists tile_warehouse_position;
create table tile_warehouse_position (
  position_id           bigint(20)      not null auto_increment    comment '仓位ID',
  warehouse_id         bigint(20)      not null                  comment '仓库ID',
  position_code        varchar(64)     not null                  comment '仓位编码',
  position_name        varchar(255)    not null                  comment '仓位名称',
  status              char(1)         default '0'                comment '状态（0正常 1停用）',
  del_flag            char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by           varchar(64)     default ''                 comment '创建者',
  create_time         datetime                                   comment '创建时间',
  update_by           varchar(64)     default ''                 comment '更新者',
  update_time         datetime                                   comment '更新时间',
  remark              varchar(500)    default null               comment '备注',
  primary key (position_id),
  key idx_warehouse_id (warehouse_id)
) engine=innodb auto_increment=1 comment = '仓库位置表';
