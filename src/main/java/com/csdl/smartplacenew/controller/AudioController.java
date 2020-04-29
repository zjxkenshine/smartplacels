package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.*;
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
import java.util.List;

@Api(description = "音频管理接口")
@RestController
@RequestMapping("/Audio")
public class AudioController {

   @Resource
    AudioMapper audioMapper;

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




    @ApiOperation(value = "添加方言音频", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query")

    })
    @PostMapping("/addDialect")
    private ResultVO addDialect(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);

        Integer currentUserId=user.getId();

        String type=null;


        Integer platenumberid=null;

        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){

                type="platenumber";

                platenumberid=Integer.valueOf(request.getParameter("platenumberId"));

        }



        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){

                type="roadnumber";
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));

        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {


                type = "villagenumber";
                villagenumberid= Integer.valueOf(request.getParameter("villagenumberId"));

        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {


                type = "scenicspotnumber";
                scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));

        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {

                type = "road";
            roadid=Integer.valueOf(request.getParameter("roadId"));

        }

             if(file!=null) {
                 String imgVirtualPath = null;

                 String picturetype = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                 String times = System.currentTimeMillis() + "";
                 String filename = times + "." + picturetype;
                 String fileRealPath = ConfigBean.videoRealPath + "dialect" + File.separator + type + File.separator;
                 File theFile = new File(fileRealPath);
                 if (!theFile.exists()) {
                     theFile.mkdirs();
                 }

                 Path path = Paths.get(fileRealPath, filename);
                 Files.write(path, file.getBytes());

                 imgVirtualPath = ConfigBean.videoVirtualPath + "dialect/" + type + "/" + filename;

                 audioMapper.addAudio(imgVirtualPath, platenumberid, roadnumberid, villagenumberid, scenicspotid, roadid, 1, currentUserId);

             }
             else{
                 audioMapper.addAudio(null, platenumberid, roadnumberid, villagenumberid, scenicspotid, roadid, 1, currentUserId);

             }

      return ResultVOUtil.success("添加成功");


    }






    @ApiOperation(value = "添加英语音频", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query")

    })
    @PostMapping("/addEnglish")
    private ResultVO addEnglish(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {


        User user= UserSecurity.getCurrentUser(request,userMapper);

        Integer currentUserId=user.getId();

        String type=null;


        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){

            type="platenumber";

            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));

        }


        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){

            type="roadnumber";
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));

        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {

            type = "villagenumber";
            villagenumberid= Integer.valueOf(request.getParameter("villagenumberId"));

        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {

            type = "scenicspotnumber";
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));

        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {

            type = "road";
            roadid=Integer.valueOf(request.getParameter("roadId"));

        }


        String imgVirtualPath=null;

                String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+picturetype;
                String fileRealPath= ConfigBean.videoRealPath+"english"+ File.separator+type+File.separator;
                File theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }
                Path path= Paths.get(fileRealPath,filename);
                Files.write(path,file.getBytes());

                imgVirtualPath=ConfigBean.videoVirtualPath+"english/"+type+"/"+filename;



                audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);



        return ResultVOUtil.success("添加成功");

    }



    @ApiOperation(value = "添加国语音频", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query")

    })
    @PostMapping("/addMandarin")
    private ResultVO addMandarin(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {


        User user= UserSecurity.getCurrentUser(request,userMapper);

        Integer currentUserId=user.getId();


        String type=null;

        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){

            type="platenumber";

            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));

        }


        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){

            type="roadnumber";
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));

        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {

            type = "villagenumber";
            villagenumberid= Integer.valueOf(request.getParameter("villagenumberId"));

        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {

            type = "scenicspotnumber";
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));

        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {

            type = "road";
            roadid=Integer.valueOf(request.getParameter("roadId"));

        }


        String imgVirtualPath=null;

                String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+picturetype;
                String fileRealPath= ConfigBean.videoRealPath+"mandarin"+ File.separator+type+File.separator;
                File theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }

                Path path= Paths.get(fileRealPath,filename);
                Files.write(path,file.getBytes());

                imgVirtualPath=ConfigBean.videoVirtualPath+"mandarin/"+type+"/"+filename;



                audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);



        return ResultVOUtil.success("添加成功");

    }







    @ApiOperation(value = "修改方言音频", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/updateDialect")
    private ResultVO updateDialect(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String type=null;

        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;

        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

            List<String> stringList=audioMapper.getAudioByPlatenumberid(platenumberId,currentUserId);

            if(stringList!=null){
            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("dialect")!=-1) {

                    audioMapper.delAudioByPlatenumberUrl(stringList.get(k),currentUserId);
                }
                }
            }


            if(platenumberId!=null&&!platenumberId.equals("")){
                type="platenumber";
            }
        }

        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));

            List<String> stringList=audioMapper.getAudioByRoadnumberid(roadnumberId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("dialect") != -1) {

                        audioMapper.delAudioByRoadnumberUrl(stringList.get(k),currentUserId);

                    }
                }
            }


            if(roadnumberId!=null&&!roadnumberId.equals("")){
                type="roadnumber";
            }
        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {
            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));

            List<String>  stringList=audioMapper.getAudioByVillagenumberid(villagenumberId,currentUserId);
            if(stringList!=null){
            for(int k=0;k<stringList.size();k++){
                if(stringList.get(k).lastIndexOf("dialect")!=-1){
                    audioMapper.delAudioByVillagenumberUrl(stringList.get(k),currentUserId);
                }

            }}

            if (villagenumberId != null && !villagenumberId.equals("")) {
                type = "villagenumber";
            }
        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {
            scenicspotId = Integer.valueOf(request.getParameter("scenicspotId"));

            List<String> stringList=audioMapper.getAudioByScenicspotid(scenicspotId,currentUserId);
            if(stringList!=null){
            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("dialect")!=-1){
                    audioMapper.delAudioByScenicspotUrl(stringList.get(k),currentUserId);
                }}
            }

            if (scenicspotId != null && !scenicspotId.equals("")) {
                type = "scenicspotnumber";
            }
        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {
            roadId = Integer.valueOf(request.getParameter("roadId"));

            List<String> stringList=audioMapper.getAudioByRoadid(roadId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("dialect") != -1) {

                        audioMapper.delAudioByRoadUrl(stringList.get(k),currentUserId);
                    }
                }
            }

            if (roadId != null && !roadId.equals("")) {
                type = "roadnumber";
            }
        }


        String imgVirtualPath=null;

        String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String times=System.currentTimeMillis()+"";
        String filename=times+"."+picturetype;
        String fileRealPath= ConfigBean.videoRealPath+"dialect"+ File.separator+type+File.separator;
        File theFile=new File(fileRealPath);
        if(!theFile.exists()){
            theFile.mkdirs();
        }

        Path path= Paths.get(fileRealPath,filename);
        Files.write(path,file.getBytes());

        imgVirtualPath=ConfigBean.videoVirtualPath+"dialect/"+type+"/"+filename;


        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));
        }else{
            platenumberid=null;
        }



        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));
        }else{
            roadnumberid=null;
        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")){
            villagenumberid=Integer.valueOf(request.getParameter("villagenumberId"));
        }else{
            villagenumberid=null;
        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")){
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));
        }else{
            scenicspotid=null;
        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")){
            roadid=Integer.valueOf(request.getParameter("roadId"));
        }else{
            roadid=null;

        }

        audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);


        return ResultVOUtil.success("修改成功");


       }








    @ApiOperation(value = "修改英语音频", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/updateEnglish")
    private ResultVO updateEnglish(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String type=null;

        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;

        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

            List<String> stringList=audioMapper.getAudioByPlatenumberid(platenumberId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("english") != -1) {

                        audioMapper.delAudioByPlatenumberUrl(stringList.get(k),currentUserId);

                    }
                }
            }

            if(platenumberId!=null&&!platenumberId.equals("")){
                type="platenumber";
            }
        }



        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));
            List<String> stringList=audioMapper.getAudioByRoadnumberid(roadnumberId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("english") != -1) {

                        audioMapper.delAudioByRoadnumberUrl(stringList.get(k),currentUserId);

                    }
                }
            }

            if(roadnumberId!=null&&!roadnumberId.equals("")){
                type="roadnumber";
            }
        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {
            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));
            List<String>  stringList=audioMapper.getAudioByVillagenumberid(villagenumberId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("english") != -1) {
                        audioMapper.delAudioByVillagenumberUrl(stringList.get(k),currentUserId);
                    }

                }
            }
            if (villagenumberId != null && !villagenumberId.equals("")) {
                type = "villagenumber";
            }
        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {
            scenicspotId = Integer.valueOf(request.getParameter("scenicspotId"));

            List<String> stringList=audioMapper.getAudioByScenicspotid(scenicspotId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("english") != -1) {
                        audioMapper.delAudioByScenicspotUrl(stringList.get(k),currentUserId);
                    }
                }
            }
            if (scenicspotId != null && !scenicspotId.equals("")) {
                type = "scenicspotnumber";
            }
        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {
            roadId = Integer.valueOf(request.getParameter("roadId"));

            List<String> stringList=audioMapper.getAudioByRoadid(roadId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("english") != -1) {

                        audioMapper.delAudioByRoadUrl(stringList.get(k),currentUserId);
                    }
                }
            }

            if (roadId != null && !roadId.equals("")) {
                type = "roadnumber";
            }
        }


        String imgVirtualPath=null;



        String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String times=System.currentTimeMillis()+"";
        String filename=times+"."+picturetype;
        String fileRealPath= ConfigBean.videoRealPath+"english"+ File.separator+type+File.separator;
        File theFile=new File(fileRealPath);
        if(!theFile.exists()){
            theFile.mkdirs();
        }

        Path path= Paths.get(fileRealPath,filename);
        Files.write(path,file.getBytes());

        imgVirtualPath=ConfigBean.videoVirtualPath+"english/"+type+"/"+filename;


        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));
        }else{
            platenumberid=null;
        }



        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));
        }else{
            roadnumberid=null;
        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")){
            villagenumberid=Integer.valueOf(request.getParameter("villagenumberId"));
        }else{
            villagenumberid=null;
        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")){
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));
        }else{
            scenicspotid=null;
        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")){
            roadid=Integer.valueOf(request.getParameter("roadId"));
        }else{
            roadid=null;

        }

        audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);


        return ResultVOUtil.success("修改成功");


    }



    @ApiOperation(value = "修改国语音频", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "platenumberId", value = "门牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "roadnumberId", value = "路牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "villagenumberId", value = "村牌id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "scenicspotId", value = "景区id", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadId", value = "道路id", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/updateMandarin")
    private ResultVO updateMandarin(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);

        Integer currentUserId=user.getId();


        String type=null;

        Integer platenumberId =null;

        Integer  roadnumberId=null;

        Integer  villagenumberId=null;

        Integer  scenicspotId=null;

        Integer  roadId=null;

        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberId=Integer.valueOf(request.getParameter("platenumberId"));

            List<String> stringList=audioMapper.getAudioByPlatenumberid(platenumberId,currentUserId);
            if(stringList!=null){
            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1) {

                    audioMapper.delAudioByPlatenumberUrl(stringList.get(k),currentUserId);
                }
                }
            }
            if(platenumberId!=null&&!platenumberId.equals("")){
                type="platenumber";
            }
        }



        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberId=Integer.valueOf(request.getParameter("roadnumberId"));
            List<String> stringList=audioMapper.getAudioByRoadnumberid(roadnumberId,currentUserId);
            if(stringList!=null){
            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1) {

                    audioMapper.delAudioByRoadnumberUrl(stringList.get(k),currentUserId);
                }
                }
            }

            if(roadnumberId!=null&&!roadnumberId.equals("")){
                type="roadnumber";
            }
        }



        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")) {
            villagenumberId = Integer.valueOf(request.getParameter("villagenumberId"));
            List<String>  stringList=audioMapper.getAudioByVillagenumberid(villagenumberId,currentUserId);
            if(stringList!=null){
            for(int k=0;k<stringList.size();k++) {
                if (stringList.get(k).lastIndexOf("mandarin") != -1) {
                    audioMapper.delAudioByVillagenumberUrl(stringList.get(k),currentUserId);
                }
            }

            }
            if (villagenumberId != null && !villagenumberId.equals("")) {
                type = "villagenumber";
            }
        }



        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")) {
            scenicspotId = Integer.valueOf(request.getParameter("scenicspotId"));

            List<String> stringList=audioMapper.getAudioByScenicspotid(scenicspotId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {

                    if (stringList.get(k).lastIndexOf("mandarin") != -1) {
                        audioMapper.delAudioByScenicspotUrl(stringList.get(k),currentUserId);
                    }
                }
            }
            if (scenicspotId != null && !scenicspotId.equals("")) {
                type = "scenicspotnumber";
            }
        }


        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")) {
            roadId = Integer.valueOf(request.getParameter("roadId"));
            List<String> stringList=audioMapper.getAudioByRoadid(roadId,currentUserId);
            if(stringList!=null) {
                for (int k = 0; k < stringList.size(); k++) {
                    if (stringList.get(k).lastIndexOf("mandarin") != -1) {

                        audioMapper.delAudioByRoadUrl(stringList.get(k),currentUserId);
                    }
                }
            }
            if (roadId != null && !roadId.equals("")) {
                type = "roadnumber";
            }
        }


        String imgVirtualPath=null;



        String picturetype=file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
        String times=System.currentTimeMillis()+"";
        String filename=times+"."+picturetype;
        String fileRealPath= ConfigBean.videoRealPath+"mandarin"+ File.separator+type+File.separator;
        File theFile=new File(fileRealPath);
        if(!theFile.exists()){
            theFile.mkdirs();
        }

        Path path= Paths.get(fileRealPath,filename);
        Files.write(path,file.getBytes());

        imgVirtualPath=ConfigBean.videoVirtualPath+"mandarin/"+type+"/"+filename;


        Integer platenumberid=null;
        if(request.getParameter("platenumberId")!=null&&!request.getParameter("platenumberId").equals("")){
            platenumberid=Integer.valueOf(request.getParameter("platenumberId"));
        }else{
            platenumberid=null;
        }



        Integer roadnumberid=null;
        if(request.getParameter("roadnumberId")!=null&&!request.getParameter("roadnumberId").equals("")){
            roadnumberid=Integer.valueOf(request.getParameter("roadnumberId"));
        }else{
            roadnumberid=null;
        }


        Integer villagenumberid=null;
        if(request.getParameter("villagenumberId")!=null&&!request.getParameter("villagenumberId").equals("")){
            villagenumberid=Integer.valueOf(request.getParameter("villagenumberId"));
        }else{
            villagenumberid=null;
        }


        Integer scenicspotid=null;
        if(request.getParameter("scenicspotId")!=null&&!request.getParameter("scenicspotId").equals("")){
            scenicspotid=Integer.valueOf(request.getParameter("scenicspotId"));
        }else{
            scenicspotid=null;
        }


        Integer roadid=null;
        if(request.getParameter("roadId")!=null&&!request.getParameter("roadId").equals("")){
            roadid=Integer.valueOf(request.getParameter("roadId"));
        }else{
            roadid=null;

        }

        audioMapper.addAudio(imgVirtualPath,platenumberid,roadnumberid,villagenumberid,scenicspotid,roadid,1,currentUserId);


        return ResultVOUtil.success("修改成功");


    }



}
