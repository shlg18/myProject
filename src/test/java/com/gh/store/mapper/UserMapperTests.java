package com.gh.store.mapper;
import com.gh.store.entity.User;
import com.gh.store.mapper.UserMapper;
import com.gh.store.service.IUserService;
import com.gh.store.service.ex.UpdateException;
import com.gh.store.service.ex.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest  //表示标注当前的类是一个测试类，不会随同项目一同打包
@RunWith(SpringRunner.class) //表示启动这个单元测试类（单元测试类是不能够运行的），需要传递一个参数，必须是SpringRunner的实例类型
public class UserMapperTests {
    //idea有监测的功能，接口是不能够直接创建Bean的（动态代理技术来解决）
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    /** 单元测试方法：就可以单独独立运行，不用启动整个项目，可以做单元测试，提升了代码的测试效率
     * 1.必须被@Test注解修饰
     * 2.返回值类型必须是void
     * 3.方法的参数列表不指定任何类型
     * 4.方法的访问修饰符必须是public
     */

    @Test
    public void tsetInsert(){
        User user=new User();
//        user.setUserid(3);
        user.setUsername("Marry");
        user.setPassword("12345678");
//        user.setSalt("一般");
//        user.setPhone("1111");
//        user.setAvatar("333");
//        user.setEmail("222");
//        user.setGender(1);
//        user.setIsDelete(0);
//        user.setCreatedTime(new Date());
//        user.setCreatedUser("com");
//        user.setModifiedTime(new Date());
//        user.setModifiedUser("kk");

        int result= userMapper.insertUser(user);
        System.out.println(result);
    }
    @Test
    public void testFindByname(){
        User user=userMapper.findByUsername("MIKE");
        System.out.println(user);
    }

    @Test
    public void  updatePasswordByUid(){
        int row=userMapper.updatePasswordByUid(1,"888888","admin",new Date());
        System.out.println(row);
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(7));
    }

    @Test
    public void changeInfo() {
        User user=new User();
        user.setEmail("2739147949@qq.com");
        user.setPhone("18255831025");
        user.setGender(1);
        userService.changeInfo(7,"admin",user);
    }
    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(7,"/upload/my.png","admin",new Date());
    }
    @Test
    public void changeAvatar(){
        userService.changeAvatar(6,"系统管理员","/upload/test.png");
    }
}

