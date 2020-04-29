package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Famousscenery;
import com.csdl.smartplacenew.informationvo.FamoussceneryVo;
import com.csdl.smartplacenew.mapper.FamoussceneryMapper;
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

@Api(description = "风景名胜")
@RestController
@RequestMapping("/Famousscenery")
public class FamoussceneryController {


    @Resource
    FamoussceneryMapper famoussceneryMapper;

    @Resource
    UserMapper userMapper;


    @ApiOperation(value = "添加风景名胜", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        String name=request.getParameter("name");

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        if(file!=null) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String times = System.currentTimeMillis() + "";
            String filename = times + "." + type;
            String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Famousscenery" + File.separator;
            File thefile = new File(fileRealPath);
            if (!thefile.exists()) {
                thefile.mkdirs();
            }
            Path path = Paths.get(fileRealPath + filename);
            Files.write(path, file.getBytes());
            String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Famousscenery/" + filename;
            famoussceneryMapper.addFamousscenery(name, imgVirtualPath, 1,currentUserId);
        }
        else{
            famoussceneryMapper.addFamousscenery(name, null, 1,currentUserId);
        }

        Famousscenery famousscenery=famoussceneryMapper.getFamoussceneryByMaxId(currentUserId);

        return ResultVOUtil.success(famousscenery);

    }






    @ApiOperation(value = "添加风景名胜内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "风景名胜id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/addTxt")
    private void addTxt( HttpServletRequest request) throws IOException {


        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Famousscenery"+File.separator;

        File file1=new File(filepath);

        if(!file1.exists()){
            file1.mkdirs();
        }

        String filepath2=  ConfigBean.sourceRealPath+"txt"+ File.separator+"Famousscenery"+File.separator+"Famousscenery"+id+".txt";

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




    @ApiOperation(value = "上传风景名胜内容文件", notes = "")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "id", value = "华侨地名id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addTxtFile")
    private String addTxtFile(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //   Integer id=Integer.valueOf(request.getParameter("id"));


        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String times = System.currentTimeMillis() + "";

        String filename = times + "." + type;

        String filepath2=  ConfigBean.sourceRealPath+"informapict"+ File.separator+"Famousscenery"+File.separator;

        File thefile = new File(filepath2);

        if (!thefile.exists()) {

            thefile.mkdirs();

        }
        Path path = Paths.get(filepath2 + filename);
        Files.write(path, file.getBytes());//将图片存入服务器0

        String realPath= ConfigBean.sourceVirtualPath+"informapict/"+"Famousscenery/"+filename;

        return realPath;

    }


    @ApiOperation(value = "修改风景名胜内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "风景名胜id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/updateTxt")
    private void updateTxt( HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Famousscenery"+File.separator+"Famousscenery"+id+".txt";

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


    @ApiOperation(value = "查询风景名胜", notes = "")
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

            Integer count=famoussceneryMapper.getFamoussceneryCount("%"+name+"%",currentUserId);

            PageHelper.startPage(page,size);

            List<Famousscenery> famoussceneryList=famoussceneryMapper.selectFamousscenery2("%"+name+"%",currentUserId);


            List<FamoussceneryVo> famoussceneryVoArrayList=new ArrayList<>();

            for(int i=0;i<famoussceneryList.size();i++){

                Famousscenery famousscenery=famoussceneryList.get(i);

                FamoussceneryVo famoussceneryVo=new FamoussceneryVo();

                famoussceneryVo.setId(famousscenery.getId());
                famoussceneryVo.setName(famousscenery.getName());
                famoussceneryVo.setPictureUrl(famousscenery.getPictureurl());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Famousscenery"+File.separator+"Famousscenery"+famousscenery.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                famoussceneryVo.setTxt(jsonString);

                famoussceneryVoArrayList.add(famoussceneryVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(famoussceneryVoArrayList);

            return ResultVOUtil.success(listVO);


        }
        else{

            Integer count=famoussceneryMapper.getFamoussceneryCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Famousscenery> famoussceneryList=famoussceneryMapper.selectFamousscenery2(null,currentUserId);

            List<FamoussceneryVo> famoussceneryVoArrayList=new ArrayList<>();

            for(int i=0;i<famoussceneryList.size();i++){

                Famousscenery famousscenery=famoussceneryList.get(i);

                FamoussceneryVo famoussceneryVo=new FamoussceneryVo();

                famoussceneryVo.setId(famousscenery.getId());
                famoussceneryVo.setName(famousscenery.getName());
                famoussceneryVo.setPictureUrl(famousscenery.getPictureurl());


                String pathname =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Famousscenery"+File.separator+"Famousscenery"+famousscenery.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                famoussceneryVo.setTxt(jsonString);

                famoussceneryVoArrayList.add(famoussceneryVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(famoussceneryVoArrayList);

            return ResultVOUtil.success(listVO);



        }


    }






    @ApiOperation(value = "删除风景名胜内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "风景名胜id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        famoussceneryMapper.delFamousscenery(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }




    @ApiOperation(value = "删除风景名胜内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "风景名胜id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/update")
    private ResultVO update(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id = Integer.valueOf(request.getParameter("id"));

        Famousscenery famousscenery = famoussceneryMapper.getFamoussceneryById2(id,currentUserId);


        String name = request.getParameter("name");


        if (file != null) {

            if(famousscenery.getPictureurl()!=null){


             if(famousscenery.getPictureurl()!=null&&!famousscenery.getPictureurl().equals("")) {
                 String[] strings = famousscenery.getPictureurl().split("\\/");


                 File file1 = new File(ConfigBean.sourceRealPath + "information" + File.separator + "Famousscenery" + File.separator + strings[5]);

                 file1.delete();
             }

            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String times = System.currentTimeMillis() + "";
            String filename = times + "." + type;
            String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Famousscenery" + File.separator;
            File theFile = new File(fileRealPath);
            if (!theFile.exists()) {
                theFile.mkdirs();
            }
            Path paths = Paths.get(fileRealPath + filename);
            Files.write(paths, file.getBytes());

            String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Famousscenery/" + filename;


            famoussceneryMapper.updateFamousscenery(name, imgVirtualPath, id,currentUserId);

            Famousscenery famousscenery1 = famoussceneryMapper.getFamoussceneryById2(id,currentUserId);

            return ResultVOUtil.success(famousscenery1);

            }
            else{
                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                String times = System.currentTimeMillis() + "";
                String filename = times + "." + type;
                String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Famousscenery" + File.separator;
                File theFile = new File(fileRealPath);
                if (!theFile.exists()) {
                    theFile.mkdirs();
                }
                Path paths = Paths.get(fileRealPath + filename);
                Files.write(paths, file.getBytes());

                String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Famousscenery/" + filename;


                famoussceneryMapper.updateFamousscenery(name, imgVirtualPath, id,currentUserId);

                Famousscenery famousscenery1 = famoussceneryMapper.getFamoussceneryById2(id,currentUserId);

                return ResultVOUtil.success(famousscenery1);

            }

        }
        else {

            famoussceneryMapper.updateFamousscenery(name, famousscenery.getPictureurl(), id,currentUserId);

            Famousscenery famousscenery3 = famoussceneryMapper.getFamoussceneryById2(id,currentUserId);

            return ResultVOUtil.success(famousscenery3);

        }


    }




}
