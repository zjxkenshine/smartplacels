package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Picture2;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.NioFileUtil;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Api(description = "图片处理接口")
@RestController
@RequestMapping("/Picture")
public class PictureController {

   @Resource
    PictureMapper pictureMapper;

   @Resource
    PlatenumberMapper platenumberMapper;

   @Resource
    RoadMapper roadMapper;

   @Resource
    RoadnumberMapper roadnumberMapper;

   @Resource
    ScenicspotMapper scenicspotMapper;

   @Resource
   VillagenumberMapper villagenumberMapper;

   @Resource
   UserMapper userMapper;

   Logger logging= LoggerFactory.getLogger(PictureController.class);



    @ApiOperation(value = "添加图片", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "files",value = "多个文件",paramType = "formData",allowMultiple=true,required = true,dataType = "file"),
            @ApiImplicitParam(name = "platenumberId", value = "门牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌Id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "maintrecordsId", value = "维护记录Id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@RequestParam(value = "files", required = true) MultipartFile[] files, Picture2 picture, HttpServletRequest request) throws IOException {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        if(user==null){
            return ResultVOUtil.byEnum(CodeMessage.UserNotLogin);
        }
        Integer currentUserId=user.getId();

        String type="default";
        String imgVirtualPath=null;

        if(picture.getPlatenumberId()!=null&&picture.getPlatenumberId()!=0){
            type="platenumber";
        }else if(picture.getRoadnumberId()!=null&&picture.getRoadnumberId()!=0){
            type="roadnumber";
        }else if(picture.getVillagenumberId()!=null&&picture.getVillagenumberId()!=0) {
            type = "villagenumber";
        }else if(picture.getScenicspotId()!=null&&picture.getScenicspotId()!=0) {
            type = "scenicspotnumber";
        }else if(picture.getRoadId()!=null&&picture.getRoadId()!=0) {
            type = "road";
        }else if(picture.getMaintrecordsId()!=null&&picture.getMaintrecordsId()!=0) {
            type = "maintrecords";
        }



        if(files!=null&&files.length>0){

            for(int i=0;i<files.length;i++){

                MultipartFile file=files[i];

//                String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
//                String times=System.currentTimeMillis()+"";
//                String filename=times+"."+picturetype;
//                String fileRealPath= ConfigBean.imageRealPath+"picture"+ File.separator+type+File.separator;
//                File theFile=new File(fileRealPath);
//                if(!theFile.exists()){
//                    theFile.mkdirs();
//                }
//
//                Path path= Paths.get(fileRealPath,filename);
//                Files.write(path,file.getBytes());
//
//                  imgVirtualPath=ConfigBean.imageVirtualPath+"picture/"+type+"/"+filename;

                imgVirtualPath= NioFileUtil.upload(file,type);
                pictureMapper.addPicture(imgVirtualPath,picture.getPlatenumberId(),picture.getRoadnumberId(),picture.getVillagenumberId(),picture.getScenicspotId(),picture.getRoadId(),1,currentUserId,picture.getMaintrecordsId());

            }

        }
      return ResultVOUtil.success("添加成功");

    }





    @ApiOperation(value = "修改图片", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "files",value = "多个文件，",paramType = "formData",allowMultiple=true,required = true,dataType = "file"),

            @ApiImplicitParam(name = "platenumberId", value = "门牌Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路Id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "maintrecordsId", value = "维护记录Id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/update")
    private ResultVO update(@RequestParam(value = "files", required = true) MultipartFile[] files, Picture2 picture, HttpServletRequest request) throws IOException {
            User user= UserSecurity.getCurrentUser(request,userMapper);
            if(user==null){
                return ResultVOUtil.byEnum(CodeMessage.UserNotLogin);
            }
            Integer currentUserId = user.getId();

            String type="default";

            if(picture.getPlatenumberId()!=null&&picture.getPlatenumberId()!=0){
                pictureMapper.delPictureByPlatenumberid(picture.getPlatenumberId(),currentUserId);
                type="platenumber";
            }else if(picture.getRoadnumberId()!=null&&picture.getRoadnumberId()!=0){
                pictureMapper.delPictureByRoadnumberid(picture.getRoadnumberId(),currentUserId);
                    type="roadnumber";
            }else if(picture.getVillagenumberId()!=null&&picture.getVillagenumberId()!=0) {
                pictureMapper.delPictureByVillagenumberid(picture.getVillagenumberId(),currentUserId);
                type = "villagenumber";
            }else if(picture.getScenicspotId()!=null&&picture.getScenicspotId()!=0) {
                pictureMapper.delPictureByScenicspotid(picture.getScenicspotId(),currentUserId);
                type = "scenicspotnumber";
            }else if(picture.getRoadId()!=null&&picture.getRoadId()!=0) {
                pictureMapper.delPictureByRoadid(picture.getRoadId(),currentUserId);
                type = "roadnumber";
            }else if(picture.getMaintrecordsId()!=null&&picture.getMaintrecordsId()!=0) {
                pictureMapper.delPictureByMaintrecordsid(picture.getMaintrecordsId(),currentUserId);
                type = "maintrecords";
            }


        String imgVirtualPath=null;

            if(files!=null&&files.length>0){

                for(int i=0;i<files.length;i++){

                    MultipartFile file=files[i];
//
//                    String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
//                    String times=System.currentTimeMillis()+"";
//                    String filename=times+"."+picturetype;
//                    String fileRealPath= ConfigBean.imageRealPath+"picture"+ File.separator+type+File.separator;
//                    File theFile=new File(fileRealPath);
//                    if(!theFile.exists()){
//                        theFile.mkdirs();
//                    }
//
//                    Path path= Paths.get(fileRealPath,filename);
//                    Files.write(path,file.getBytes());
//
//                    imgVirtualPath=ConfigBean.imageVirtualPath+"picture/"+type+"/"+filename;
                    imgVirtualPath= NioFileUtil.upload(file,type);

                    pictureMapper.addPicture(imgVirtualPath,picture.getPlatenumberId(),picture.getRoadnumberId(),picture.getVillagenumberId(),picture.getScenicspotId(),picture.getRoadId(),1,currentUserId,picture.getRoadId());
                }

            }

            return ResultVOUtil.success("修改成功");

    }


}
