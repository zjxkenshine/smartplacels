package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Nolegaculture;
import com.csdl.smartplacenew.informationvo.NolegacultureVo;
import com.csdl.smartplacenew.mapper.NolegacultureMapper;
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

@Api(description = "非遗文化")
@RestController
@RequestMapping("/Nolegaculture")
public class NolegacultureController {



    @Resource
    NolegacultureMapper nolegacultureMapper;

    @Resource
    UserMapper userMapper;


    @ApiOperation(value = "添加非遗文化", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "abbreviation", value = "简称", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String name=request.getParameter("name");

        String abbreviation=request.getParameter("abbreviation");


        if(file!=null) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String times = System.currentTimeMillis() + "";
            String filename = times + "." + type;
            String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Nolegaculture" + File.separator;
            File thefile = new File(fileRealPath);
            if (!thefile.exists()) {
                thefile.mkdirs();
            }
            Path path = Paths.get(fileRealPath + filename);
            Files.write(path, file.getBytes());
            String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Nolegaculture/" + filename;
            nolegacultureMapper.addNolegaculture(name, imgVirtualPath, 1,currentUserId,abbreviation);
        }
        else{
            nolegacultureMapper.addNolegaculture(name, null, 1,currentUserId,abbreviation);
        }


        Nolegaculture nolegaculture=nolegacultureMapper.getNolegacultureByMaxId(currentUserId);


        return ResultVOUtil.success(nolegaculture);

    }






    @ApiOperation(value = "添加非遗文化内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "非遗文化id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/addTxt")
    private void addTxt( HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator;

        File file1=new File(filepath);

        if(!file1.exists()){
            file1.mkdirs();
        }

        String filepath2=  ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator+"Nolegaculture"+id+".txt";

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



    @ApiOperation(value = "上传非遗文化内容文件", notes = "")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "id", value = "华侨地名id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addTxtFile")
    private String addTxtFile(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //   Integer id=Integer.valueOf(request.getParameter("id"));


        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String times = System.currentTimeMillis() + "";

        String filename = times + "." + type;

        String filepath2=  ConfigBean.sourceRealPath+"informapict"+ File.separator+"Nolegaculture"+File.separator;

        File thefile = new File(filepath2);

        if (!thefile.exists()) {

            thefile.mkdirs();

        }
        Path path = Paths.get(filepath2 + filename);
        Files.write(path, file.getBytes());//将图片存入服务器0

        String realPath= ConfigBean.sourceVirtualPath+"informapict/"+"Nolegaculture/"+filename;

        return realPath;

    }


    @ApiOperation(value = "修改非遗文化内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "非遗文化id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/updateTxt")
    private void updateTxt( HttpServletRequest request) throws IOException {


        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator+"Nolegaculture"+id+".txt";

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


    @ApiOperation(value = "查询古村流韵", notes = "")
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

            Integer count=nolegacultureMapper.getNolegacultureCount("%"+name+"%",currentUserId);

            PageHelper.startPage(page,size);

            List<Nolegaculture> nolegacultureList=nolegacultureMapper.selectNolegaculture2("%"+name+"%",currentUserId);

            List<NolegacultureVo> nolegacultureVoArrayList=new ArrayList<>();

            for(int i=0;i<nolegacultureList.size();i++){

                Nolegaculture nolegaculture=nolegacultureList.get(i);

                NolegacultureVo nolegacultureVo=new NolegacultureVo();

                nolegacultureVo.setId(nolegaculture.getId());
                nolegacultureVo.setName(nolegaculture.getName());
                nolegacultureVo.setPictureUrl(nolegaculture.getPictureurl());
                nolegacultureVo.setAbbreviation(nolegaculture.getAbbreviation());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator+"Nolegaculture"+nolegaculture.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                nolegacultureVo.setTxt(jsonString);

                nolegacultureVoArrayList.add(nolegacultureVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(nolegacultureVoArrayList);

            return ResultVOUtil.success(listVO);


        }
        else{

            Integer count=nolegacultureMapper.getNolegacultureCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Nolegaculture> nolegacultureList=nolegacultureMapper.selectNolegaculture2(null,currentUserId);

            List<NolegacultureVo> nolegacultureVoArrayList=new ArrayList<>();

            for(int i=0;i<nolegacultureList.size();i++){

                Nolegaculture nolegaculture=nolegacultureList.get(i);

                NolegacultureVo nolegacultureVo=new NolegacultureVo();

                nolegacultureVo.setId(nolegaculture.getId());
                nolegacultureVo.setName(nolegaculture.getName());
                nolegacultureVo.setPictureUrl(nolegaculture.getPictureurl());
                nolegacultureVo.setAbbreviation(nolegaculture.getAbbreviation());


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator+"Nolegaculture"+nolegaculture.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                nolegacultureVo.setTxt(jsonString);

                nolegacultureVoArrayList.add(nolegacultureVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(nolegacultureVoArrayList);

            return ResultVOUtil.success(listVO);




        }


    }






    @ApiOperation(value = "删除非遗文化内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "非遗文化id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) throws IOException {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        nolegacultureMapper.delNolegaculture(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }




    @ApiOperation(value = "修改非遗文化内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "非遗文化id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "abbreviation", value = "简称", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/update")
    private ResultVO update(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Nolegaculture nolegaculture=nolegacultureMapper.getNolegacultureById2(id,currentUserId);


        String name=request.getParameter("name");

        String abbreviation=request.getParameter("abbreviation");


        if(file!=null) {

            if(nolegaculture.getPictureurl()!=null){

                if(nolegaculture.getPictureurl()!=null&&!nolegaculture.getPictureurl().equals("")) {
                    String[] strings = nolegaculture.getPictureurl().split("\\/");

                    File file1 = new File(ConfigBean.sourceRealPath + "information" + File.separator + "Nolegaculture" + File.separator + strings[5]);

                    file1.delete();
                }

            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String times=System.currentTimeMillis()+"";
            String filename=times+"."+type;
            String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Nolegaculture"+File.separator;
            File  theFile=new File(fileRealPath);
            if(!theFile.exists()){
                theFile.mkdirs();
            }
            Path paths=Paths.get(fileRealPath+filename);
            Files.write(paths,file.getBytes());

            String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Nolegaculture/"+filename;



            nolegacultureMapper.updateNolegaculture(name,imgVirtualPath,abbreviation,id,currentUserId);

            Nolegaculture nolegaculture2=nolegacultureMapper.getNolegacultureById2(id,currentUserId);

            return  ResultVOUtil.success(nolegaculture2);

            }else{

                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+type;
                String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Nolegaculture"+File.separator;
                File  theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }
                Path paths=Paths.get(fileRealPath+filename);
                Files.write(paths,file.getBytes());

                String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Nolegaculture/"+filename;



                nolegacultureMapper.updateNolegaculture(name,imgVirtualPath,abbreviation,id,currentUserId);

                Nolegaculture nolegaculture2=nolegacultureMapper.getNolegacultureById2(id,currentUserId);

                return  ResultVOUtil.success(nolegaculture2);


            }

        }

        else{
            nolegacultureMapper.updateNolegaculture(name,nolegaculture.getPictureurl(),abbreviation,id,currentUserId);

            Nolegaculture nolegaculture2=nolegacultureMapper.getNolegacultureById2(id,currentUserId);

            return  ResultVOUtil.success(nolegaculture2);
        }



    }


}
