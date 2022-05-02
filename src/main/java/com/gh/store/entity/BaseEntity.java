package com.gh.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class BaseEntity implements Serializable {
    /**
     * 创建人
     */
    private String createdUser;
    /**
     * 创建时间
     */
    private Date createdTime ;
    /**
     *  修改人
     */
    private String modifiedUser;
    /**
     *  修改时间
     */
    private Date modifiedTime;
}
