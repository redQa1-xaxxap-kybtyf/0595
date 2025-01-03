DROP TABLE IF EXISTS tile_supplier_price;
CREATE TABLE tile_supplier_price (
    price_id         BIGINT(20)      NOT NULL AUTO_INCREMENT    COMMENT '价格ID',
    supplier_id      BIGINT(20)      NOT NULL                   COMMENT '供应商ID',
    goods_name       VARCHAR(50)     NOT NULL                   COMMENT '商品名称',
    piece_price      DECIMAL(10,2)                              COMMENT '片价格',
    square_price     DECIMAL(10,2)                              COMMENT '平方价格',
    unit_price       DECIMAL(10,2)                              COMMENT '件价格',
    status          CHAR(1)         DEFAULT '0'                COMMENT '状态（0正常 1停用）',
    del_flag        CHAR(1)         DEFAULT '0'                COMMENT '删除标志（0代表存在 2代表删除）',
    create_by       VARCHAR(64)     DEFAULT ''                 COMMENT '创建者',
    create_time     DATETIME                                   COMMENT '创建时间',
    update_by       VARCHAR(64)     DEFAULT ''                 COMMENT '更新者',
    update_time     DATETIME                                   COMMENT '更新时间',
    remark          VARCHAR(500)    DEFAULT NULL               COMMENT '备注',
    PRIMARY KEY (price_id)
) ENGINE=InnoDB AUTO_INCREMENT=200 COMMENT = '供应商价格表';

-- 添加外键约束
ALTER TABLE tile_supplier_price ADD CONSTRAINT fk_supplier_price FOREIGN KEY (supplier_id) REFERENCES tile_supplier (supplier_id);

-- 添加唯一索引，确保每个供应商的同一商品只有一个价格记录
ALTER TABLE tile_supplier_price ADD UNIQUE INDEX idx_supplier_goods(supplier_id, goods_name);
