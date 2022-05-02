package com.gh.store.controller;

import com.gh.store.controller.ex.*;
import com.gh.store.service.ex.*;
import com.gh.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**控制层类的基类*/
public class BaseController {
    /** 操作成功的状态码 */
    public static final int OK=200;

    //请求处理方法，这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    @ExceptionHandler({ServiceException.class,FileUploadException.class})    //用于统一处理抛出的异常
    public JsonResult handleException(Throwable e){
        JsonResult result=new JsonResult();
        if(e instanceof UsernameDuplicateException){
            result.setState(4000);
            result.setMessage("用户名被占用");
        }else if(e instanceof UserNotFoundException){
            result.setState(4001);
            result.setMessage("用户数据不存在的异常");
        }else if(e instanceof PasswordNotMatchException){
            result.setState(4002);
            result.setMessage("用户密码不匹配的异常");
        }else if(e instanceof AddressCountLimitException){
            result.setState(4003);
            result.setMessage("用户收获地址超出上限的异常");
        }else if(e instanceof AddressNotFoundException){
            result.setState(4004);
            result.setMessage("用户收获地址不存在的异常");
        }else if(e instanceof AccessDeniedException){
            result.setState(4005);
            result.setMessage("非法访问用户收获地址的异常");
        }else if(e instanceof ProductNotFoundException){
            result.setState(4006);
            result.setMessage("商品数据不存在的异常");
        }else if(e instanceof CartNotFoundException){
            result.setState(4007);
            result.setMessage("购物车数据不存在的异常");
        } else if(e instanceof DeleteException){
            result.setState(5002);
            result.setMessage("用户收获地址删除的异常");
        }else if(e instanceof InsertException){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }else if(e instanceof UpdateException){
            result.setState(5001);
            result.setMessage("更新时产生未知的异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("空文件异常");
        }else if (e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("文件大小超过限制的异常");
        }else if (e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("文件类型异常");
        }else if (e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("文件状态异常");
        }else if (e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("文件IO异常");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户的uid
     */
    protected final Integer getUidFromSession(HttpSession session){
      return Integer.valueOf(session.getAttribute("userid").toString());
    }

    /**
     * 获取session对象中的username
     * @param session
     * @return 当前登录用户的名称
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
