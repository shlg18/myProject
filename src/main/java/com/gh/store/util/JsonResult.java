package com.gh.store.util;

import lombok.Data;

import java.io.Serializable;

/**
 * Json格式的数据进行响应
 * @param <E>响应数据的类型
 */
@Data
public class JsonResult<E> implements Serializable {
    /**状态码*/
    private Integer state;
    /**描述信息*/
    private String message;
    /**数据*/
    private E data;

    public JsonResult(){

    }
    public JsonResult(Integer state){
        this.state=state;
    }
    /** 出现异常时调用 */
    public JsonResult(Throwable e) {
        super();
    // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }
}
