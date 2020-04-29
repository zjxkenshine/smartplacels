package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.MD5Utils;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import com.csdl.smartplacenew.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.quartz.JobListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(description = "用户接口")
@RestController
@RequestMapping("/User")
public class UserController {

     @Resource
     private UserMapper userMapper;


    @ApiOperation(value = "用户登录", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "account", value = "登录名", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType = "query"),


    })
    @PostMapping("/login")
    private ResultVO login(HttpServletRequest request) {


        String loginname=request.getParameter("account");

        User user1=userMapper.getUserByLoginName(loginname);

        if(user1==null){
            return ResultVOUtil.byEnum(CodeMessage.UserNotExit);
        }

        String password=request.getParameter("password");

        String afterPassword= MD5Utils.MD5Encode(password,"utf8");

        User user=userMapper.getUserByLoginAndPassword(loginname,afterPassword);

        if(user!=null){

           HttpSession session=request.getSession();
           session.setAttribute("account",loginname);

           UserVo userVo=new UserVo();
           userVo.setId(user.getId());
           userVo.setCode(user.getCode());
           userVo.setUserName(user.getUsername());
           userVo.setLoginName(user.getLoginname());
           userVo.setJoinTime(user.getJointime());
           userVo.setLevel(user.getLevel());
           userVo.setCounty(user.getCounty());

           return ResultVOUtil.success(userVo);

       }
     else{

           return ResultVOUtil.error("404", "用户名或密码不正确");

       }


    }






    @ApiOperation(value = "修改密码", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "oldpassword", value = "旧密码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "newpassword", value = "新密码", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/changepwd")
    private ResultVO changepwd(HttpServletRequest request, HttpServletResponse response) {

        String oldpassword=request.getParameter("oldpassword");

        String newpassword=request.getParameter("newpassword");

        if(UserSecurity.checkUserLogin(request)  ){

            User user=UserSecurity.getCurrentUser(request,userMapper);

            String old= MD5Utils.MD5Encode(oldpassword,"utf8");

            String newpassoword=MD5Utils.MD5Encode(newpassword,"utf8");

            if(user.getPassword().equals(old)){

                userMapper.updateUserById(user.getCode(),user.getUsername(),user.getLoginname(),newpassoword,user.getLevel(),user.getCounty(),user.getId());

                return ResultVOUtil.success("用户修改密码成功");

            }else{

                return ResultVOUtil.error("400","输入密码错误");

            }
        }
          return ResultVOUtil.byEnum(CodeMessage.UserNotLogin);


    }
}
