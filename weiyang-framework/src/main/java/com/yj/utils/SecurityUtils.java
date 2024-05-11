package com.yj.utils;

import com.yj.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.acl.AclEntry;


public class SecurityUtils {
    /*
    * 获取用户
    * */
    public static LoginUser getLoginUser(){
       return (LoginUser) getAuthentication().getPrincipal();
    }
    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }
    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && id.equals(1L);
    }

    public static Long getUserId(){
        return getLoginUser().getUser().getId();
    }
}
