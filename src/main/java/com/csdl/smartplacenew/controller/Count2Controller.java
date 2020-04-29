package com.csdl.smartplacenew.controller;

import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.service.Count2Service;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

@Api(description = "计数器接口")
@RestController
@RequestMapping("/Count2")
public class Count2Controller {

    @Resource
    Count2Service count2Service;

    @Resource
    UserMapper userMapper;

    @ApiOperation(value = "添加访问记录", notes = "")
    @ApiImplicitParam(name = "place", value = "地区(庆元，松阳，青田，缙云)", required = true, dataType = "string", paramType = "query")
    @GetMapping("/addCount")
    public ResultVO addCount(String place) throws ParseException {
        return count2Service.addCount(place);
    }

    @ApiOperation(value = "获取七天访问量记录", notes = "")
    @GetMapping("/sevenCount")
    public ResultVO getSevenCounts(HttpServletRequest request) throws ParseException {
        HttpSession session=request.getSession();
        String account=(String) session.getAttribute("account");
        User user1=userMapper.getUserByLoginName(account);
        Integer userId=user1.getId();
        return count2Service.getSevenCounts(userId);
    }


    @ApiOperation(value = "获取总访问量", notes = "")
    @GetMapping("/allCount")
    public ResultVO getTotalCounts(HttpServletRequest request){
        HttpSession session=request.getSession();
        String account=(String) session.getAttribute("account");
        User user1=userMapper.getUserByLoginName(account);
        Integer userId=user1.getId();
        return count2Service.getTotalCounts(userId);
    }



}
