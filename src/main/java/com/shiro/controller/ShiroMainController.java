package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.pojo.Result;
import com.shiro.common.util.Md5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wuyiming on 2017/8/2.
 */
@RestController
public class ShiroMainController extends AbstractController{
    
    /**
     * 验证用户名和密码
     * @return
     */
    @RequestMapping(value="/checkLogin.json",method= RequestMethod.POST)
    public JSONObject checkLogin(@RequestBody JSONObject requestObject,
                                 HttpServletResponse response) {
        String account = requestObject.getString("account");
        String password = requestObject.getString("password");
        try{
            UsernamePasswordToken token = new UsernamePasswordToken();
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()){
                token.setUsername(account);
                token.setPassword(Md5Util.string2MD5(password).toCharArray());
                token.setRememberMe(true);//使用shiro来验证
                currentUser.login(token);//验证角色和权限
            }
        } catch (ExcessiveAttemptsException e){
            response.setStatus(401);
            return Result.fail("登录失败5次");
        } catch (Exception ex){
            log.error(ex.getMessage());
            response.setStatus(401);
            return Result.fail("用户名或密码错误");
        }

        return Result.success();
    }

    /**
     * 退出登录
     */
    @RequestMapping(value="/logout.json",method=RequestMethod.POST)
    public JSONObject logout() {
        JSONObject result = new JSONObject();
        result.put("success", true);
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return Result.success();
    }
}
