package com.gh.store.controller;

import com.gh.store.controller.ex.*;
import com.gh.store.entity.User;
import com.gh.store.service.IUserService;
import com.gh.store.service.ex.InsertException;
import com.gh.store.service.ex.UsernameDuplicateException;
import com.gh.store.util.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Api(tags = "用户管理模块")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/reg",method = RequestMethod.POST)
    @ApiOperation(value = "用户注册")
    @ApiImplicitParam(name = "user",value = "用户实体")
    public JsonResult reg(@Valid User user){
        userService.reg(user);
        return new JsonResult(OK);
    }
    //@ResponseBody //表示此方法的响应结果以json格式进行数据的响应给前端
   /* public JsonResult reg(User user){
        JsonResult result=new JsonResult();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicateException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }*/
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true),
            @ApiImplicitParam(name = "session",value = "session对象")
    })
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data=userService.login(username,password);
        session.setAttribute("userid",data.getUserid());
        session.setAttribute("username",data.getUsername());
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK,data);
    }
    @RequestMapping(value = "/change_password",method = RequestMethod.PUT)
    @ApiOperation(value = "修改密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassword",value = "原始密码",required = true),
            @ApiImplicitParam(name = "newPassword",value = "新密码",required = true),
            @ApiImplicitParam(name = "session",value = "session对象")
    })
    public JsonResult changePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changePassword(uid,username,oldPassword,newPassword);
        return new JsonResult(OK);
    }
    @RequestMapping(value = "/get_by_uid",method = RequestMethod.GET)
    @ApiOperation(value = "根据uid获取用户信息")
    public JsonResult<User> getByUid(HttpSession session){
       User data= userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping(value = "/change_info",method = RequestMethod.PUT)
    @ApiOperation(value = "修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user",value = "用户对象")
    })
    public JsonResult changeInfo(User user,HttpSession session){
        Integer uid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult(OK);
    }

    /**
     * MultipartFile接口是SpringMVC提供的接口，这个接口为我们包装了获取文件类型的数据（任何类型的file都可以接收）
     * SpringBoot它又整合了SpringMVC,只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，
     * 然后Springboot自动将传递给服务的文件数据赋值给这个参数
     *
     * @RequestParam 表示请求中的参数，将请求中的参数注入请求处理方法的某个参数上，如果名称不一致则可以使用@RequestParam
     * 注解进行标记和映射
     * @param session
     * @param file
     * @return
     */
    public static final int AVATAR_MAX_SIZE=10*1024*1024;  //设置上传文件的最大值
    public static final List<String> AVATAR_TYPE=new ArrayList<>(); //限制上传文件的类型
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }
    @RequestMapping(value = "/change_avatar",method = RequestMethod.PUT)
    @ApiOperation(value ="修改头像")
    @ApiImplicitParam(name = "file",value = "上传文件")
    public JsonResult<String> changeAvatar(HttpSession session,@RequestParam("file") MultipartFile file){
        if(file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if(file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超过限制");
        }
        String contentType=file.getContentType();
        if(!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件.../upload/文件.png
        String parent=session.getServletContext().getRealPath("upload");
        File dir=new File(parent);
        if(!dir.exists()){ //检测目录是否存在
            dir.mkdirs(); //创建当前的目录
        }
        String originalFilename =file.getOriginalFilename();
        Integer index=originalFilename.lastIndexOf(".");
        String suffix=originalFilename.substring(index);
        String filename= UUID.randomUUID().toString().toUpperCase()+suffix;
        File dest=new File(dir,filename);
        try {
            file.transferTo(dest);
        }catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        String avatar="/upload/"+filename;
        Integer userid=getUidFromSession(session);
        String username=getUsernameFromSession(session);
        userService.changeAvatar(userid,username,avatar);
        return new JsonResult<String>(OK,avatar);
    }
}
