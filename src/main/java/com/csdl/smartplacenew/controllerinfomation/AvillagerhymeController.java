package com.csdl.smartplacenew.controllerinfomation;

import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Avillagerhyme;
import com.csdl.smartplacenew.informationvo.AvillagerhymeSYVo;
import com.csdl.smartplacenew.informationvo.AvillagerhymeUserIdAndLonLat;
import com.csdl.smartplacenew.informationvo.AvillagerhymeVo;
import com.csdl.smartplacenew.mapper.AvillagerhymeMapper;
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

@Api(description = "古村流韵")
@RestController
@RequestMapping("/Avillagerhyme")
public class AvillagerhymeController {


     @Resource
     AvillagerhymeMapper avillagerhymeMapper;

     @Resource
    UserMapper userMapper;


    @ApiOperation(value = "添加古村流韵", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lonAndLat", value = "经纬度", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "link", value = "链接", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String name=request.getParameter("name");

        String lonAndLat=request.getParameter("lonAndLat");

        String link=request.getParameter("link");

          if(file!=null) {
              String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
              String times = System.currentTimeMillis() + "";
              String filename = times + "." + type;
              String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Avillagerhyme" + File.separator;
              File thefile = new File(fileRealPath);
              if (!thefile.exists()) {
                  thefile.mkdirs();
              }
              Path path = Paths.get(fileRealPath + filename);
              Files.write(path, file.getBytes());
              String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Avillagerhyme/" + filename;
              avillagerhymeMapper.addAvillagerhyme(name, imgVirtualPath, 1,lonAndLat,link,currentUserId);
          }
          else{
              avillagerhymeMapper.addAvillagerhyme(name, null, 1,lonAndLat,link,currentUserId);
          }

        Avillagerhyme avillagerhyme=avillagerhymeMapper.getAvillagerhymeByMaxId(currentUserId);


        return ResultVOUtil.success(avillagerhyme);

    }






    @ApiOperation(value = "添加古村流韵内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "古村流韵id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/addTxt")
    private void addTxt( HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator;

        File file1=new File(filepath);

        if(!file1.exists()){
            file1.mkdirs();
        }

        String filepath2=  ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+id+".txt";

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



    @ApiOperation(value = "上传古村流韵内容文件", notes = "")
    @ApiImplicitParams({
            // @ApiImplicitParam(name = "id", value = "华侨地名id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addTxtFile")
    private String addTxtFile(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //   Integer id=Integer.valueOf(request.getParameter("id"));


        String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String times = System.currentTimeMillis() + "";

        String filename = times + "." + type;

        String filepath2=  ConfigBean.sourceRealPath+"informapict"+ File.separator+"Avillagerhyme"+File.separator;

        File thefile = new File(filepath2);

        if (!thefile.exists()) {

            thefile.mkdirs();

        }
        Path path = Paths.get(filepath2 + filename);
        Files.write(path, file.getBytes());//将图片存入服务器0

        String realPath= ConfigBean.sourceVirtualPath+"informapict/"+"Avillagerhyme/"+filename;

        return realPath;

    }



    @ApiOperation(value = "修改古村流韵内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "古村流韵id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "txt", value = "内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/updateTxt")
    private void updateTxt( HttpServletRequest request) throws IOException {


        Integer id=Integer.valueOf(request.getParameter("id"));

        String filepath =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+id+".txt";

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

           Integer count=avillagerhymeMapper.getAvillagerhymeCount("%"+name+"%",currentUserId);

           PageHelper.startPage(page,size);

           List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName2("%"+name+"%",currentUserId);

           List<AvillagerhymeVo> avillagerhymeVoList=new ArrayList<>();

           for(int i=0;i<avillagerhymeList.size();i++){

               Avillagerhyme avillagerhyme=avillagerhymeList.get(i);

               AvillagerhymeVo avillagerhymeVo=new AvillagerhymeVo();
               avillagerhymeVo.setId(avillagerhyme.getId());
                avillagerhymeVo.setName(avillagerhyme.getName());
                avillagerhymeVo.setPictureUrl(avillagerhyme.getPictureurl());
                avillagerhymeVo.setLonAndLon(avillagerhyme.getLonandlat());


               String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+avillagerhyme.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
               //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
               //不关闭文件会导致资源的泄露，读写文件都同理
               //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
               String line=null;

               String jsonString = "";

//               FileReader reader = new FileReader(pathname);
//
//               BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

               //网友推荐更加简洁的写法
               while ((line = br.readLine()) != null) {
                   // 一次读入一行数据
                   jsonString += line;

               }


               avillagerhymeVo.setTxt(jsonString);

               avillagerhymeVoList.add(avillagerhymeVo);

           }

           ListVO listVO=new ListVO();
           listVO.setCount(count);
           listVO.setList(avillagerhymeVoList);

           return ResultVOUtil.success(listVO);


       }
       else{

           Integer count=avillagerhymeMapper.getAvillagerhymeCount(null,currentUserId);

           PageHelper.startPage(page,size);

           List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName2(null,currentUserId);

           List<AvillagerhymeVo> avillagerhymeVoList=new ArrayList<>();

           for(int i=0;i<avillagerhymeList.size();i++){
               Avillagerhyme avillagerhyme=avillagerhymeList.get(i);

               AvillagerhymeVo avillagerhymeVo=new AvillagerhymeVo();
               avillagerhymeVo.setId(avillagerhyme.getId());
               avillagerhymeVo.setName(avillagerhyme.getName());
               avillagerhymeVo.setPictureUrl(avillagerhyme.getPictureurl());
               avillagerhymeVo.setLonAndLon(avillagerhyme.getLonandlat());


               String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+avillagerhyme.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
               //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
               //不关闭文件会导致资源的泄露，读写文件都同理
               //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
               String line=null;

               String jsonString = "";

//               FileReader reader = new FileReader(pathname);
//
//               BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

               BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

               //网友推荐更加简洁的写法
               while ((line = br.readLine()) != null) {
                   // 一次读入一行数据
                   jsonString += line;


               }


               avillagerhymeVo.setTxt(jsonString);

               avillagerhymeVoList.add(avillagerhymeVo);

           }

           ListVO listVO=new ListVO();
           listVO.setCount(count);
           listVO.setList(avillagerhymeVoList);

           return ResultVOUtil.success(listVO);



       }


    }



    @ApiOperation(value = "查询古村流韵还有静态资源", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "古村流域id", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList2")
    private String getList2(HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Avillagerhyme avillagerhyme=avillagerhymeMapper.getAvillagerhymeById(id,currentUserId);

        String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+avillagerhyme.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        String line=null;

        String jsonString = "";

//               FileReader reader = new FileReader(pathname);
//
//               BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

        //网友推荐更加简洁的写法
        while ((line = br.readLine()) != null) {
            // 一次读入一行数据
            jsonString += line;

        }

        return avillagerhyme.getLonandlat()+avillagerhyme.getLink()+jsonString;



    }




    @ApiOperation(value = "查询松阳传统村落经纬度", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "intrger", paramType = "query")
    })
    @GetMapping("/getList3")
    private ResultVO getList3(HttpServletRequest request) throws IOException {

        Integer currentUserId=Integer.valueOf(request.getParameter("userid"));

        List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName2(null,currentUserId);

        List<AvillagerhymeSYVo> adminvsionVoList=new ArrayList<>();

        for(int i=0;i<avillagerhymeList.size();i++){

            Avillagerhyme avillagerhyme=avillagerhymeList.get(i);

            if(avillagerhyme.getLonandlat()!=null&&!avillagerhyme.getLonandlat().equals("")){


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+avillagerhyme.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";

//               FileReader reader = new FileReader(pathname);
//
//               BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }

                String[] lonAndlat=avillagerhyme.getLonandlat().split(",");

                AvillagerhymeSYVo avillagerhymeSYVo=new AvillagerhymeSYVo();

                avillagerhymeSYVo.setName(avillagerhyme.getName());
                avillagerhymeSYVo.setLon(Double.valueOf(lonAndlat[0]));
                avillagerhymeSYVo.setLat(Double.valueOf(lonAndlat[1]));
                avillagerhymeSYVo.setTxt(jsonString);

                adminvsionVoList.add(avillagerhymeSYVo);
            }


        }

        return  ResultVOUtil.success(adminvsionVoList);


    }




    @ApiOperation(value = "查询传统村落经纬度和用户id", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "村落名称", required = true, dataType = "string", paramType = "query")
    })
    @GetMapping("/getList4")
    private ResultVO getList4(HttpServletRequest request) throws IOException {

        String  name=request.getParameter("name");

        Avillagerhyme avillagerhyme=avillagerhymeMapper.getAvillagerhymeByName2("%"+name+"%");

        if(avillagerhyme==null){

            return ResultVOUtil.error("404","找不到村落信息");
        }

        AvillagerhymeUserIdAndLonLat avillagerhymeUserIdAndLonLat=new AvillagerhymeUserIdAndLonLat();
        avillagerhymeUserIdAndLonLat.setUserId(
                avillagerhyme.getUserid());

        if(avillagerhyme.getLonandlat()!=null) {
            String[] strings=avillagerhyme.getLonandlat().split(",");
            avillagerhymeUserIdAndLonLat.setLon(strings[0]);
            avillagerhymeUserIdAndLonLat.setLat(strings[1]);


        }
        return ResultVOUtil.success(avillagerhymeUserIdAndLonLat);




    }




    @ApiOperation(value = "查询松阳传统村落详细信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "intrger", paramType = "query"),

            @ApiImplicitParam(name = "name", value = "传统村落名称", required = true, dataType = "intrger", paramType = "query")
    })
    @GetMapping("/getInfoByName")
    private ResultVO getInfoByName(HttpServletRequest request) throws IOException {

        Integer currentUserId = Integer.valueOf(request.getParameter("userid"));

        String name=request.getParameter("name");

        Avillagerhyme avillagerhyme = avillagerhymeMapper.getAvillagerhymeByName(name, currentUserId);

                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Avillagerhyme" + File.separator + "Avillagerhyme" + avillagerhyme.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";

//               FileReader reader = new FileReader(pathname);
//
//               BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }

                String[] lonAndlat = avillagerhyme.getLonandlat().split(",");

                AvillagerhymeSYVo avillagerhymeSYVo = new AvillagerhymeSYVo();

                avillagerhymeSYVo.setName(avillagerhyme.getName());
                avillagerhymeSYVo.setLon(Double.valueOf(lonAndlat[0]));
                avillagerhymeSYVo.setLat(Double.valueOf(lonAndlat[1]));
                avillagerhymeSYVo.setTxt(jsonString);
        avillagerhymeSYVo.setLink(avillagerhyme.getLink());

            return ResultVOUtil.success(avillagerhymeSYVo);

    }



    @ApiOperation(value = "删除古村流韵内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "古村流韵id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        avillagerhymeMapper.delAvillagerhyme(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }




    @ApiOperation(value = "修改古村流韵内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "古村流韵id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "lonAndLat", value = "经纬度", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "link", value = "链接", required = false, dataType = "string", paramType = "query"),
    })
    @PostMapping("/update")
    private ResultVO update(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer id=Integer.valueOf(request.getParameter("id"));

        Avillagerhyme avillagerhyme=avillagerhymeMapper.getAvillagerhymeById2(id,currentUserId);


        String name=request.getParameter("name");

        String lonAndLat=request.getParameter("lonAndLat");

        String link=request.getParameter("link");



        if(file!=null) {

            if(avillagerhyme.getPictureurl()!=null){

             if(avillagerhyme.getPictureurl()!=null&&!avillagerhyme.getPictureurl().equals("")) {
                 String[] strings = avillagerhyme.getPictureurl().split("\\/");


                 File file1 = new File(ConfigBean.sourceRealPath + "information" + File.separator + "Avillagerhyme" + File.separator + strings[5]);

                 file1.delete();
             }

            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String times=System.currentTimeMillis()+"";
            String filename=times+"."+type;
            String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Avillagerhyme"+File.separator;
            File  theFile=new File(fileRealPath);
            if(!theFile.exists()){
                theFile.mkdirs();
            }
            Path paths=Paths.get(fileRealPath+filename);
            Files.write(paths,file.getBytes());

            String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Avillagerhyme/"+filename;


            avillagerhymeMapper.updateAvillagerhyme(name,imgVirtualPath,lonAndLat,link,id,currentUserId);

            Avillagerhyme avillagerhyme2=avillagerhymeMapper.getAvillagerhymeById2(id,currentUserId);

            return  ResultVOUtil.success(avillagerhyme2);

            }

            else{

                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+type;
                String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Avillagerhyme"+File.separator;
                File  theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }
                Path paths=Paths.get(fileRealPath+filename);
                Files.write(paths,file.getBytes());

                String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Avillagerhyme/"+filename;


                avillagerhymeMapper.updateAvillagerhyme(name,imgVirtualPath,lonAndLat,link,id,currentUserId);

                Avillagerhyme avillagerhyme2=avillagerhymeMapper.getAvillagerhymeById2(id,currentUserId);

                return  ResultVOUtil.success(avillagerhyme2);


            }

        }

        else{

            avillagerhymeMapper.updateAvillagerhyme(name,avillagerhyme.getPictureurl(),lonAndLat,link,id,currentUserId);


            Avillagerhyme avillagerhyme3=avillagerhymeMapper.getAvillagerhymeById2(id,currentUserId);

            return  ResultVOUtil.success(avillagerhyme3);
        }


    }






}
