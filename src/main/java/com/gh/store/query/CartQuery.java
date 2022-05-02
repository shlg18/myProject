package com.gh.store.query;

import lombok.Data;

@Data
public class CartQuery {
    /**
     * 分页查询参数
     */
    private Integer pageNum=1;
    private Integer pageSize=10;

    /**
     * 条件查询参数
     */
    private Integer uid;

}
