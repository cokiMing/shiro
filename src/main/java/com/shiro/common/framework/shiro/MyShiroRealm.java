package com.shiro.common.framework.shiro;

import com.shiro.common.pojo.Result;
import com.shiro.common.util.Md5Util;

import com.shiro.entity.DO.UserDO;
import com.shiro.service.UserService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by wuyiming on 2017/8/2.
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 权限匹配
     * @param principalCollection
     * @return
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();

        roleNames.add("administrator");//添加角色
        permissions.add("newPage.jhtml");  //添加权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 登录匹配
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Result userInfo = userService.getUserByAccount(token.getUsername());
        if ("1".equals(userInfo.getCode())){
            UserDO userDO = userInfo.getObject("content",UserDO.class);
            if (userDO != null){
                String account = userDO.getAccount();
                String password = userDO.getPassword();
                if (account.equals(token.getUsername())){
                    return new SimpleAuthenticationInfo(account,password, getName());
                }
            }
        }

        throw new AuthenticationException();
    }
}
