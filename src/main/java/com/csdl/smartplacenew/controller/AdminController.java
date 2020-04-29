package com.csdl.smartplacenew.controller;

import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.MD5Utils;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
import com.csdl.smartplacenew.vo.UserVo;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "管理员接口")
@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Resource
    private UserMapper userMapper;


    @ApiOperation(value = "添加用户", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "code", value = "编号", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "loginname", value = "登录名", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "level", value = "等级", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "county", value = "县市", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add( HttpServletRequest request) {

        if(UserSecurity.checkCurrentUserIsAdmin(request,userMapper)){

            String code=request.getParameter("code");

            String username=request.getParameter("username");

            String loginname=request.getParameter("loginname");

            String county=request.getParameter("county");

            User user=userMapper.getUserByLoginName(loginname);

            if(user!=null){

                return ResultVOUtil.error("400","您添加的用户已存在");
            }

            String password=request.getParameter("password");

            String level=request.getParameter("level");

            String encryptedPassword= MD5Utils.MD5Encode(password,"utf8");

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

            String jointime=df.format(new Date()).substring(0,10);

            userMapper.addUser(code,username,loginname,encryptedPassword,jointime,level,1,1,county);

            return ResultVOUtil.success("添加成功");
        }

        else{
            return ResultVOUtil.byEnum(CodeMessage.UserNotAdmin);
        }


    }






    @ApiOperation(value = "删除用户", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) {

        if(UserSecurity.checkCurrentUserIsAdmin(request,userMapper)) {

            Integer id = Integer.valueOf(request.getParameter("id"));

            userMapper.updateUserDelStatus(id);

            User user = userMapper.getUserById(id);

            return ResultVOUtil.success("成功删除:" + user);
        }

        return ResultVOUtil.byEnum(CodeMessage.UserNotAdmin);

    }





    @ApiOperation(value = "设置用户停用状态", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/sortStatus")
    private ResultVO sortStatus( HttpServletRequest request) {
        if(UserSecurity.checkCurrentUserIsAdmin(request,userMapper)) {

            Integer id = Integer.valueOf(request.getParameter("id"));

            userMapper.updateUserStatus(id);

            User user = userMapper.getUserById(id);

            return ResultVOUtil.success("成功设置:" + user);
        }

        return ResultVOUtil.byEnum(CodeMessage.UserNotAdmin);


    }





    @ApiOperation(value = "设置用户启用状态", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/sortStatus2")
    private ResultVO sortStatus2( HttpServletRequest request) {
        if(UserSecurity.checkCurrentUserIsAdmin(request,userMapper)) {

            Integer id = Integer.valueOf(request.getParameter("id"));

            userMapper.updateUserStatus2(id);

            User user = userMapper.getUserById(id);

            return ResultVOUtil.success("成功设置:" + user);
        }

        return ResultVOUtil.byEnum(CodeMessage.UserNotAdmin);


    }



    @ApiOperation(value = "修改用户", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "点位id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "code", value = "编号", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "loginname", value = "登录名", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "level", value = "等级", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "county", value = "县市", required = false, dataType = "integer", paramType = "query"),
    })
    @PostMapping("/update")
    private ResultVO update( HttpServletRequest request)  {

        if(UserSecurity.checkCurrentUserIsAdmin(request,userMapper)){


        Integer id=Integer.valueOf(request.getParameter("id"));

        String code=null;

        String username=null;

        String loginname=null;

        String password=null;

        Integer level=null;

        String county=null;


        User user=userMapper.getUserById(id);

        if(request.getParameter("code")!=null&&!request.getParameter("code").equals("")){
            code=request.getParameter("code");
        }else{
            code=user.getCode();
        }



        if(request.getParameter("username")!=null&&!request.getParameter("username").equals("")){
            username=request.getParameter("username");
        }else{
            username=user.getUsername();
        }



        if(request.getParameter("loginname")!=null&&!request.getParameter("loginname").equals("")){
            loginname=request.getParameter("loginname");
        }else{
            loginname=user.getLoginname();
        }



        if(request.getParameter("password")!=null&&!request.getParameter("password").equals("")){

            password= MD5Utils.MD5Encode(request.getParameter("password"),"utf8");
        }else{
            password=user.getPassword();
        }



        if(request.getParameter("level")!=null&&!request.getParameter("level").equals("")){
            level=Integer.valueOf(request.getParameter("level"));
        }else{
            level=user.getLevel();
        }



            if(request.getParameter("county")!=null&&!request.getParameter("county").equals("")){
                county=request.getParameter("county");
            }else{
                county=user.getCounty();
            }

        userMapper.updateUserById(code,username,loginname,password,level,county,user.getId());

        User user1=userMapper.getUserById(id);

        return ResultVOUtil.success("修改成功:"+user1);
        }

        return ResultVOUtil.byEnum(CodeMessage.UserNotAdmin);



    }





     @ApiOperation(value = "获取用户信息", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "key", value = "关键字", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "integer", paramType = "query"),

    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request, HttpServletResponse response) {

        if(UserSecurity.checkUserLogin(request)){

        if(UserSecurity.checkCurrentUserIsAdmin(request,userMapper)) {

            String key = request.getParameter("key");

            Integer type=Integer.valueOf(request.getParameter("type"));

            if(type==1){
                Integer page = Integer.valueOf(request.getParameter("page"));

                Integer size = Integer.valueOf(request.getParameter("size"));

                if (key != null && !key.equals("")) {

                    Integer count=userMapper.getUsrByKeyCount("%" + key + "%");

                    PageHelper.startPage(page, size);

                    List<User> userList = userMapper.getUsrByKey("%" + key + "%");

                    List<UserVo> userVoList = new ArrayList<>();

                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        UserVo userVo = new UserVo();
                        userVo.setId(user.getId());
                        userVo.setCode(user.getCode());
                        userVo.setUserName(user.getUsername());
                        userVo.setLoginName(user.getLoginname());
                        userVo.setJoinTime(user.getJointime());
                        userVo.setLevel(user.getLevel());
                        userVo.setCounty(user.getCounty());
                        if(user.getStatus()==1){
                            userVo.setStatus("已启用");
                        }

                        if(user.getStatus()==0){
                            userVo.setStatus("已停用");

                        }
                        userVoList.add(userVo);
                    }

                    ListVO listVO=new ListVO();
                    listVO.setCount(count);
                    listVO.setList(userVoList);

                    return ResultVOUtil.success(listVO);
                } else {

                    Integer count=userMapper.getUsrByKeyCount(null);

                    PageHelper.startPage(page, size);

                    List<User> userList = userMapper.getUsrByKey(null);

                    List<UserVo> userVoList = new ArrayList<>();

                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        UserVo userVo = new UserVo();
                        userVo.setId(user.getId());
                        userVo.setCode(user.getCode());
                        userVo.setUserName(user.getUsername());
                        userVo.setLoginName(user.getLoginname());
                        userVo.setJoinTime(user.getJointime());
                        userVo.setLevel(user.getLevel());
                        userVo.setCounty(user.getCounty());
                        if(user.getStatus()==1){
                            userVo.setStatus("已启用");
                        }

                        if(user.getStatus()==0){
                            userVo.setStatus("已停用");

                        }
                        userVoList.add(userVo);
                    }

                    ListVO listVO=new ListVO();
                    listVO.setCount(count);
                    listVO.setList(userVoList);

                    return ResultVOUtil.success(listVO);
                }

            }






            if(type==2){

                Integer page = Integer.valueOf(request.getParameter("page"));

                Integer size = Integer.valueOf(request.getParameter("size"));

                if (key != null && !key.equals("")) {

                    Integer count=userMapper.getUsrByKeyCountOfAdmin("%" + key + "%");

                    PageHelper.startPage(page, size);

                    List<User> userList = userMapper.getUsrByKeyOfAdmin("%" + key + "%");

                    List<UserVo> userVoList = new ArrayList<>();

                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        UserVo userVo = new UserVo();
                        userVo.setId(user.getId());
                        userVo.setCode(user.getCode());
                        userVo.setUserName(user.getUsername());
                        userVo.setLoginName(user.getLoginname());
                        userVo.setJoinTime(user.getJointime());
                        userVo.setLevel(user.getLevel());
                        userVo.setCounty(user.getCounty());
                        if(user.getStatus()==1){
                            userVo.setStatus("已启用");
                        }

                        if(user.getStatus()==0){
                            userVo.setStatus("已停用");

                        }
                        userVoList.add(userVo);
                    }

                    ListVO listVO=new ListVO();
                    listVO.setCount(count);
                    listVO.setList(userVoList);

                    return ResultVOUtil.success(listVO);
                } else {

                    Integer count=userMapper.getUsrByKeyCountOfAdmin(null);

                    PageHelper.startPage(page, size);

                    List<User> userList = userMapper.getUsrByKeyOfAdmin(null);

                    List<UserVo> userVoList = new ArrayList<>();

                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        UserVo userVo = new UserVo();
                        userVo.setId(user.getId());
                        userVo.setCode(user.getCode());
                        userVo.setUserName(user.getUsername());
                        userVo.setLoginName(user.getLoginname());
                        userVo.setJoinTime(user.getJointime());
                        userVo.setLevel(user.getLevel());
                        userVo.setCounty(user.getCounty());
                        if(user.getStatus()==1){
                            userVo.setStatus("已启用");
                        }

                        if(user.getStatus()==0){
                            userVo.setStatus("已停用");

                        }
                        userVoList.add(userVo);
                    }

                    ListVO listVO=new ListVO();
                    listVO.setCount(count);
                    listVO.setList(userVoList);

                    return ResultVOUtil.success(listVO);
                }

            }





            if(type==3){

                Integer page = Integer.valueOf(request.getParameter("page"));

                Integer size = Integer.valueOf(request.getParameter("size"));

                if (key != null && !key.equals("")) {

                    Integer count=userMapper.getUsrByKeyCountOfUser("%" + key + "%");

                    PageHelper.startPage(page, size);

                    List<User> userList = userMapper.getUsrByKeyOfUser("%" + key + "%");

                    List<UserVo> userVoList = new ArrayList<>();

                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        UserVo userVo = new UserVo();
                        userVo.setId(user.getId());
                        userVo.setCode(user.getCode());
                        userVo.setUserName(user.getUsername());
                        userVo.setLoginName(user.getLoginname());
                        userVo.setJoinTime(user.getJointime());
                        userVo.setLevel(user.getLevel());
                        userVo.setCounty(user.getCounty());
                        if(user.getStatus()==1){
                            userVo.setStatus("已启用");
                        }

                        if(user.getStatus()==0){
                            userVo.setStatus("已停用");

                        }
                        userVoList.add(userVo);
                    }

                    ListVO listVO=new ListVO();
                    listVO.setCount(count);
                    listVO.setList(userVoList);

                    return ResultVOUtil.success(listVO);
                } else {

                    Integer count=userMapper.getUsrByKeyCountOfUser(null);

                    PageHelper.startPage(page, size);

                    List<User> userList = userMapper.getUsrByKeyOfUser(null);

                    List<UserVo> userVoList = new ArrayList<>();

                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);

                        UserVo userVo = new UserVo();
                        userVo.setId(user.getId());
                        userVo.setCode(user.getCode());
                        userVo.setUserName(user.getUsername());
                        userVo.setLoginName(user.getLoginname());
                        userVo.setJoinTime(user.getJointime());
                        userVo.setCounty(user.getCounty());
                        userVo.setLevel(user.getLevel());
                        if(user.getStatus()==1){
                            userVo.setStatus("已启用");
                        }

                        if(user.getStatus()==0){
                            userVo.setStatus("已停用");

                        }
                        userVoList.add(userVo);
                    }

                    ListVO listVO=new ListVO();
                    listVO.setCount(count);
                    listVO.setList(userVoList);

                    return ResultVOUtil.success(listVO);
                }

            }



        }
            return  ResultVOUtil.byEnum(CodeMessage.UserNotAdmin);
        }


         return  ResultVOUtil.byEnum(CodeMessage.UserNotLogin);




     }





}
