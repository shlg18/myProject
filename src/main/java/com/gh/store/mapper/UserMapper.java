package com.gh.store.mapper;

import com.gh.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/** 用户模块的持久层接口 */
@Repository
public interface UserMapper {
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return 受影响的行数(增删改都受影响的行数作为返回值，可以根据返回值来判断是否执行成功)
     */
    Integer insertUser(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据uid修改密码
     * @param uid 用户id
     * @param password 新输入的密码
     * @param modifiedUser 修改的执行者
     * @param modifiedTime 修改数据的时间
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据uid查询用户的信息
     * @param uid 用户id
     * @return
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户的数据
     * @return 返回值为受影响的行数
     */
    Integer updateInfoByUid(User user);

    /**
     * @Param("SQL映射文件中#{}占位符的变量名")：解决的问题，当SQL语句的占位符和映射的接口方法参数名不一致时，
     * 需要将某个参数强行注入到某个占位符变量上时，可以使用此注解来标注映射的关系
     *
     * 根据用户uid值来修改用户的头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(
            @Param("userid") Integer uid,
            @Param("avatar") String avatar,
            @Param("modifiedUser") String modifiedUser,
            @Param("modifiedTime") Date modifiedTime);



}
