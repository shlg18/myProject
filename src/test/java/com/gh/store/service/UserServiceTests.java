package com.gh.store.service;

import com.gh.store.entity.User;
import com.gh.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)

public class UserServiceTests {
    @Autowired
    private IUserService userService;
    @Test
    public void reg(){
        try {
            User user=new User();
            user.setUserid(1);
            user.setUsername("Kobe");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("注册成功~");
        } catch (ServiceException e) {
            //获取类的对象，再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void login(){
       User user= userService.login("gh","631010");
        System.out.println(user);

       String str="file:/E:/csmis-micro-iom-sh-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/";
        int i = str.indexOf("/");;
        String temp=str.substring(i+1)+"public";
        System.out.println(temp);
    }


}
