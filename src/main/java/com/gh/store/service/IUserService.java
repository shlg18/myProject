package com.gh.store.service;

import com.gh.store.entity.User;
import org.springframework.stereotype.Service;


public interface IUserService {
    /**
     * 用户注册
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    User login(String username,String password);

    /**
     * 修改用户的密码
     * @param uid 用户id
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 根据用户的id获取用户信息
     * @param uid 用户的id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据操作
     * @param uid 用户id
     * @param username 用户的名称
     * @param user 用户对象的数据
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 根据用户uid更新头像
     * @param uid
     * @param username
     * @param avatar
     */
    void changeAvatar(Integer uid,String username,String avatar);
}
