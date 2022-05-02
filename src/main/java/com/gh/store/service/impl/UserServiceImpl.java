package com.gh.store.service.impl;

import com.gh.store.entity.User;
import com.gh.store.mapper.UserMapper;
import com.gh.store.service.IUserService;
import com.gh.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service //将当前类的对象交给Spring来管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username=user.getUsername();
        //调用findByUsername方法判断用户是否被注册过
        User u=userMapper.findByUsername(username);
        //判断结果是否不为null,则抛出用户名被占用的异常
        if(u!=null){
            //抛出异常
            throw new UsernameDuplicateException("用户名被占用");
        }
        //密码加密处理的实现：md5
        String oldPassword=user.getPassword();
        //获取一个随机值
        String randNum=UUID.randomUUID().toString().toUpperCase();
        //补全数据:盐值的记录
        user.setSalt(randNum);
        //将密码和随机值作为一个整体进行加密处理,忽略原有密码强度提升了数据的安全性
        String md5Password= getMD5Password(oldPassword,randNum);
        //将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);
        //补全数据：is_delete设置成0
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(new Date());
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(new Date());
        //执行注册业务功能的实现
        int row=userMapper.insertUser(user);
        if(row!=1){
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }

    }

    @Override
    public User login(String username, String password) {
        User result=userMapper.findByUsername(username);
        if(result==null){
            throw new UserNotFoundException("用户名不存在");
        }
        String oldPassword=result.getPassword();
        String salt=result.getSalt();
        String newPassword=getMD5Password(password,salt);
        if(!oldPassword.equals(newPassword)){
            throw new PasswordNotMatchException("密码不匹配");
        }
        if(result.getIsDelete()==1){
            throw new UserNotFoundException("用户名不存在");
        }
        User user=new User();
        user.setUserid(result.getUserid());
        user.setUsername(result.getUsername());
        //返回有用户的头像
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result=userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        String oldMd5Password=getMD5Password(oldPassword,result.getSalt());
        if(!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码不匹配");
        }
        String newMd5Password=getMD5Password(newPassword,result.getSalt());
        int row= userMapper.updatePasswordByUid(uid,newMd5Password,username,new Date());
        if(row!=1){
            throw new UpdateException("更新过程中发生的未知异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result=userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }
        User user=new User();
        user.setUsername(result.getUsername());
        user.setGender(result.getGender());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {

        User result=userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }
        user.setUserid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows=userMapper.updateInfoByUid(user);

        if(rows!=1){
            throw new UpdateException("更新数据产生的未知异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        User result=userMapper.findByUid(uid);
        if(result==null||result.getIsDelete()==1){
            throw new UserNotFoundException("用户不存在");
        }
        Integer row=userMapper.updateAvatarByUid(uid,avatar,username,new Date());
        if(row!=1){
            throw new UpdateException("更新数据产生的未知异常");
        }
    }

    /** 定义一个MD5算法的加密处理*/
    private String getMD5Password(String password,String randNum){
        for (int i = 0; i <3 ; i++) {
            password=DigestUtils.md5DigestAsHex((randNum+password+randNum).getBytes()).toUpperCase();
        }
       return password;
    }
}
