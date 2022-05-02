package com.gh.store.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
public class User extends BaseEntity implements Serializable {
    /**
     * 用户id
     */
    private Integer userid;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max = 10,message = "密码长度再6-10位之间")
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 电子邮箱
     */
    @Email
    private String email;
    /**
     * 性别:0-女，1-男
     */
    private Integer gender;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
}
