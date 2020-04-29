package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Leisuretrip;
import com.csdl.smartplacenew.informationvo.LeisuretripVo;
import com.csdl.smartplacenew.mapper.LeisuretripMapper;
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

@Api(description = "休闲之旅")
@RestController
@RequestMapping("/Leisuretrip")
public class LeisuretripController {


    @Resource
    LeisuretripMapper leisuretripMapper;

    @Resource
    UserMapper userMapper;

    @ApiOperation(value = "添加休闲之旅", notes = "")
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
            String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Leisuretrip" + File.separator;
            File thefile = new File(fileRealPath);
            if (!thefile.exists()) {
                thefile.mkdirs();
            }
            Path path = Paths.get(fileRealPath + filename);
            Files.write(path, file.getBytes());
            String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Leisuretrip/" + filename;
            leisuretripMapper.addLeisuretrip(name, imgVirtualPath, 1,currentUserId);

        }
        else{

            leisuretripMapper.addLeisuretrip(name, null, 1,currentUserId);

        }

        Leisuretrip leisuretrip=leisuretripMapper.getLeisuretripByMaxId(currentUserId);

        return ResultVOUtil.success(leisuretrip);

    }






    @ApiOperation(value = "添加休闲之旅内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "休闲之旅id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/addTxt")
    private void addTxt( HttpServletRequest request) throws IOException {


        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator;

        File file1=new File(filepath);

        if(!file1.exists()){
            file1.mkdirs();
        }

        String filepath2=  ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+id+".txt";

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



    @ApiOperation(value = "上传休闲之旅内容文件", notes = "")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "id", value = "华侨地名id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addTxtFile")
    private String addTxtFile(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //   Integer id=Integer.valueOf(request.getParameter("id"));


        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String times = System.currentTimeMillis() + "";

        String filename = times + "." + type;

        String filepath2=  ConfigBean.sourceRealPath+"informapict"+ File.separator+"Leisuretrip"+File.separator;

        File thefile = new File(filepath2);

        if (!thefile.exists()) {

            thefile.mkdirs();

        }
        Path path = Paths.get(filepath2 + filename);
        Files.write(path, file.getBytes());//将图片存入服务器0

        String realPath= ConfigBean.sourceVirtualPath+"informapict/"+"Leisuretrip/"+filename;

        return realPath;

    }





    @ApiOperation(value = "修改休闲之旅内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "休闲之旅id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/updateTxt")
    private void updateTxt( HttpServletRequest request) throws IOException {


        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+id+".txt";

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


    @ApiOperation(value = "查询休闲之旅", notes = "")
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

            Integer count=leisuretripMapper.getLeisuretripCount("%"+name+"%",currentUserId);

            PageHelper.startPage(page,size);

            List<Leisuretrip> leisuretripList=leisuretripMapper.selectLeisuretripByName2("%"+name+"%",currentUserId);

            List<LeisuretripVo> leisuretripVoArrayList=new ArrayList<>();

            for(int i=0;i<leisuretripList.size();i++){

                Leisuretrip leisuretrip=leisuretripList.get(i);

                LeisuretripVo leisuretripVo=new LeisuretripVo();

                leisuretripVo.setId(leisuretrip.getId());
                leisuretripVo.setName(leisuretrip.getName());
                leisuretripVo.setPictureUrl(leisuretrip.getPictureurl());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+leisuretrip.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                leisuretripVo.setTxt(jsonString);

                leisuretripVoArrayList.add(leisuretripVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(leisuretripVoArrayList);

            return ResultVOUtil.success(listVO);


        }
        else{

            Integer count=leisuretripMapper.getLeisuretripCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Leisuretrip> leisuretripList=leisuretripMapper.selectLeisuretripByName2(null,currentUserId);

            List<LeisuretripVo> leisuretripVoArrayList=new ArrayList<>();

            for(int i=0;i<leisuretripList.size();i++){

                Leisuretrip leisuretrip=leisuretripList.get(i);

                LeisuretripVo leisuretripVo=new LeisuretripVo();

                leisuretripVo.setId(leisuretrip.getId());
                leisuretripVo.setName(leisuretrip.getName());
                leisuretripVo.setPictureUrl(leisuretrip.getPictureurl());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+leisuretrip.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                leisuretripVo.setTxt(jsonString);

                leisuretripVoArrayList.add(leisuretripVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(leisuretripVoArrayList);

            return ResultVOUtil.success(listVO);


        }


    }



//    @ApiOperation(value = "取到休闲之旅文章内容", notes = "")
//    @ApiImplicitParams({
//
//    })
//    @PostMapping("/getList2")
//    private ResultVO getList2( HttpServletRequest request) throws IOException {
//
//
//        String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+4+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
//        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
//        //不关闭文件会导致资源的泄露，读写文件都同理
//        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
//        String line=null;
//
//        String jsonString = "";
//
//
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));
//
//        //网友推荐更加简洁的写法
//        while ((line = br.readLine()) != null) {
//            // 一次读入一行数据
//            jsonString += line;
//
//        }
//
//        return ResultVOUtil.success(jsonString);
//
//
//    }
//
//




    @ApiOperation(value = "删除休闲之旅内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "休闲之旅id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) throws IOException {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer id=Integer.valueOf(request.getParameter("id"));

        leisuretripMapper.delLeisuretrip(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }




    @ApiOperation(value = "修改休闲之旅内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "休闲之旅id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/update")
    private ResultVO update(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer id = Integer.valueOf(request.getParameter("id"));

        Leisuretrip leisuretrip = leisuretripMapper.getLeisuretripById2(id,currentUserId);

        String name = request.getParameter("name");


        if (file != null) {

            if (leisuretrip.getPictureurl() != null) {

                if( leisuretrip.getPictureurl()!=null&&! leisuretrip.getPictureurl().equals("")) {

                    String[] strings = leisuretrip.getPictureurl().split("\\/");

                    File file1 = new File(ConfigBean.sourceRealPath + "information" + File.separator + "Leisuretrip" + File.separator + strings[5]);

                    if (file1.exists()) {

                        file1.delete();

                    }

                }

                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                String times = System.currentTimeMillis() + "";
                String filename = times + "." + type;
                String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Leisuretrip" + File.separator;
                File theFile = new File(fileRealPath);
                if (!theFile.exists()) {
                    theFile.mkdirs();
                }
                Path paths = Paths.get(fileRealPath + filename);
                Files.write(paths, file.getBytes());

                String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Leisuretrip/" + filename;


                leisuretripMapper.updateLeisuretrip(name, imgVirtualPath, id,currentUserId);

                Leisuretrip leisuretrip1 = leisuretripMapper.getLeisuretripById2(id,currentUserId);

                return ResultVOUtil.success(leisuretrip1);


            }
            else{


                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                String times = System.currentTimeMillis() + "";
                String filename = times + "." + type;
                String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Leisuretrip" + File.separator;
                File theFile = new File(fileRealPath);
                if (!theFile.exists()) {
                    theFile.mkdirs();
                }
                Path paths = Paths.get(fileRealPath + filename);
                Files.write(paths, file.getBytes());

                String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Leisuretrip/" + filename;

                leisuretripMapper.updateLeisuretrip(name, imgVirtualPath, id,currentUserId);

                Leisuretrip leisuretrip1 = leisuretripMapper.getLeisuretripById2(id,currentUserId);

                return ResultVOUtil.success(leisuretrip1);


            }
    }
        else{
            leisuretripMapper.updateLeisuretrip(name, leisuretrip.getPictureurl(), id,currentUserId);

            Leisuretrip leisuretrip1 = leisuretripMapper.getLeisuretripById2(id,currentUserId);

            return ResultVOUtil.success(leisuretrip1);
        }



    }




}

