package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.FeedbackMapper;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.Feedback;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.FeedbackVo;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
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
import java.util.ArrayList;
import java.util.List;

@Api(description = "反馈记录接口")
@RestController
@RequestMapping("/Feedback")
public class FeedbackController {


    @Resource
    FeedbackMapper feedbackMapper;

    @Resource
    UserMapper userMapper;


    @ApiOperation(value = "添加反馈记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "feedbackpath", value = "反馈道路", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feedbacktype", value = "反馈类型", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "details", value = "详细内容", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "handle", value = "是否处理", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "submitime", value = "提交时间", required = true, dataType = "string", paramType = "query"),


    })
    @PostMapping("/add")
    private ResultVO add(HttpServletRequest request) {

        String feedbackpath=request.getParameter("feedbackpath");

        String feedbacktype=request.getParameter("feedbacktype");

        String details=request.getParameter("details");

        String handle=request.getParameter("handle");

        String submitime=request.getParameter("submitime");

       // feedbackMapper.addFeedback(feedbackpath,feedbacktype,details,handle,submitime);

        return ResultVOUtil.success("添加成功");



    }




        @ApiOperation(value = "查询反馈记录", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "key", value = "关键字", required = false, dataType = "string", paramType = "query"),
    })
    @GetMapping("/getList")
    private ResultVO getList(HttpServletRequest request) {

            User user= UserSecurity.getCurrentUser(request,userMapper);
            if(user==null){
                return ResultVOUtil.byEnum2(CodeMessage.UserNotLogin,new ListVO());
            }
            Integer currentUserId=user.getId();


        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        String key=request.getParameter("key");


        if(key!=null&&!key.equals("")){

          Integer count=feedbackMapper.getAllCount("%"+key+"%",currentUserId);

            PageHelper.startPage(page,size);

            List<Feedback> feedbackList=feedbackMapper.findAllFeedback("%"+key+"%",currentUserId);

            List<FeedbackVo> feedbackVoList=new ArrayList<>();

            for(int i=0;i<feedbackList.size();i++){

                Feedback feedback=feedbackList.get(i);

                FeedbackVo feedbackVo=new FeedbackVo();

                feedbackVo.setId(feedback.getId());
                feedbackVo.setFeedbackPath(feedback.getFeedbackpath());
                feedbackVo.setFeedbackType(feedback.getFeedbacktype());
                feedbackVo.setDetails(feedback.getDetails());
                feedbackVo.setHandle(feedback.getHandle());
                feedbackVo.setSubmiTime(feedback.getSubmitime());

                feedbackVoList.add(feedbackVo);


            }

            ListVO listVO=new ListVO();
            listVO.setList(feedbackVoList);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);


        }

        else{
            Integer count=feedbackMapper.getAllCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Feedback> feedbackList=feedbackMapper.findAllFeedback(null,currentUserId);

            List<FeedbackVo> feedbackVoList=new ArrayList<>();

            for(int i=0;i<feedbackList.size();i++){

                Feedback feedback=feedbackList.get(i);

                FeedbackVo feedbackVo=new FeedbackVo();

                feedbackVo.setId(feedback.getId());
                feedbackVo.setFeedbackPath(feedback.getFeedbackpath());
                feedbackVo.setFeedbackType(feedback.getFeedbacktype());
                feedbackVo.setDetails(feedback.getDetails());
                feedbackVo.setHandle(feedback.getHandle());
                feedbackVo.setSubmiTime(feedback.getSubmitime());

                feedbackVoList.add(feedbackVo);


            }

            ListVO listVO=new ListVO();
            listVO.setList(feedbackVoList);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);


              }

          }
    }
