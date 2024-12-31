-- ----------------------------
-- 瓷砖分类表
-- ----------------------------
drop table if exists tile_category;
create table tile_category (
  category_id        bigint(20)      not null auto_increment    comment '分类ID',
  parent_id         bigint(20)      default 0                  comment '父分类ID',
  category_name     varchar(30)     default ''                 comment '分类名称',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  order_num         int(4)          default 0                  comment '显示顺序',
  level            int(4)          default 1                  comment '层级',
  visible          char(1)         default '0'                comment '分类状态（0显示 1隐藏）',
  category_image    varchar(200)    default ''                 comment '分类图片',
  goods_count      int(11)         default 0                  comment '关联商品数量',
  del_flag         char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (category_id)
) engine=innodb auto_increment=200 comment = '瓷砖分类表';

-- ----------------------------
-- 初始化-瓷砖分类表数据
-- ----------------------------
-- 一级分类
insert into tile_category values(1, 0,    '室内瓷砖', '0', 1, 1, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(2, 0,    '室外瓷砖', '0', 2, 1, '0', '', 0, '0', 'admin', sysdate(), '', null);

-- 二级分类
insert into tile_category values(100, 1,  '地砖', '1', 1, 2, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(101, 1,  '墙砖', '1', 2, 2, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(102, 2,  '广场砖', '2', 1, 2, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(103, 2,  '园林砖', '2', 2, 2, '0', '', 0, '0', 'admin', sysdate(), '', null);

-- 三级分类
insert into tile_category values(1001, 100, '卧室地砖', '1,100', 1, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(1002, 100, '书房地砖', '1,100', 2, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(1003, 100, '客厅地砖', '1,100', 3, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(1004, 101, '厨房墙砖', '1,101', 1, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(1005, 101, '卫生间墙砖', '1,101', 2, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(1006, 102, '防滑广场砖', '2,102', 1, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
insert into tile_category values(1007, 103, '仿古园林砖', '2,103', 1, 3, '0', '', 0, '0', 'admin', sysdate(), '', null);
