package com.gh.store.entity;

import lombok.Data;

import java.io.Serializable;

/** 购物车数据的实体类 */
@Data
public class Cart extends BaseEntity implements Serializable {
    private Integer cid;
    private Integer userid;
    private Integer pid;
    private Long price;
    private Integer num;
}

