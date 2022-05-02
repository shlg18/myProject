package com.gh.store.entity;

import lombok.Data;

@Data
public class District extends BaseEntity{
    private String id;
    private String parent;
    private String code;
    private String name;
}
