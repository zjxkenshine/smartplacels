package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(description = "页面跳转")
@RestController
@RequestMapping("/Jump")
public class JumpController {


    @Resource
    PlatenumberMapper platenumberMapper;

    @Resource
    private RoadMapper roadMapper;

    @Resource
    private RoadnumberMapper roadnumberMapper;

    @Resource
    ScenicspotMapper scenicspotMapper;

    @Resource
    VillagenumberMapper villagenumberMapper;


    @Resource
    CountMapper countMapper;

    @Resource
    UserMapper userMapper;



    @ApiOperation(value = "跳转页面计算", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "地名管理id", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "placekey", value = "地名管理关键词(1 门牌管理 ，2 路牌管理 ，3 村牌管理 ，4 景区管理，5 道路管理 )", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "trunkey", value = "跳转关键字(0 向小一级跳转 1 向大一级跳转)", required = true, dataType = "string", paramType = "query"),


    })
    @GetMapping("/jump")
    private ResultVO jump(HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer  id=Integer.valueOf(request.getParameter("id"));

        Integer placekey=Integer.valueOf(request.getParameter("placekey"));

        Integer trunkey=Integer.valueOf(request.getParameter("trunkey"));

        if(placekey==1){

            if(trunkey==0){

                Integer trueId=id-1;

                String platenumberUrl=platenumberMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(platenumberUrl);
            }

            if(trunkey==1){

                Integer trueId=id+1;

                String platenumberUrl=platenumberMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(platenumberUrl);
            }



        }



        if(placekey==2){

            if(trunkey==0){

                Integer trueId=id-1;

                String roadnumberUrl=roadnumberMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(roadnumberUrl);
            }


            if(trunkey==1){

                Integer trueId=id+1;

                String roadnumberUrl=roadnumberMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(roadnumberUrl);
            }


        }


        if(placekey==3){

            if(trunkey==0){

                Integer trueId=id-1;
                String villagenumberUrl=villagenumberMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(villagenumberUrl);

            }

            if(trunkey==1){

                Integer trueId=id+1;
                String villagenumberUrl=villagenumberMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(villagenumberUrl);

            }



        }


        if(placekey==4){

            if(trunkey==0){

                Integer trueId=id-1;

                String scenicspotUrl=scenicspotMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(scenicspotUrl);

            }



            if(trunkey==1){

                Integer trueId=id+1;

                String scenicspotUrl=scenicspotMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(scenicspotUrl);

            }


        }


        if(placekey==5){

            if(trunkey==0){

                Integer trueId=id-1;
                String roadUrl=roadMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(roadUrl);

            }

            if(trunkey==1){

                Integer trueId=id+1;
                String roadUrl=roadMapper.getUrlById(trueId,currentUserId);

                return ResultVOUtil.success(roadUrl);

            }

        }

        return ResultVOUtil.success();

    }


    }
