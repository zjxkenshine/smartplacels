package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.mapper.VillagenumberMapper;
import com.csdl.smartplacenew.mapper.VillagenumberSYMapper;
import com.csdl.smartplacenew.pojo.Villagenumber;
import com.csdl.smartplacenew.pojo.VillagenumberSY;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import com.csdl.smartplacenew.vo.VillagenumberFeiyiSYVo;
import com.csdl.smartplacenew.vo.VillagenumberSYVo;
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

@Api(description = "松阳普通村落接口")
@RestController
@RequestMapping("/VillagenumberSY")
public class VillagenumberSYController {


    @Resource
    private VillagenumberSYMapper villagenumberSYMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    private VillagenumberMapper villagenumberMapper;


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
        String fileRealPath= ConfigBean.imageRealPath+"picture"+ File.separator+"villagenumberSY"+File.separator;
        File file1=new File(fileRealPath);
        if(!file1.exists()){
            file1.mkdirs();

        }
        Path path= Paths.get(fileRealPath,filename);
        Files.write(path,file.getBytes());
        String picturUrl=ConfigBean.imageVirtualPath+"picture/"+"villagenumberSY/"+filename;

        villagenumberSYMapper.updatePictUrl(picturUrl,name);


      return   ResultVOUtil.success("新增图片成功");



    }



    @ApiOperation(value = "获取村落信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "intrger", paramType = "query")
    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) {


          Integer currentUserId=Integer.valueOf(request.getParameter("userid"));

        String name=request.getParameter("name");

        VillagenumberSY villagenumberSY=villagenumberSYMapper.getVillagenumberSYByName("%"+name+"%",currentUserId);


        return ResultVOUtil.success(villagenumberSY);


    }






    @ApiOperation(value = "获取松阳村落的名称经纬度", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "intrger", paramType = "query")
    })
    @GetMapping("/getList2")
    private ResultVO getList2(HttpServletRequest request) {

        Integer currentUserId=Integer.valueOf(request.getParameter("userid"));

        List<VillagenumberSY>  villagenumberSYList=villagenumberSYMapper.getAllVillagenumberSY(currentUserId);

        List<VillagenumberSYVo> villagenumberSYVoArrayList=new ArrayList<>();

        for(int i=0;i<villagenumberSYList.size();i++){


            VillagenumberSY VillagenumberSY=villagenumberSYList.get(i);


            VillagenumberSYVo villagenumberSYVo=new VillagenumberSYVo();

            villagenumberSYVo.setName(VillagenumberSY.getName());
            villagenumberSYVo.setLon(Double.valueOf(VillagenumberSY.getLon()));
            villagenumberSYVo.setLat(Double.valueOf(VillagenumberSY.getLat()));

            villagenumberSYVoArrayList.add(villagenumberSYVo);



         }

        return ResultVOUtil.success(villagenumberSYVoArrayList);

      }



    @ApiOperation(value = "松阳路牌非遗文化和民俗文化" , notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "松阳村牌 id", required = false, dataType = "string", paramType = "query"),
    })
    @GetMapping("/getSoYanFeiyiAndMinsu")
    private ResultVO getSoYanFeiyiAndMinsu(HttpServletRequest request) {

        Integer id=Integer.valueOf(request.getParameter("id"));

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,15);


        VillagenumberSY villagenumberSY=villagenumberSYMapper.getVillagenumberSYByName2(villagenumber.getName());


        VillagenumberFeiyiSYVo villagenumberFeiyiSYVo=new VillagenumberFeiyiSYVo();
        villagenumberFeiyiSYVo.setFeiyiwenhua(villagenumberSY.getFeiyiwenhua());
        villagenumberFeiyiSYVo.setMinsuwenhua(villagenumberSY.getMinsuwenhua());

        return ResultVOUtil.success(villagenumberFeiyiSYVo);


    }



//    @ApiOperation(value = "增加松阳非遗文化和民俗文化" , notes = "")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "松阳村牌 id", required = false, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "name", value = "松阳村牌名称", required = false, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "text", value = "松阳村牌内容", required = false, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "url", value = "松阳村牌链接", required = false, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "chuantongjianzhu", value = "松阳村牌传统建筑", required = false, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "feiyiwenhua", value = "松阳村牌非遗文化", required = false, dataType = "string", paramType = "query"),
//            @ApiImplicitParam(name = "minsuwenhua", value = "松阳村牌民俗文化", required = false, dataType = "string", paramType = "query"),
//
//
//    })
//    @GetMapping("/addVillageNumberSy")
//    private ResultVO addVillageNumberSy(HttpServletRequest request) {
//
//        String name=request.getParameter("name");
//
//        String text=request.getParameter("text");
//
//        String url=request.getParameter("url");
//
//        String chuantongjianzhu=request.getParameter("chuantongjianzhu");
//
//        String feiyiwenhua=request.getParameter("feiyiwenhua");
//
//        String minsuwenhua=request.getParameter("minsuwenhua");
//
//        villagenumberSYMapper.addVillageNumberSy(name,text,url,15,chuantongjianzhu,feiyiwenhua,minsuwenhua);
//
//        return ResultVOUtil.success("添加成功");
//
//
//
//    }


    @ApiOperation(value = "修改松阳非遗文化和民俗文化" , notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "松阳村牌 id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "feiyiwenhua", value = "松阳村牌非遗文化", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "minsuwenhua", value = "松阳村牌民俗文化", required = false, dataType = "string", paramType = "query"),


    })
    @PostMapping("/updateVillageNumberSy")
    private ResultVO updateVillageNumberSy(HttpServletRequest request) {

        Integer id=Integer.valueOf(request.getParameter("id"));

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,15);


        VillagenumberSY villagenumberSY=villagenumberSYMapper.getVillagenumberSYByName2(villagenumber.getName());


        String feiyiwenhua=null;
        if(request.getParameter("feiyiwenhua")!=null&&!request.getParameter("feiyiwenhua").equals("")){
            feiyiwenhua=request.getParameter("feiyiwenhua");
           // System.out.println(request.getParameter("feiyiwenhua"));
        }else{
            feiyiwenhua=villagenumberSY.getFeiyiwenhua();
        }



        String minsuwenhua=null;
        if(request.getParameter("minsuwenhua")!=null&&!request.getParameter("minsuwenhua").equals("")
                &&!request.getParameter("minsuwenhua").equals("<p></p>")){

            minsuwenhua=request.getParameter("minsuwenhua");
        }else{
            minsuwenhua=villagenumberSY.getMinsuwenhua();
        }

        System.out.println(feiyiwenhua);
        System.out.println(villagenumberSY.getId());

        villagenumberSYMapper.updateVillageNumberSy(
                feiyiwenhua,
                minsuwenhua,
                villagenumberSY.getId());

        return ResultVOUtil.success("修改成功");


    }
 }
