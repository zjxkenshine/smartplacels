package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.MaintrecordsMapper;
import com.csdl.smartplacenew.mapper.PictureMapper;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.Maintrecords;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.MaintrecordsVo;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api(description = "维护记录接口")
@RestController
@RequestMapping("/Maintrecords")
public class MaintrecordsController {


    @Resource
    private MaintrecordsMapper maintrecordsMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PictureMapper pictureMapper;

    @ApiOperation(value = "查询维护记录", notes = "")
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

            Integer count=maintrecordsMapper.getAllCount("%"+key+"%",currentUserId);

            PageHelper.startPage(page,size);



            List<Maintrecords> maintrecordsList=maintrecordsMapper.findAllMaintrecores("%"+key+"%",currentUserId);

            List<MaintrecordsVo> maintrecordsVoList=new ArrayList<>();

            for(int i=0;i<maintrecordsList.size();i++){

                Maintrecords maintrecords=maintrecordsList.get(i);

                MaintrecordsVo maintrecordsVo=new MaintrecordsVo();

                maintrecordsVo.setId(maintrecords.getId());
                maintrecordsVo.setMaintType(maintrecords.getMainttype());
                maintrecordsVo.setNumber(maintrecords.getNumber());
                maintrecordsVo.setMaintContent(maintrecords.getMaintcontent());
                maintrecordsVo.setMaintCompany(maintrecords.getMaintcompany());
                maintrecordsVo.setContacts(maintrecords.getContacts());
                maintrecordsVo.setContactsNum(maintrecords.getContactsnum());
                maintrecordsVo.setCost(maintrecords.getCost());
                maintrecordsVo.setMaintTime(maintrecords.getMainttime());

                List<String> stringList=pictureMapper.getPictureByMaintreid(maintrecords.getId(),currentUserId);
                maintrecordsVo.setPictureUrl(stringList);

                maintrecordsVoList.add(maintrecordsVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(maintrecordsVoList);

            return ResultVOUtil.success(listVO);
        }


      else{

            Integer count=maintrecordsMapper.getAllCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Maintrecords> maintrecordsList=maintrecordsMapper.findAllMaintrecores(null,currentUserId);

            List<MaintrecordsVo> maintrecordsVoList=new ArrayList<>();

            for(int i=0;i<maintrecordsList.size();i++){

                Maintrecords maintrecords=maintrecordsList.get(i);

                MaintrecordsVo maintrecordsVo=new MaintrecordsVo();

                maintrecordsVo.setId(maintrecords.getId());
                maintrecordsVo.setMaintType(maintrecords.getMainttype());
                maintrecordsVo.setNumber(maintrecords.getNumber());
                maintrecordsVo.setMaintContent(maintrecords.getMaintcontent());
                maintrecordsVo.setMaintCompany(maintrecords.getMaintcompany());
                maintrecordsVo.setContacts(maintrecords.getContacts());
                maintrecordsVo.setContactsNum(maintrecords.getContactsnum());
                maintrecordsVo.setCost(maintrecords.getCost());
                maintrecordsVo.setMaintTime(maintrecords.getMainttime());

                List<String> stringList=pictureMapper.getPictureByMaintreid(maintrecords.getId(),currentUserId);
                maintrecordsVo.setPictureUrl(stringList);

                maintrecordsVoList.add(maintrecordsVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(maintrecordsVoList);

            return ResultVOUtil.success(listVO);
        }
    }




    @ApiOperation(value = "统计总的费用", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "time", value = "时间段", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getCostCount")
    private ResultVO getCostCount(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String time=request.getParameter("time");

        List<Maintrecords> maintrecordsList=maintrecordsMapper.getAllMaintrecores(currentUserId,"%"+time+"%");

        Integer count=maintrecordsMapper.getCostCount(currentUserId,"%"+time+"%");

         ListVO listVO=new ListVO();
         listVO.setCount(count);
         listVO.setList(maintrecordsList);

        return ResultVOUtil.success(listVO);


    }



}
