package com.csdl.smartplacenew.security;

import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.User;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Resource
public class UserSecurity {


    //校验用户是否已经登入
    public static boolean checkUserLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(true);

        if ((String)session.getAttribute("account") != null) {
            return true;
        }
        return false;
    }


    public static User getCurrentUser(HttpServletRequest request, UserMapper  userMapper) {
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account");
        if (account != null) {
            User user = userMapper.getUserByLoginName(account);
            return user;
        }
        return null;
    }




    public static boolean checkCurrentUserIsAdmin(HttpServletRequest request, UserMapper userMapper) {

        User user = getCurrentUser(request, userMapper);
        if (user == null) return false;
        int e = user.getLevel();
        if (user.getLevel()==2) {
            return true;
        }
        return false;
    }





}
