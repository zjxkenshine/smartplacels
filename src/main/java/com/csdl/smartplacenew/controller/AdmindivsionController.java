package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.mapper.AdmindivsionMapper;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.Admindivsion;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.AdminvsionTwoVo;
import com.csdl.smartplacenew.vo.AdminvsionVo;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Api(description = "行政区划接口")
@RestController
@RequestMapping("/Admindivsion")
public class AdmindivsionController {


    @Resource
    private AdmindivsionMapper admindivsionMapper;

    @Resource
    UserMapper userMapper;


    @ApiOperation(value = "上传图片", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "string", paramType = "query"),


    })
    @PostMapping("/updatePictUrl")
    private ResultVO updatePictUrl(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {


        String name=request.getParameter("name");

    //    Admindivsion admindivsion=admindivsionMapper.getAdminvisionByName(name);

        String type=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String time=System.currentTimeMillis()+"";
        String filename=time+"."+type;
        String fileRealPath= ConfigBean.imageRealPath+"picture"+ File.separator+"admindivsion"+File.separator;
        File file1=new File(fileRealPath);
        if(!file1.exists()){
            file1.mkdirs();

        }
        Path path= Paths.get(fileRealPath,filename);
        Files.write(path,file.getBytes());
        String picturUrl=ConfigBean.imageVirtualPath+"picture/"+"admindivsion/"+filename;
        admindivsionMapper.updatePictUrl(picturUrl,name);


      return   ResultVOUtil.success("新增成功");



    }



    @ApiOperation(value = "上传行政区划", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "text", value = "内容", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "lonAndlat", value = "经纬度", required = false, dataType = "string", paramType = "query"),



    })
    @PostMapping("/add")
    private ResultVO add(HttpServletRequest request) throws IOException {

                User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name=request.getParameter("name");

        String text=request.getParameter("text");

        if(request.getParameter("lonAndlat")!=null) {
            String lonAndlat = request.getParameter("lonAndlat");

            String[] lonlat = lonAndlat.split(",");
            admindivsionMapper.addAllAdminvision(name, text, lonlat[0], lonlat[1], currentUserId);
        }else{
            admindivsionMapper.addAllAdminvision(name, text, null, null, currentUserId);
        }




        return ResultVOUtil.success("添加成功");


    }


    @ApiOperation(value = "删除行政区划", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "行政区划Id", required = true, dataType = "string", paramType = "query"),


    })
    @PostMapping("/del")
    private ResultVO del(HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer id=Integer.valueOf(request.getParameter("id"));

        admindivsionMapper.delAdmindivsionByIdAndUserid(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }


    @ApiOperation(value = "修改行政区划", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "text", value = "内容", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "lonAndLat", value = "经纬度", required = false, dataType = "string", paramType = "query"),


    })
    @PostMapping("/update")
    private ResultVO update(HttpServletRequest request) throws IOException {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer id=Integer.valueOf(request.getParameter("id"));

        Admindivsion admindivsion=admindivsionMapper.getAdmindivsionById(id,currentUserId);

        String name=null;

        String text=null;

        String lon=null;
        String lat=null;



        if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){

            name=request.getParameter("name");
        }else{
            name=admindivsion.getName();
        }


        if(request.getParameter("text")!=null&&!request.getParameter("text").equals("")){
            text=request.getParameter("text");

        }else{
            text=admindivsion.getText();
        }


        if(request.getParameter("lonAndLat")!=null&&!request.getParameter("lonAndLat").equals("")&&!request.getParameter("lonAndLat").equals("null")){
          String[]  lonAndLat=request.getParameter("lonAndLat").split(",");

            lon=lonAndLat[0].trim();
            lat=lonAndLat[1].trim();

        }else{
            lon=admindivsion.getLon();
            lat=admindivsion.getLat();
        }




        admindivsionMapper.updateAllAdminvision(name,text,lon,lat,currentUserId,id);

        return ResultVOUtil.success("修改成功");
    }




    @ApiOperation(value = "获取乡镇", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "intrger", paramType = "query")
    })
    @GetMapping("/getList3")
    private ResultVO getList3(HttpServletRequest request) {

//        User user= UserSecurity.getCurrentUser(request,userMapper);
//        Integer currentUserId=user.getId();

          Integer currentUserId=Integer.valueOf(request.getParameter("userid"));

        String name=request.getParameter("name");

        Admindivsion admindivsion=admindivsionMapper.getAdminvisionByName("%"+name+"%",currentUserId);

        return ResultVOUtil.success(admindivsion);


    }








    @ApiOperation(value = "获取乡镇名称经纬度", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "intrger", paramType = "query")
    })
    @GetMapping("/getList2")
    private ResultVO getList2(HttpServletRequest request) {

        Integer currentUserId=Integer.valueOf(request.getParameter("userid"));

        List<Admindivsion> admindivsionList=admindivsionMapper.getAllAdminvision(currentUserId);

        List<AdminvsionVo> adminvsionVoList=new ArrayList<>();

        for(int i=0;i<admindivsionList.size();i++){


            Admindivsion admindivsion=admindivsionList.get(i);

            if(admindivsion.getName().lastIndexOf("青田县")==-1
                    &&admindivsion.getName().lastIndexOf("缙云县")==-1
                    &&admindivsion.getName().lastIndexOf("松阳县")==-1
                    &&admindivsion.getName().lastIndexOf("庆元县")==-1&&
                    admindivsion.getLat()!=null&&admindivsion.getLon()!=null

            ){

                AdminvsionVo adminvsionVo=new AdminvsionVo();

                adminvsionVo.setName(admindivsion.getName());
                adminvsionVo.setLon(Double.valueOf(admindivsion.getLon()));
                adminvsionVo.setLat(Double.valueOf(admindivsion.getLat()));

                adminvsionVoList.add(adminvsionVo);

               }

         }

        return ResultVOUtil.success(adminvsionVoList);

      }




    @ApiOperation(value = "获取行政区划", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = false, dataType = "string", paramType = "query"),
    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        String name=request.getParameter("name");


        if(name!=null&&!name.equals("")) {

            PageHelper.startPage(page,size);

            List<Admindivsion>  admindivsionList = admindivsionMapper.getAdmindivsionByNameAndUserId("%"+name+"%", currentUserId);

            PageInfo<Admindivsion> pageInfo = new PageInfo<Admindivsion>(admindivsionList);

            List<AdminvsionTwoVo> adminvsionTwoVoList=new ArrayList<>();

            for(int i=0;i<admindivsionList.size();i++){

                Admindivsion admindivsion=admindivsionList.get(i);

                AdminvsionTwoVo adminvsionTwoVo=new AdminvsionTwoVo();
                adminvsionTwoVo.setId(admindivsion.getId());
                adminvsionTwoVo.setName(admindivsion.getName());
                adminvsionTwoVo.setTxt(admindivsion.getText());
                if(admindivsion.getLat()!=null&&!admindivsion.getLat().equals("")) {
                    adminvsionTwoVo.setLonAndLon(admindivsion.getLon()+","+admindivsion.getLat());
                }
                adminvsionTwoVoList.add(adminvsionTwoVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(adminvsionTwoVoList);

            return ResultVOUtil.success(listVO);


        }
        else{
            PageHelper.startPage(page,size);
            List<Admindivsion>  admindivsionList = admindivsionMapper.getAdmindivsionByNameAndUserId(null, currentUserId);

            PageInfo<Admindivsion> pageInfo = new PageInfo<Admindivsion>(admindivsionList);

            List<AdminvsionTwoVo> adminvsionTwoVoList=new ArrayList<>();

            for(int i=0;i<admindivsionList.size();i++){

                Admindivsion admindivsion=admindivsionList.get(i);

                AdminvsionTwoVo adminvsionTwoVo=new AdminvsionTwoVo();
                adminvsionTwoVo.setId(admindivsion.getId());
                adminvsionTwoVo.setName(admindivsion.getName());
                adminvsionTwoVo.setTxt(admindivsion.getText());
                if(admindivsion.getLat()!=null&&!admindivsion.getLat().equals("")) {
                    adminvsionTwoVo.setLonAndLon(admindivsion.getLon()+","+admindivsion.getLat());
                }
                adminvsionTwoVoList.add(adminvsionTwoVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(adminvsionTwoVoList);

            return ResultVOUtil.success(listVO);
        }



    }




 }
