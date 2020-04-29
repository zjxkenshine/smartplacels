package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Api(description = "庆元背景图片")
@RestController
@RequestMapping("/QingYuanPict")
public class QingYuanPictController {



    public static void deleteDirectory(File file){

        if(file.isFile()){

            file.delete();//清理文件

        }else{

            File list[] = file.listFiles();

            if(list!=null){

                for(File f: list){

                    deleteDirectory(f);

                }

                file.delete();//清理目录

            }

        }

    }


    @ApiOperation(value = "添加图片", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "files",value = "多个文件，",paramType = "formData",allowMultiple=true,required = true,dataType = "file"),

            @ApiImplicitParam(name = "userid", value = "用户Id", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@RequestParam(value = "files", required = true) MultipartFile[] files, HttpServletRequest request) throws IOException {

//        for(int i=0;i<files.length;i++) {
//
//            MultipartFile file = files[i];
//
//            Integer userid = Integer.valueOf(request.getParameter("userid"));
//
//            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            String times=System.currentTimeMillis()+"";
//            String filename = userid+times + "." + type;
//            String fileRealPath = ConfigBean.imageRealPath + "picture" + File.separator + "Qingyuan" + File.separator;
//
//
//            File thefile = new File(fileRealPath);
//            if (!thefile.exists()) {
//                thefile.mkdirs();
//            }
//            Path path = Paths.get(fileRealPath + filename);
//            Files.write(path, file.getBytes());//将图片存入服务器
//            String imgVirtualPath = ConfigBean.imageVirtualPath + "picture/" + "Qingyuan" + "/" + filename;
//
//
//    }
//
//        return ResultVOUtil.success("添加成功");


        String fileRealPath = ConfigBean.imageRealPath + "picture" + File.separator + "Qingyuan"+File.separator;



        File file1=new File(fileRealPath);
        if(!file1.exists()) {
          file1.mkdirs();
        }else{

            deleteDirectory(file1);
        }


        for(int i=0;i<files.length;i++) {

            MultipartFile file = files[i];

            Integer userid = Integer.valueOf(request.getParameter("userid"));

            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String times=System.currentTimeMillis()+"";
            String filename = userid+times + "." + type;


            File thefile = new File(fileRealPath);
            if (!thefile.exists()) {
                thefile.mkdirs();
            }
            Path path = Paths.get(fileRealPath + filename);
            Files.write(path, file.getBytes());//将图片存入服务器
            String imgVirtualPath = ConfigBean.imageVirtualPath + "picture/" + "Qingyuan" + "/" + filename;


        }

        return ResultVOUtil.success("修改成功");


    }





//
//    @ApiOperation(value = "修改庆元背景图片", notes = "")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "files",value = "多个文件，",paramType = "formData",allowMultiple=true,required = true,dataType = "file"),
//
//            @ApiImplicitParam(name = "userid", value = "用户Id", required = true, dataType = "string", paramType = "query"),
//
//    })
//    @PostMapping("/updateQingYuanPict")
//    public ResultVO updateQingYuanPict(@RequestParam(value = "files", required = true) MultipartFile[] files,
//                                    HttpServletRequest request) throws IOException {
//
//        String fileRealPath = ConfigBean.imageRealPath + "picture" + File.separator + "Qingyuan"+File.separator;
//
//        File file1=new File(fileRealPath);
//        deleteDirectory(file1);
//
//        for(int i=0;i<files.length;i++) {
//
//            MultipartFile file = files[i];
//
//            Integer userid = Integer.valueOf(request.getParameter("userid"));
//
//            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            String times=System.currentTimeMillis()+"";
//            String filename = userid+times + "." + type;
//
//
//            File thefile = new File(fileRealPath);
//            if (!thefile.exists()) {
//                thefile.mkdirs();
//            }
//            Path path = Paths.get(fileRealPath + filename);
//            Files.write(path, file.getBytes());//将图片存入服务器
//            String imgVirtualPath = ConfigBean.imageVirtualPath + "picture/" + "Qingyuan" + "/" + filename;
//
//
//        }
//
//        return ResultVOUtil.success("修改成功");
//
//
//    }



    @ApiOperation(value = "显示庆元背景图片", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userid", value = "用户Id", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getPictUrl")
    public ResultVO getPictUrl(HttpServletRequest request) throws IOException {

        String fileRealPath = ConfigBean.imageRealPath + "picture" + File.separator + "Qingyuan" + File.separator;

        File file=new File(fileRealPath);

        File[] files=file.listFiles();

        List<String> stringList=new ArrayList();

        if(files!=null){
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                String fileName = files[i].getName();

                String imgVirtualPath = ConfigBean.imageVirtualPath + "picture/" + "Qingyuan" + "/" + fileName;
                stringList.add(imgVirtualPath);
            }


        }
            return ResultVOUtil.success(stringList);
    }

        return ResultVOUtil.success();

    }




}
