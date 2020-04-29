package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Administrativemap;
import com.csdl.smartplacenew.informationvo.AdministrativemapVo;
import com.csdl.smartplacenew.mapper.AdministrativemapMapper;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Api(description = "庆元行政区划图")
@RestController
@RequestMapping("/Administrativemap")
public class AdministrativemapController {


    @Resource
    AdministrativemapMapper administrativemapMapper;

    @Resource
    UserMapper userMapper;


    @ApiOperation(value = "添加庆元行政区划图", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name=request.getParameter("name");

        if(file!=null) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String times = System.currentTimeMillis() + "";
            String filename = times + "." + type;
            String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Administrativemap" + File.separator;
            File thefile = new File(fileRealPath);
            if (!thefile.exists()) {
                thefile.mkdirs();
            }
            Path path = Paths.get(fileRealPath + filename);
            Files.write(path, file.getBytes());
            String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Administrativemap/" + filename;
            administrativemapMapper.addAdministrativemap(name, imgVirtualPath, 1,currentUserId);
        }
        else{
            administrativemapMapper.addAdministrativemap(name, null, 1,currentUserId);
        }


        Administrativemap administrativemap=administrativemapMapper.getAdministrativemapByMaxId(currentUserId);


        return ResultVOUtil.success(administrativemap);

    }






    @ApiOperation(value = "添加庆元行政区划图内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "庆元行政区划图id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/addTxt")
    private void addTxt( HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Administrativemap"+File.separator;

        File file1=new File(filepath);

        if(!file1.exists()){
            file1.mkdirs();
        }

        String filepath2=  ConfigBean.sourceRealPath+"txt"+ File.separator+"Administrativemap"+File.separator+"Administrativemap"+id+".txt";

        File file2=new File(filepath2);

        if(!file2.exists()){
            file2.createNewFile();
        }


        String word = request.getParameter("txt");

        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filepath2,true),"utf-8"));
        out.write(word+"\r\n");
        out.close();


    }



    @ApiOperation(value = "上传庆元行政区划图内容文件", notes = "")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "id", value = "华侨地名id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addTxtFile")
    private String addTxtFile(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //   Integer id=Integer.valueOf(request.getParameter("id"));


        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String times = System.currentTimeMillis() + "";

        String filename = times + "." + type;

        String filepath2=  ConfigBean.sourceRealPath+"informapict"+ File.separator+"Administrativemap"+File.separator;

        File thefile = new File(filepath2);

        if (!thefile.exists()) {

            thefile.mkdirs();

        }
        Path path = Paths.get(filepath2 + filename);
        Files.write(path, file.getBytes());//将图片存入服务器0

        String realPath= ConfigBean.sourceVirtualPath+"informapict/"+"Administrativemap/"+filename;

        return realPath;

    }


    @ApiOperation(value = "修改庆元行政区划图内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "庆元行政区划图id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/updateTxt")
    private void updateTxt( HttpServletRequest request) throws IOException {


        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Administrativemap"+File.separator+"Administrativemap"+id+".txt";

        File file1=new File(filepath);

        if(!file1.exists()) {
            file1.createNewFile();
        }

        FileWriter fileWriter=new FileWriter(file1);
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();


        String word = request.getParameter("txt");
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filepath,true),"utf-8"));
        out.write(word+"\r\n");
        out.close();





    }


    @ApiOperation(value = "查询庆元行政区划图", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        String name=request.getParameter("name");

        if(name!=null&&!name.equals("")){

            Integer count=administrativemapMapper.getAdministrativemapCount("%"+name+"%",currentUserId);

            PageHelper.startPage(page,size);

            List<Administrativemap> administrativemapList=administrativemapMapper.selectAdministrativemap2("%"+name+"%",currentUserId);

            List<AdministrativemapVo> administrativemapVoArrayList=new ArrayList<>();

            for(int i=0;i<administrativemapList.size();i++){

                Administrativemap administrativemap=administrativemapList.get(i);

                AdministrativemapVo administrativemapVo=new AdministrativemapVo();

                administrativemapVo.setId(administrativemap.getId());
                administrativemapVo.setName(administrativemap.getName());
                administrativemapVo.setPictureUrl(administrativemap.getPictureurl());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Administrativemap"+File.separator+"Administrativemap"+administrativemap.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";

//                FileReader reader = new FileReader(pathname);
//
//                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                administrativemapVo.setTxt(jsonString);

                administrativemapVoArrayList.add(administrativemapVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(administrativemapVoArrayList);

            return ResultVOUtil.success(listVO);


        }
        else{

            Integer count=administrativemapMapper.getAdministrativemapCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Administrativemap> administrativemapList=administrativemapMapper.selectAdministrativemap2(null,currentUserId);

            List<AdministrativemapVo> administrativemapVoArrayList=new ArrayList<>();

            for(int i=0;i<administrativemapList.size();i++){

                Administrativemap administrativemap=administrativemapList.get(i);

                AdministrativemapVo administrativemapVo=new AdministrativemapVo();

                administrativemapVo.setId(administrativemap.getId());
                administrativemapVo.setName(administrativemap.getName());
                administrativemapVo.setPictureUrl(administrativemap.getPictureurl());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Administrativemap"+File.separator+"Administrativemap"+administrativemap.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";

//                FileReader reader = new FileReader(pathname);
//
//                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                administrativemapVo.setTxt(jsonString);

                administrativemapVoArrayList.add(administrativemapVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(administrativemapVoArrayList);

            return ResultVOUtil.success(listVO);


        }


    }






    @ApiOperation(value = "删除庆元行政区划图", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "庆元行政区划图id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) throws IOException {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        administrativemapMapper.delAdministrativemape(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }




    @ApiOperation(value = "修改庆元行政区划图内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "庆元行政区划图id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/update")
    private ResultVO update(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Administrativemap administrativemap=administrativemapMapper.getAdministrativemapById2(id,currentUserId);


        String name=request.getParameter("name");


        if(file!=null) {

            if(administrativemap.getPictureurl()!=null){

                if(administrativemap.getPictureurl()!=null&&!administrativemap.getPictureurl().equals("")) {
                    String[] strings = administrativemap.getPictureurl().split("\\/");

                    File file1 = new File(ConfigBean.sourceRealPath + "information" + File.separator + "Administrativemap" + File.separator + strings[5]);

                    file1.delete();
                }

                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+type;
                String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Administrativemap"+File.separator;
                File  theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }
                Path paths=Paths.get(fileRealPath+filename);
                Files.write(paths,file.getBytes());

                String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Administrativemap/"+filename;



                administrativemapMapper.updateAdministrativemap(name,imgVirtualPath,id,currentUserId);

                Administrativemap administrativemap1=administrativemapMapper.getAdministrativemapById2(id,currentUserId);

                return  ResultVOUtil.success(administrativemap1);

            }else{

                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+type;
                String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Administrativemap"+File.separator;
                File  theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }
                Path paths=Paths.get(fileRealPath+filename);
                Files.write(paths,file.getBytes());

                String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Administrativemap/"+filename;



                administrativemapMapper.updateAdministrativemap(name,imgVirtualPath,id,currentUserId);

                Administrativemap administrativemap1=administrativemapMapper.getAdministrativemapById2(id,currentUserId);

                return  ResultVOUtil.success(administrativemap1);


            }

        }

        else{
            administrativemapMapper.updateAdministrativemap(name,administrativemap.getPictureurl(),id,currentUserId);

            Administrativemap administrativemap1=administrativemapMapper.getAdministrativemapById2(id,currentUserId);
            return  ResultVOUtil.success(administrativemap1);
        }



    }


}
