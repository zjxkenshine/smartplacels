package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.*;
import com.csdl.smartplacenew.informationvo.*;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Admindivsion;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Api("获取总的信息")
@RestController
@RequestMapping("/Allinforma")
public class AllinformaController {


    @Resource
    AvillagerhymeMapper avillagerhymeMapper;//行政村名

    @Resource
    ChafoodMapper chafoodMapper;

    @Resource
    FamoussceneryMapper famoussceneryMapper;

    @Resource
    FocustomsMapper focustomsMapper;  //文旅文化

    @Resource
    NolegacultureMapper nolegacultureMapper; //红色地名

    @Resource
    OcplacenamesMapper ocplacenamesMapper;  //廊桥地名

    @Resource
    PlacenacultuMapper placenacultuMapper; //地名文化

    @Resource
    StreetplanaMapper streetplanaMapper;//街巷地名

    @Resource
    UserMapper userMapper;

    @Resource
    JinliterbrigaMapper jinliterbrigaMapper;

    @Resource
    ImageplacenamesMapper imageplacenamesMapper;//影像地名

    @Resource
    CultuinsurunitMapper cultuinsurunitMapper;

    @Resource
    LeisuretripMapper leisuretripMapper;

    @Resource
    AdministrativemapMapper administrativemapMapper; //庆元行政区划图

    @Resource
    DictionaryplacenamesMapper dictionaryplacenamesMapper;    //地名词典

    @Resource
    GazetteerMapper gazetteerMapper; //地名志

    @Resource
    StangeogrnamesMapper stangeogrnamesMapper;//标准地名录

    @Resource
    private AdmindivsionMapper admindivsionMapper;//行政区划






    @ApiOperation(value = "获取总的信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userid", value = "用户id", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "seniorkey", value = "高级关键词", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lowerkey", value = "低级关键词", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) throws IOException {

//        User user= UserSecurity.getCurrentUser(request,userMapper);
//        Integer currentUserId=user.getId();

        Integer currendId=0;

        if(request.getParameter("userid")!=null&&!request.getParameter("userid").equals("")){

            currendId=Integer.valueOf(request.getParameter("userid"));
        }

        Integer seniorkey=Integer.valueOf(request.getParameter("seniorkey"));

        String name=request.getParameter("name");


        if(seniorkey==1){

            List<Ocplacenames> ocplacenamesList=ocplacenamesMapper.selectOcplacenames(null,currendId);

            return ResultVOUtil.success(ocplacenamesList);

        }


        if(seniorkey==2){

            if(currendId==15&&name!=null){

                List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName("%"+name+"%",currendId);

                return ResultVOUtil.success(avillagerhymeList);

            }

            else{

                List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName(null,currendId);

                return ResultVOUtil.success(avillagerhymeList);

            }


        }


         if(seniorkey==3){

             List<Chafood>  chafoodList=chafoodMapper.selectChafoodByName(null,currendId);

             return ResultVOUtil.success(chafoodList);

         }

          if(seniorkey==4){
              Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));
              if(currendId==15||currendId==16){

                  if(lowerkey==1){
                      List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey1",currendId);

                      return ResultVOUtil.success(focustomsList);
                  }

                  if(lowerkey==2){
                      List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey2",currendId);

                      return ResultVOUtil.success(focustomsList);
                  }

                  if(lowerkey==3){
                      List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey3",currendId);

                      return ResultVOUtil.success(focustomsList);
                  }

                  if(lowerkey==4){
                      List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey4",currendId);

                      return ResultVOUtil.success(focustomsList);
                  }

              }else{


                      List<Focustoms> focustomsList=focustomsMapper.selectFocustoms(null,currendId);

                      return ResultVOUtil.success(focustomsList);

              }



          }


          if(seniorkey==5){

              List<Famousscenery> famoussceneryList=famoussceneryMapper.selectFamousscenery(null,currendId);

              return ResultVOUtil.success(famoussceneryList);

          }


          if(seniorkey==6){

              List<Nolegaculture> nolegacultureList=nolegacultureMapper.selectNolegaculture(null,currendId);

              return ResultVOUtil.success(nolegacultureList);

          }

          if(seniorkey==7){

              Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

              if(lowerkey==1) {

                  List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey1",currendId);

                  return ResultVOUtil.success(placenacultuList);

              }

              if(lowerkey==2) {

                  List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey2",currendId);

                  return ResultVOUtil.success(placenacultuList);

              }

              if(lowerkey==3) {

                  List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey3",currendId);

                  return ResultVOUtil.success(placenacultuList);

              }

              if(lowerkey==4) {

                  List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey4",currendId);

                  return ResultVOUtil.success(placenacultuList);

              }

              if(lowerkey==5) {

                  List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey5",currendId);

                  return ResultVOUtil.success(placenacultuList);

              }



          }


          if(seniorkey==8){

              List<Streetplana> streetplanaList=streetplanaMapper.selectStreetplana(null,currendId);

              return ResultVOUtil.success(streetplanaList);

          }



        if(seniorkey==9){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey1" ,currendId);

                return ResultVOUtil.success(jinliterbrigaList);

            }

            if(lowerkey==2) {

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey2" ,currendId);

                return ResultVOUtil.success(jinliterbrigaList);
            }

            if(lowerkey==3) {

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey3" ,currendId);

                return ResultVOUtil.success(jinliterbrigaList);
            }

            if(lowerkey==4) {

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey4" ,currendId);

                return ResultVOUtil.success(jinliterbrigaList);
            }

            if(lowerkey==5) {

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey5" ,currendId);

                return ResultVOUtil.success(jinliterbrigaList);
            }

            if(lowerkey==6) {

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey6" ,currendId);

                return ResultVOUtil.success(jinliterbrigaList);
            }


        }


       //影像地名
        if(seniorkey==10){

            List<Imageplacenames> imageplacenamesList=imageplacenamesMapper.getImageplacenamesByNameAndUserid(null,currendId);

            return  ResultVOUtil.success(imageplacenamesList);

        }


        //文保单位
        if(seniorkey==11){

            List<Cultuinsurunit> cultuinsurunitList=cultuinsurunitMapper.getCultuinsurunitByNameAndUserid(null,currendId);

            return  ResultVOUtil.success(cultuinsurunitList);

        }


        //休闲之旅
        if(seniorkey==12){

            List<Leisuretrip> leisuretripList=leisuretripMapper.getLeisuretripByNameAndUserid(null,currendId);

            return ResultVOUtil.success(leisuretripList);

        }






        return ResultVOUtil.success();


    }




    @ApiOperation(value = "获取庆元行政区划图", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),


    })
    @GetMapping("/getList2")
    private ResultVO getList2(HttpServletRequest request) throws IOException {

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        String  name=request.getParameter("name");

        if(name!=null&&!name.equals("")) {

            Integer count = administrativemapMapper.getAdministrativemapCount("%"+name+"%", userid);

            PageHelper.startPage(page, size);

            List<Administrativemap> administrativemapList = administrativemapMapper.selectAdministrativemap("%"+name+"%", userid);

            List<AdministrativemapVo> administrativemapVoArrayList = new ArrayList<>();

            for (int i = 0; i < administrativemapList.size(); i++) {

                Administrativemap administrativemap = administrativemapList.get(i);

                AdministrativemapVo administrativemapVo = new AdministrativemapVo();

                administrativemapVo.setId(administrativemap.getId());
                administrativemapVo.setName(administrativemap.getName());
                administrativemapVo.setPictureUrl(administrativemap.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Administrativemap" + File.separator + "Administrativemap" + administrativemap.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

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

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(administrativemapVoArrayList);

            return ResultVOUtil.success(listVO);
        }else{

            Integer count = administrativemapMapper.getAdministrativemapCount(null, userid);

            PageHelper.startPage(page, size);

            List<Administrativemap> administrativemapList = administrativemapMapper.selectAdministrativemap(null, userid);

            List<AdministrativemapVo> administrativemapVoArrayList = new ArrayList<>();

            for (int i = 0; i < administrativemapList.size(); i++) {

                Administrativemap administrativemap = administrativemapList.get(i);

                AdministrativemapVo administrativemapVo = new AdministrativemapVo();

                administrativemapVo.setId(administrativemap.getId());
                administrativemapVo.setName(administrativemap.getName());
                administrativemapVo.setPictureUrl(administrativemap.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Administrativemap" + File.separator + "Administrativemap" + administrativemap.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

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

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(administrativemapVoArrayList);

            return ResultVOUtil.success(listVO);


        }

    }



    @ApiOperation(value = "获取庆元行政区划图总的信息", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getAdministrativemapInfoById")
    private String getAdministrativemapInfoById(HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        Administrativemap administrativemap=administrativemapMapper.getAdministrativemapById(id,userid);

        String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Administrativemap"+File.separator+"Administrativemap"+administrativemap.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

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

        return jsonString;



    }



    @ApiOperation(value = "获取地名词典", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名字", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),


    })
    @GetMapping("/getList3")
    private ResultVO getList3(HttpServletRequest request) throws IOException {

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        String name=request.getParameter("name");

        if(name!=null&&!name.equals("")) {
            Integer count = dictionaryplacenamesMapper.getDictionaryplacenamesCount("%"+name+"%", userid);

            PageHelper.startPage(page, size);

            List<Dictionaryplacenames> dictionaryplacenamesList = dictionaryplacenamesMapper.selectDictionaryplacenames("%"+name+"%", userid);

            List<DictionaryplacenamesVo> dictionaryplacenamesVoArrayList = new ArrayList<>();

            for (int i = 0; i < dictionaryplacenamesList.size(); i++) {

                Dictionaryplacenames dictionaryplacenames = dictionaryplacenamesList.get(i);

                DictionaryplacenamesVo dictionaryplacenamesVo = new DictionaryplacenamesVo();

                dictionaryplacenamesVo.setId(dictionaryplacenames.getId());
                dictionaryplacenamesVo.setName(dictionaryplacenames.getName());
                dictionaryplacenamesVo.setPictureUrl(dictionaryplacenames.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Dictionaryplacenames" + File.separator + "Dictionaryplacenames" + dictionaryplacenames.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                dictionaryplacenamesVo.setTxt(jsonString);

                dictionaryplacenamesVoArrayList.add(dictionaryplacenamesVo);

            }

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(dictionaryplacenamesVoArrayList);

            return ResultVOUtil.success(listVO);
        }else{

            Integer count = dictionaryplacenamesMapper.getDictionaryplacenamesCount(null, userid);

            PageHelper.startPage(page, size);

            List<Dictionaryplacenames> dictionaryplacenamesList = dictionaryplacenamesMapper.selectDictionaryplacenames(null, userid);

            List<DictionaryplacenamesVo> dictionaryplacenamesVoArrayList = new ArrayList<>();

            for (int i = 0; i < dictionaryplacenamesList.size(); i++) {

                Dictionaryplacenames dictionaryplacenames = dictionaryplacenamesList.get(i);

                DictionaryplacenamesVo dictionaryplacenamesVo = new DictionaryplacenamesVo();

                dictionaryplacenamesVo.setId(dictionaryplacenames.getId());
                dictionaryplacenamesVo.setName(dictionaryplacenames.getName());
                dictionaryplacenamesVo.setPictureUrl(dictionaryplacenames.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Dictionaryplacenames" + File.separator + "Dictionaryplacenames" + dictionaryplacenames.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                dictionaryplacenamesVo.setTxt(jsonString);

                dictionaryplacenamesVoArrayList.add(dictionaryplacenamesVo);

            }

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(dictionaryplacenamesVoArrayList);

            return ResultVOUtil.success(listVO);

        }
    }









    @ApiOperation(value = "获取地名词典总的信息", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getDictionaryplacenamesInfoById")
    private String getDictionaryplacenamesInfoById(HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        Dictionaryplacenames dictionaryplacenames=dictionaryplacenamesMapper.getDictionaryplacenamesById(id,userid);

        String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Dictionaryplacenames"+File.separator+"Dictionaryplacenames"+dictionaryplacenames.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

        String line=null;

        String jsonString = "";



        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

        //网友推荐更加简洁的写法
        while ((line = br.readLine()) != null) {
            // 一次读入一行数据
            jsonString += line;


        }

        return jsonString;



    }






    @ApiOperation(value = "获取地名志", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),


    })
    @GetMapping("/getList4")
    private ResultVO getList4(HttpServletRequest request) throws IOException {

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        String name=request.getParameter("name");

        if(name!=null&&!name.equals("")) {

            Integer count = gazetteerMapper.getGazetteerCount("%"+name+"%", userid);

            PageHelper.startPage(page, size);

            List<Gazetteer> gazetteerList = gazetteerMapper.selectGazetteer("%"+name+"%", userid);
            List<GazetteerVo> gazetteerVoArrayList = new ArrayList<>();

            for (int i = 0; i < gazetteerList.size(); i++) {

                Gazetteer gazetteer = gazetteerList.get(i);

                GazetteerVo gazetteerVo = new GazetteerVo();

                gazetteerVo.setId(gazetteer.getId());
                gazetteerVo.setName(gazetteer.getName());
                gazetteerVo.setPictureUrl(gazetteer.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Gazetteer" + File.separator + "Gazetteer" + gazetteer.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                gazetteerVo.setTxt(jsonString);

                gazetteerVoArrayList.add(gazetteerVo);

            }

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(gazetteerVoArrayList);

            return ResultVOUtil.success(listVO);
        }else{
            Integer count = gazetteerMapper.getGazetteerCount(null, userid);

            PageHelper.startPage(page, size);

            List<Gazetteer> gazetteerList = gazetteerMapper.selectGazetteer(null, userid);

            List<GazetteerVo> gazetteerVoArrayList = new ArrayList<>();

            for (int i = 0; i < gazetteerList.size(); i++) {

                Gazetteer gazetteer = gazetteerList.get(i);

                GazetteerVo gazetteerVo = new GazetteerVo();

                gazetteerVo.setId(gazetteer.getId());
                gazetteerVo.setName(gazetteer.getName());
                gazetteerVo.setPictureUrl(gazetteer.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Gazetteer" + File.separator + "Gazetteer" + gazetteer.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                gazetteerVo.setTxt(jsonString);

                gazetteerVoArrayList.add(gazetteerVo);

            }

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(gazetteerVoArrayList);

            return ResultVOUtil.success(listVO);


        }
    }






    @ApiOperation(value = "获取地名志总的信息", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getGazetteerInfoById")
    private String getGazetteerInfoById(HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        Gazetteer gazetteer=gazetteerMapper.getGazetteerById(id,userid);

        String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Gazetteer"+File.separator+"Gazetteer"+gazetteer.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

        String line=null;

        String jsonString = "";



        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

        //网友推荐更加简洁的写法
        while ((line = br.readLine()) != null) {
            // 一次读入一行数据
            jsonString += line;


        }

        return jsonString;



    }




    @ApiOperation(value = "获取标准地名录", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList5")
    private ResultVO getList5(HttpServletRequest request) throws IOException {

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        String name=request.getParameter("name");

        if(name!=null&&!name.equals("")) {

            Integer count = stangeogrnamesMapper.getStangeogrnamesCount("%"+name+"%", userid);

            PageHelper.startPage(page, size);

            List<Stangeogrnames> stangeogrnamesList = stangeogrnamesMapper.selectStangeogrnames("%"+name+"%", userid);

            List<StangeogrnamesVo> stangeogrnamesVoArrayList = new ArrayList<>();

            for (int i = 0; i < stangeogrnamesList.size(); i++) {

                Stangeogrnames stangeogrnames = stangeogrnamesList.get(i);

                StangeogrnamesVo stangeogrnamesVo = new StangeogrnamesVo();

                stangeogrnamesVo.setId(stangeogrnames.getId());
                stangeogrnamesVo.setName(stangeogrnames.getName());
                stangeogrnamesVo.setPictureUrl(stangeogrnames.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Stangeogrnames" + File.separator + "Stangeogrnames" + stangeogrnames.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                stangeogrnamesVo.setTxt(jsonString);

                stangeogrnamesVoArrayList.add(stangeogrnamesVo);

            }

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(stangeogrnamesVoArrayList);

            return ResultVOUtil.success(listVO);
        }else{

            Integer count = stangeogrnamesMapper.getStangeogrnamesCount(null, userid);

            PageHelper.startPage(page, size);

            List<Stangeogrnames> stangeogrnamesList = stangeogrnamesMapper.selectStangeogrnames(null, userid);

            List<StangeogrnamesVo> stangeogrnamesVoArrayList = new ArrayList<>();

            for (int i = 0; i < stangeogrnamesList.size(); i++) {

                Stangeogrnames stangeogrnames = stangeogrnamesList.get(i);

                StangeogrnamesVo stangeogrnamesVo = new StangeogrnamesVo();

                stangeogrnamesVo.setId(stangeogrnames.getId());
                stangeogrnamesVo.setName(stangeogrnames.getName());
                stangeogrnamesVo.setPictureUrl(stangeogrnames.getPictureurl());


                String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Stangeogrnames" + File.separator + "Stangeogrnames" + stangeogrnames.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line = null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                stangeogrnamesVo.setTxt(jsonString);

                stangeogrnamesVoArrayList.add(stangeogrnamesVo);

            }

            ListVO listVO = new ListVO();
            listVO.setCount(count);
            listVO.setList(stangeogrnamesVoArrayList);

            return ResultVOUtil.success(listVO);

        }
    }







    @ApiOperation(value = "获取标准地名录总的信息", notes = "")
    @ApiImplicitParams({


            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = true, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getStangeogrnamesInfoById")
    private String getStangeogrnamesInfoById(HttpServletRequest request) throws IOException {

        Integer id=Integer.valueOf(request.getParameter("id"));

        Integer userid=Integer.valueOf(request.getParameter("userid"));

        Stangeogrnames stangeogrnames=stangeogrnamesMapper.getStangeogrnamesById(id,userid);

        String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Stangeogrnames"+File.separator+"Stangeogrnames"+stangeogrnames.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

        String line=null;

        String jsonString = "";



        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

        //网友推荐更加简洁的写法
        while ((line = br.readLine()) != null) {
            // 一次读入一行数据
            jsonString += line;


        }

        return jsonString;



    }





    @ApiOperation(value = "获取总的信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "seniorkey", value = "高级关键词", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lowerkey", value = "低级关键词", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getInfoById")
    private String getInfoById(HttpServletRequest request) throws IOException {


        Integer seniorkey=Integer.valueOf(request.getParameter("seniorkey"));

        Integer id=Integer.valueOf(request.getParameter("id"));

        Integer currendId=Integer.valueOf(request.getParameter("userid"));


        if(seniorkey==1){

            Ocplacenames ocplacenames2=ocplacenamesMapper.getOcplacenamesById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Ocplacenames"+File.separator+"Ocplacenames"+ocplacenames2.getId()+".txt";;// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";


            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }



            return jsonString;

        }


        if(seniorkey==2){

            Avillagerhyme avillagerhyme2=avillagerhymeMapper.getAvillagerhymeById(id,currendId);


            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+avillagerhyme2.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return  jsonString;



        }


        if(seniorkey==3){


            Chafood chafood2=chafoodMapper.getChafoodById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Chafood"+File.separator+"Chafood"+chafood2.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

           return  jsonString;


        }

        if(seniorkey==4){

            Focustoms focustoms3=focustomsMapper.getFocustomsById(id,currendId);


            String pathname =ConfigBean.sourceRealPath+"txt"+ File.separator+"Focustoms"+File.separator+"Focustoms"+focustoms3.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return jsonString;

        }



        if(seniorkey==5){

            Famousscenery famousscenery3 = famoussceneryMapper.getFamoussceneryById(id,currendId);

            String pathname =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Famousscenery"+File.separator+"Famousscenery"+famousscenery3.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }


            return jsonString;

        }


        if(seniorkey==6){

            Nolegaculture nolegaculture2=nolegacultureMapper.getNolegacultureById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator+"Nolegaculture"+nolegaculture2.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }


            return jsonString;

        }




        if(seniorkey==7){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {

                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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

                System.out.println(jsonString);
                return jsonString;

            }


            if(lowerkey==2) {

                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }


            if(lowerkey==3) {

                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }



            if(lowerkey==4) {
                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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

                System.out.println(jsonString);
                return jsonString;

            }


            if(lowerkey==5){
                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }




        }


        if(seniorkey==8){

            Streetplana streetplana1=streetplanaMapper.getStreetplanaById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Streetplana"+File.separator+"Streetplana"+streetplana1.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return jsonString;

        }




        if(seniorkey==9){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {

                Jinliterbriga jinliterbriga=jinliterbrigaMapper.getJinliterbrigaById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Jinliterbriga"+File.separator+"Jinliterbriga"+jinliterbriga.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }


            if(lowerkey==2) {

                Jinliterbriga jinliterbriga=jinliterbrigaMapper.getJinliterbrigaById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Jinliterbriga"+File.separator+"Jinliterbriga"+jinliterbriga.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }


            if(lowerkey==3) {

                Jinliterbriga jinliterbriga=jinliterbrigaMapper.getJinliterbrigaById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Jinliterbriga"+File.separator+"Jinliterbriga"+jinliterbriga.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;


            }



            if(lowerkey==4) {
                Jinliterbriga jinliterbriga=jinliterbrigaMapper.getJinliterbrigaById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Jinliterbriga"+File.separator+"Jinliterbriga"+jinliterbriga.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }




            if(lowerkey==5) {
                Jinliterbriga jinliterbriga=jinliterbrigaMapper.getJinliterbrigaById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Jinliterbriga"+File.separator+"Jinliterbriga"+jinliterbriga.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }





            if(lowerkey==6) {
                Jinliterbriga jinliterbriga=jinliterbrigaMapper.getJinliterbrigaById(id,currendId);


                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Jinliterbriga"+File.separator+"Jinliterbriga"+jinliterbriga.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
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


                return jsonString;

            }



        }



        if(seniorkey==11){

                Cultuinsurunit cultuinsurunit2=cultuinsurunitMapper.getCultuinsurunitById(id,currendId);

                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Cultuinsurunit"+File.separator+"Cultuinsurunit"+cultuinsurunit2.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                String line=null;

                String jsonString = "";

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }

                return  jsonString;



        }


        //休闲之旅
        if(seniorkey==12){

            Leisuretrip leisuretrip=leisuretripMapper.getLeisuretripById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+leisuretrip.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return  jsonString;


        }



        return "";


    }



    @ApiOperation(value = "获取总的信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "seniorkey", value = "高级关键词", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lowerkey", value = "低级关键词", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList6")
    private ResultVO getList6(HttpServletRequest request) throws IOException {

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));


        Integer currendId=0;

        if(request.getParameter("userid")!=null&&!request.getParameter("userid").equals("")){

            currendId=Integer.valueOf(request.getParameter("userid"));
        }

        Integer seniorkey=Integer.valueOf(request.getParameter("seniorkey"));

        String name=request.getParameter("name");


        if(seniorkey==1){



            if(name!=null&&!name.equals("")){

                PageHelper.startPage(page,size);  //行政区划

                List<Admindivsion> admindivsionList=admindivsionMapper.getAdmindivsionByNameAndUserId("%"+name+"%",currendId);

                PageInfo<Admindivsion> admindivsionPageInfo=new PageInfo<>(admindivsionList);

                ListVO listVO=new ListVO();
                listVO.setList(admindivsionList);
                listVO.setCount(admindivsionPageInfo.getTotal());

                return ResultVOUtil.success(listVO);

            }else{

                PageHelper.startPage(page,size);  //行政区划

                List<Admindivsion> admindivsionList=admindivsionMapper.getAdmindivsionByNameAndUserId(null,currendId);

                PageInfo<Admindivsion> admindivsionPageInfo=new PageInfo<>(admindivsionList);

                ListVO listVO=new ListVO();
                listVO.setList(admindivsionList);
                listVO.setCount(admindivsionPageInfo.getTotal());

                return ResultVOUtil.success(listVO);

            }

        }


        if(seniorkey==2){

            if(name!=null) {

                PageHelper.startPage(page, size);//行政村名

                List<Avillagerhyme> avillagerhymeList = avillagerhymeMapper.selectAvillagerhymeByName("%"+name+"%", currendId);

                PageInfo<Avillagerhyme> avillagerhymeInfo = new PageInfo<>(avillagerhymeList);

                List<AvillagerhymeTwoVo> avillagerhymeVoList = new ArrayList<>();


                for (int i = 0; i < avillagerhymeList.size(); i++) {

                    Avillagerhyme avillagerhyme2 = avillagerhymeList.get(i);

                    AvillagerhymeTwoVo avillagerhymeVo = new AvillagerhymeTwoVo();

                    String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Avillagerhyme" + File.separator + "Avillagerhyme" + avillagerhyme2.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                    //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                    //不关闭文件会导致资源的泄露，读写文件都同理
                    //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                    String line = null;

                    String jsonString = "";


                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                    //网友推荐更加简洁的写法
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        jsonString += line;


                    }

                    avillagerhymeVo.setId(avillagerhyme2.getId());
                    avillagerhymeVo.setName(avillagerhyme2.getName());
                    avillagerhymeVo.setPictureurl(avillagerhyme2.getPictureurl());
                    avillagerhymeVo.setStatus(avillagerhyme2.getStatus());
                    avillagerhymeVo.setTxt(jsonString);

                    avillagerhymeVoList.add(avillagerhymeVo);


                }

                ListVO listVO = new ListVO();
                listVO.setList(avillagerhymeVoList);
                listVO.setCount(avillagerhymeInfo.getTotal());

                return ResultVOUtil.success(listVO);
            }else{

                PageHelper.startPage(page, size);//行政村名

                List<Avillagerhyme> avillagerhymeList = avillagerhymeMapper.selectAvillagerhymeByName(null, currendId);

                PageInfo<Avillagerhyme> avillagerhymeInfo = new PageInfo<>(avillagerhymeList);

                List<AvillagerhymeTwoVo> avillagerhymeVoList = new ArrayList<>();


                for (int i = 0; i < avillagerhymeList.size(); i++) {

                    Avillagerhyme avillagerhyme2 = avillagerhymeList.get(i);

                    AvillagerhymeTwoVo avillagerhymeVo = new AvillagerhymeTwoVo();

                    String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Avillagerhyme" + File.separator + "Avillagerhyme" + avillagerhyme2.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                    //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                    //不关闭文件会导致资源的泄露，读写文件都同理
                    //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                    String line = null;

                    String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                    //网友推荐更加简洁的写法
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        jsonString += line;


                    }

                    avillagerhymeVo.setId(avillagerhyme2.getId());
                    avillagerhymeVo.setName(avillagerhyme2.getName());
                    avillagerhymeVo.setPictureurl(avillagerhyme2.getPictureurl());
                    avillagerhymeVo.setStatus(avillagerhyme2.getStatus());
                    avillagerhymeVo.setTxt(jsonString);

                    avillagerhymeVoList.add(avillagerhymeVo);


                }

                ListVO listVO = new ListVO();
                listVO.setList(avillagerhymeVoList);
                listVO.setCount(avillagerhymeInfo.getTotal());

                return ResultVOUtil.success(listVO);

            }


        }


        if(seniorkey==3){

            if(name!=null) {

                PageHelper.startPage(page, size); //街巷地名

                List<Streetplana> streetplanaList = streetplanaMapper.selectStreetplana("%"+name+"%", currendId);

                PageInfo<Streetplana> avillagerhymeInfo = new PageInfo<>(streetplanaList);

                List<StreetplanaTwoVo> streetplanaVoList = new ArrayList<>();

                for (int i = 0; i < streetplanaList.size(); i++) {
                    Streetplana streetplana = streetplanaList.get(i);

                    StreetplanaTwoVo streetplanaVo = new StreetplanaTwoVo();

                    String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Streetplana" + File.separator + "Streetplana" + streetplana.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                    //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                    //不关闭文件会导致资源的泄露，读写文件都同理
                    //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                    String line = null;

                    String jsonString = "";


                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                    //网友推荐更加简洁的写法
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        jsonString += line;


                    }

                    streetplanaVo.setId(streetplana.getId());
                    streetplanaVo.setName(streetplana.getName());
                    streetplanaVo.setPictureurl(streetplana.getPictureurl());
                    streetplanaVo.setTxt(jsonString);
                    streetplanaVo.setAbbreviation(streetplana.getAbbreviation());

                    streetplanaVoList.add(streetplanaVo);

                }

                ListVO listVO = new ListVO();
                listVO.setList(streetplanaVoList);
                listVO.setCount(avillagerhymeInfo.getTotal());

                return ResultVOUtil.success(listVO);

            } else {


                PageHelper.startPage(page, size); //街巷地名

                List<Streetplana> streetplanaList = streetplanaMapper.selectStreetplana(null, currendId);

                PageInfo<Streetplana> avillagerhymeInfo = new PageInfo<>(streetplanaList);

                List<StreetplanaTwoVo> streetplanaVoList = new ArrayList<>();

                for (int i = 0; i < streetplanaList.size(); i++) {
                    Streetplana streetplana = streetplanaList.get(i);

                    StreetplanaTwoVo streetplanaVo = new StreetplanaTwoVo();

                    String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Streetplana" + File.separator + "Streetplana" + streetplana.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                    //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                    //不关闭文件会导致资源的泄露，读写文件都同理
                    //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                    String line = null;

                    String jsonString = "";


                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                    //网友推荐更加简洁的写法
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        jsonString += line;


                    }

                    streetplanaVo.setId(streetplana.getId());
                    streetplanaVo.setName(streetplana.getName());
                    streetplanaVo.setPictureurl(streetplana.getPictureurl());
                    streetplanaVo.setTxt(jsonString);
                    streetplanaVo.setAbbreviation(streetplana.getAbbreviation());

                    streetplanaVoList.add(streetplanaVo);

                }

                ListVO listVO = new ListVO();
                listVO.setList(streetplanaVoList);
                listVO.setCount(avillagerhymeInfo.getTotal());

                return ResultVOUtil.success(listVO);


            }

        }

        if(seniorkey==4){
            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

                if(lowerkey==1){

                    if(name!=null) {

                        PageHelper.startPage(page, size); //地名文化

                        List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu("%"+name+"%", "tabkey1", currendId);

                        PageInfo<Placenacultu> placenacultuInfo = new PageInfo<>(placenacultuList);

                        List<PlacenacultuTwoVo> placenacultuVoList = new ArrayList<>();

                        for (int i = 0; i < placenacultuList.size(); i++) {

                            Placenacultu placenacultu = placenacultuList.get(i);

                            PlacenacultuTwoVo placenacultuVo = new PlacenacultuTwoVo();

                            String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Placenacultu" + File.separator + "Placenacultu" + placenacultu.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                            //不关闭文件会导致资源的泄露，读写文件都同理
                            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                            String line = null;

                            String jsonString = "";


                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                            //网友推荐更加简洁的写法
                            while ((line = br.readLine()) != null) {
                                // 一次读入一行数据
                                jsonString += line;


                            }

                            placenacultuVo.setId(placenacultu.getId());
                            placenacultuVo.setName(placenacultu.getName());
                            placenacultuVo.setPictureurl(placenacultu.getPictureurl());
                            placenacultuVo.setTxt(jsonString);
                            placenacultuVo.setType(placenacultu.getType());
                            placenacultuVo.setAbbreviation(placenacultu.getAbbreviation());
                            placenacultuVo.setStatus(placenacultu.getStatus());

                            placenacultuVoList.add(placenacultuVo);


                        }

                        ListVO listVO = new ListVO();
                        listVO.setList(placenacultuVoList);
                        listVO.setCount(placenacultuInfo.getTotal());

                        return ResultVOUtil.success(listVO);

                    }else{

                        PageHelper.startPage(page, size); //地名文化

                        List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null, "tabkey1", currendId);

                        PageInfo<Placenacultu> placenacultuInfo = new PageInfo<>(placenacultuList);

                        List<PlacenacultuTwoVo> placenacultuVoList = new ArrayList<>();

                        for (int i = 0; i < placenacultuList.size(); i++) {

                            Placenacultu placenacultu = placenacultuList.get(i);

                            PlacenacultuTwoVo placenacultuVo = new PlacenacultuTwoVo();

                            String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Placenacultu" + File.separator + "Placenacultu" + placenacultu.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                            //不关闭文件会导致资源的泄露，读写文件都同理
                            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                            String line = null;

                            String jsonString = "";


                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                            //网友推荐更加简洁的写法
                            while ((line = br.readLine()) != null) {
                                // 一次读入一行数据
                                jsonString += line;


                            }

                            placenacultuVo.setId(placenacultu.getId());
                            placenacultuVo.setName(placenacultu.getName());
                            placenacultuVo.setPictureurl(placenacultu.getPictureurl());
                            placenacultuVo.setTxt(jsonString);
                            placenacultuVo.setType(placenacultu.getType());
                            placenacultuVo.setAbbreviation(placenacultu.getAbbreviation());
                            placenacultuVo.setStatus(placenacultu.getStatus());

                            placenacultuVoList.add(placenacultuVo);


                        }

                        ListVO listVO = new ListVO();
                        listVO.setList(placenacultuVoList);
                        listVO.setCount(placenacultuInfo.getTotal());

                        return ResultVOUtil.success(listVO);
                    }
                }

                if(lowerkey==2){

                    if(name!=null) {

                        PageHelper.startPage(page, size); //地名文化

                        List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu("%"+name+"%", "tabkey2", currendId);

                        PageInfo<Placenacultu> placenacultuInfo = new PageInfo<>(placenacultuList);

                        List<PlacenacultuTwoVo> placenacultuVoList = new ArrayList<>();

                        for (int i = 0; i < placenacultuList.size(); i++) {

                            Placenacultu placenacultu = placenacultuList.get(i);

                            PlacenacultuTwoVo placenacultuVo = new PlacenacultuTwoVo();

                            String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Placenacultu" + File.separator + "Placenacultu" + placenacultu.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                            //不关闭文件会导致资源的泄露，读写文件都同理
                            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                            String line = null;

                            String jsonString = "";


                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                            //网友推荐更加简洁的写法
                            while ((line = br.readLine()) != null) {
                                // 一次读入一行数据
                                jsonString += line;


                            }

                            placenacultuVo.setId(placenacultu.getId());
                            placenacultuVo.setName(placenacultu.getName());
                            placenacultuVo.setPictureurl(placenacultu.getPictureurl());
                            placenacultuVo.setTxt(jsonString);
                            placenacultuVo.setType(placenacultu.getType());
                            placenacultuVo.setAbbreviation(placenacultu.getAbbreviation());
                            placenacultuVo.setStatus(placenacultu.getStatus());

                            placenacultuVoList.add(placenacultuVo);


                        }

                        ListVO listVO = new ListVO();
                        listVO.setList(placenacultuVoList);
                        listVO.setCount(placenacultuInfo.getTotal());

                        return ResultVOUtil.success(listVO);

                    }else{

                        PageHelper.startPage(page, size); //地名文化

                        List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null, "tabkey2", currendId);

                        PageInfo<Placenacultu> placenacultuInfo = new PageInfo<>(placenacultuList);

                        List<PlacenacultuTwoVo> placenacultuVoList = new ArrayList<>();

                        for (int i = 0; i < placenacultuList.size(); i++) {

                            Placenacultu placenacultu = placenacultuList.get(i);

                            PlacenacultuTwoVo placenacultuVo = new PlacenacultuTwoVo();

                            String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Placenacultu" + File.separator + "Placenacultu" + placenacultu.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                            //不关闭文件会导致资源的泄露，读写文件都同理
                            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                            String line = null;

                            String jsonString = "";


                            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                            //网友推荐更加简洁的写法
                            while ((line = br.readLine()) != null) {
                                // 一次读入一行数据
                                jsonString += line;


                            }

                            placenacultuVo.setId(placenacultu.getId());
                            placenacultuVo.setName(placenacultu.getName());
                            placenacultuVo.setPictureurl(placenacultu.getPictureurl());
                            placenacultuVo.setTxt(jsonString);
                            placenacultuVo.setType(placenacultu.getType());
                            placenacultuVo.setAbbreviation(placenacultu.getAbbreviation());
                            placenacultuVo.setStatus(placenacultu.getStatus());

                            placenacultuVoList.add(placenacultuVo);


                        }

                        ListVO listVO = new ListVO();
                        listVO.setList(placenacultuVoList);
                        listVO.setCount(placenacultuInfo.getTotal());

                        return ResultVOUtil.success(listVO);
                    }
            }



        }


        if(seniorkey==5){

            if(name!=null) {
                PageHelper.startPage(page, size); //廊桥地名

                List<Ocplacenames> ocplacenamesList = ocplacenamesMapper.selectOcplacenames("%"+name+"%", currendId);

                PageInfo<Ocplacenames> ocplacenamesInfo = new PageInfo<>(ocplacenamesList);

                List<OcplacenamesTwoVo> ocplacenamesVoList = new ArrayList<>();

                for (int i = 0; i < ocplacenamesList.size(); i++) {

                    Ocplacenames ocplacenames = ocplacenamesList.get(i);

                    OcplacenamesTwoVo ocplacenamesVo = new OcplacenamesTwoVo();

                    String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Ocplacenames" + File.separator + "Ocplacenames" + ocplacenames.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                    //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                    //不关闭文件会导致资源的泄露，读写文件都同理
                    //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                    String line = null;

                    String jsonString = "";


                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                    //网友推荐更加简洁的写法
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        jsonString += line;


                    }

                    ocplacenamesVo.setId(ocplacenames.getId());
                    ocplacenamesVo.setName(ocplacenames.getName());
                    ocplacenamesVo.setPictureurl(ocplacenames.getPictureurl());
                    ocplacenamesVo.setStatus(ocplacenames.getStatus());
                    ocplacenamesVo.setTxt(jsonString);
                    ocplacenamesVo.setAbbreviation(ocplacenames.getAbbreviation());

                    ocplacenamesVoList.add(ocplacenamesVo);

                }


                ListVO listVO = new ListVO();
                listVO.setList(ocplacenamesVoList);
                listVO.setCount(ocplacenamesInfo.getTotal());

                return ResultVOUtil.success(listVO);

            }else{
                PageHelper.startPage(page, size); //廊桥地名

                List<Ocplacenames> ocplacenamesList = ocplacenamesMapper.selectOcplacenames(null, currendId);

                PageInfo<Ocplacenames> ocplacenamesInfo = new PageInfo<>(ocplacenamesList);

                List<OcplacenamesTwoVo> ocplacenamesVoList = new ArrayList<>();

                for (int i = 0; i < ocplacenamesList.size(); i++) {

                    Ocplacenames ocplacenames = ocplacenamesList.get(i);

                    OcplacenamesTwoVo ocplacenamesVo = new OcplacenamesTwoVo();

                    String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Ocplacenames" + File.separator + "Ocplacenames" + ocplacenames.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                    //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                    //不关闭文件会导致资源的泄露，读写文件都同理
                    //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                    String line = null;

                    String jsonString = "";


                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                    //网友推荐更加简洁的写法
                    while ((line = br.readLine()) != null) {
                        // 一次读入一行数据
                        jsonString += line;


                    }

                    ocplacenamesVo.setId(ocplacenames.getId());
                    ocplacenamesVo.setName(ocplacenames.getName());
                    ocplacenamesVo.setPictureurl(ocplacenames.getPictureurl());
                    ocplacenamesVo.setStatus(ocplacenames.getStatus());
                    ocplacenamesVo.setTxt(jsonString);
                    ocplacenamesVo.setAbbreviation(ocplacenames.getAbbreviation());

                    ocplacenamesVoList.add(ocplacenamesVo);

                }


                ListVO listVO = new ListVO();
                listVO.setList(ocplacenamesVoList);
                listVO.setCount(ocplacenamesInfo.getTotal());

                return ResultVOUtil.success(listVO);

            }

        }


        if(seniorkey==6){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {

                if(name!=null) {
                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2("%"+name+"%", "tabkey1", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);

                }else{

                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2(null, "tabkey1", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);
                }

            }

            if(lowerkey==2) {

                if(name!=null) {

                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2("%"+name+"%", "tabkey2", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);
                }else{

                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2(null, "tabkey2", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);
                }

            }

            if(lowerkey==3) {

                if(name!=null) {

                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2("%"+name+"%", "tabkey3", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);

                }else{
                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2(null, "tabkey3", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);



                }
            }

            if(lowerkey==4) {

                if(name!=null) {
                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2("%"+name+"%", "tabkey4", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);
                }else{

                    PageHelper.startPage(page, size); //文旅文化

                    List<Focustoms> focustomsList = focustomsMapper.selectFocustoms2(null, "tabkey4", currendId);

                    PageInfo<Focustoms> focustomsInfo = new PageInfo<>(focustomsList);

                    List<FocustomsTwoVo> focustomsVoList = new ArrayList<>();

                    for (int i = 0; i < focustomsList.size(); i++) {

                        Focustoms focustoms = focustomsList.get(i);

                        FocustomsTwoVo focustomsVo = new FocustomsTwoVo();

                        String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Focustoms" + File.separator + "Focustoms" + focustoms.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

                        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                        //不关闭文件会导致资源的泄露，读写文件都同理
                        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                        String line = null;

                        String jsonString = "";

                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                        //网友推荐更加简洁的写法
                        while ((line = br.readLine()) != null) {
                            // 一次读入一行数据
                            jsonString += line;


                        }

                        focustomsVo.setId(focustoms.getId());
                        focustomsVo.setName(focustoms.getName());
                        focustomsVo.setPictureurl(focustoms.getPictureurl());
                        focustomsVo.setStatus(focustoms.getStatus());
                        focustomsVo.setTxt(jsonString);
                        focustomsVo.setAbbreviation(focustoms.getAbbreviation());

                        focustomsVoList.add(focustomsVo);

                    }
                    ListVO listVO = new ListVO();
                    listVO.setList(focustomsVoList);
                    listVO.setCount(focustomsInfo.getTotal());


                    return ResultVOUtil.success(listVO);
                }

            }

        }

        if(seniorkey==7){

             if(name!=null) {

                 PageHelper.startPage(page, size); //红色地名

                 List<Nolegaculture> nolegacultureList = nolegacultureMapper.selectNolegaculture("%"+name+"%", currendId);

                 PageInfo<Nolegaculture> nolegacultureInfo = new PageInfo<>(nolegacultureList);

                 List<NolegacultureTwoVo> nolegacultureVoList = new ArrayList<>();

                 for (int i = 0; i < nolegacultureList.size(); i++) {

                     Nolegaculture nolegaculture2 = nolegacultureList.get(i);

                     NolegacultureTwoVo nolegacultureVo = new NolegacultureTwoVo();

                     String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Nolegaculture" + File.separator + "Nolegaculture" + nolegaculture2.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                     //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                     //不关闭文件会导致资源的泄露，读写文件都同理
                     //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                     String line = null;

                     String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                     BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                     //网友推荐更加简洁的写法
                     while ((line = br.readLine()) != null) {
                         // 一次读入一行数据
                         jsonString += line;


                     }

                     nolegacultureVo.setId(nolegaculture2.getId());
                     nolegacultureVo.setName(nolegaculture2.getName());
                     nolegacultureVo.setPictureurl(nolegaculture2.getPictureurl());
                     nolegacultureVo.setStatus(nolegaculture2.getStatus());
                     nolegacultureVo.setTxt(jsonString);
                     nolegacultureVo.setAbbreviation(nolegaculture2.getAbbreviation());

                     nolegacultureVoList.add(nolegacultureVo);

                 }


                 ListVO listVO = new ListVO();
                 listVO.setList(nolegacultureVoList);
                 listVO.setCount(nolegacultureInfo.getTotal());

                 return ResultVOUtil.success(listVO);

             }else{

                 PageHelper.startPage(page, size); //红色地名

                 List<Nolegaculture> nolegacultureList = nolegacultureMapper.selectNolegaculture(null, currendId);

                 PageInfo<Nolegaculture> nolegacultureInfo = new PageInfo<>(nolegacultureList);

                 List<NolegacultureTwoVo> nolegacultureVoList = new ArrayList<>();

                 for (int i = 0; i < nolegacultureList.size(); i++) {

                     Nolegaculture nolegaculture2 = nolegacultureList.get(i);

                     NolegacultureTwoVo nolegacultureVo = new NolegacultureTwoVo();

                     String pathname = ConfigBean.sourceRealPath + "txt" + File.separator + "Nolegaculture" + File.separator + "Nolegaculture" + nolegaculture2.getId() + ".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                     //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                     //不关闭文件会导致资源的泄露，读写文件都同理
                     //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                     String line = null;

                     String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

                     BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                     //网友推荐更加简洁的写法
                     while ((line = br.readLine()) != null) {
                         // 一次读入一行数据
                         jsonString += line;


                     }

                     nolegacultureVo.setId(nolegaculture2.getId());
                     nolegacultureVo.setName(nolegaculture2.getName());
                     nolegacultureVo.setPictureurl(nolegaculture2.getPictureurl());
                     nolegacultureVo.setStatus(nolegaculture2.getStatus());
                     nolegacultureVo.setTxt(jsonString);
                     nolegacultureVo.setAbbreviation(nolegaculture2.getAbbreviation());

                     nolegacultureVoList.add(nolegacultureVo);

                 }


                 ListVO listVO = new ListVO();
                 listVO.setList(nolegacultureVoList);
                 listVO.setCount(nolegacultureInfo.getTotal());

                 return ResultVOUtil.success(listVO);
             }


        }


        if(seniorkey==8){

            if(name!=null) {
                PageHelper.startPage(page, size); //影像地名

                List<Imageplacenames> imageplacenamesList = imageplacenamesMapper.selectImageplacenames("%"+name+"%", currendId);


                PageInfo<Imageplacenames> focustomsInfo = new PageInfo<>(imageplacenamesList);


                ListVO listVO = new ListVO();
                listVO.setList(imageplacenamesList);
                listVO.setCount(focustomsInfo.getTotal());


                return ResultVOUtil.success(listVO);
            }else{

                PageHelper.startPage(page, size); //影像地名

                List<Imageplacenames> imageplacenamesList = imageplacenamesMapper.selectImageplacenames(null, currendId);


                PageInfo<Imageplacenames> focustomsInfo = new PageInfo<>(imageplacenamesList);


                ListVO listVO = new ListVO();
                listVO.setList(imageplacenamesList);
                listVO.setCount(focustomsInfo.getTotal());


                return ResultVOUtil.success(listVO);

            }

        }



        return ResultVOUtil.success();


    }





    @ApiOperation(value = "获取庆元总的信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "seniorkey", value = "高级关键词", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lowerkey", value = "低级关键词", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "userid", value = "用户id", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getQingYuamInfoById")
    private String getQingYuamInfoById(HttpServletRequest request) throws IOException {


        Integer seniorkey=Integer.valueOf(request.getParameter("seniorkey"));

        Integer id=Integer.valueOf(request.getParameter("id"));

        Integer currendId=Integer.valueOf(request.getParameter("userid"));


        if(seniorkey==1){

            //行政区划

            Admindivsion admindivsion=admindivsionMapper.getAdmindivsionById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Admindivsion"+File.separator+"Admindivsion"+admindivsion.getId()+".txt";;// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件

            String line=null;

            String jsonString = "";

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }


            return jsonString;

        }


        if(seniorkey==2){

            //行政村名


            Avillagerhyme avillagerhyme2=avillagerhymeMapper.getAvillagerhymeById(id,currendId);


            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Avillagerhyme"+File.separator+"Avillagerhyme"+avillagerhyme2.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return  jsonString;



        }


        if(seniorkey==3){

             //街巷地名


            Streetplana streetplana=streetplanaMapper.getStreetplanaById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Streetplana"+File.separator+"Streetplana"+streetplana.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";


            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return  jsonString;


        }

        if(seniorkey==4){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1){

              //地名文化

                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);

                String pathname =ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }

                return jsonString;

            }

            if(lowerkey==2){


                Placenacultu placenacultu=placenacultuMapper.getPlacenacultuById(id,currendId);

                String pathname =ConfigBean.sourceRealPath+"txt"+ File.separator+"Placenacultu"+File.separator+"Placenacultu"+placenacultu.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }

                return jsonString;

            }




        }



        if(seniorkey==5){


          //廊桥地名

            Ocplacenames ocplacenames = ocplacenamesMapper.getOcplacenamesById(id,currendId);

            String pathname =  ConfigBean.sourceRealPath+"txt"+ File.separator+"Ocplacenames"+File.separator+"Ocplacenames"+ocplacenames.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";


            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }


            return jsonString;

        }


        if(seniorkey==6){

            //文旅文化
            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {


                Focustoms focustoms=focustomsMapper.getFocustomsById(id,currendId);

                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Focustoms"+File.separator+"Focustoms"+focustoms.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                return jsonString;

            }

            if(lowerkey==2) {

                Focustoms focustoms=focustomsMapper.getFocustomsById(id,currendId);

                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Focustoms"+File.separator+"Focustoms"+focustoms.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                return jsonString;


            }

            if(lowerkey==3) {

                Focustoms focustoms=focustomsMapper.getFocustomsById(id,currendId);

                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Focustoms"+File.separator+"Focustoms"+focustoms.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                return jsonString;

            }

            if(lowerkey==4) {

                Focustoms focustoms=focustomsMapper.getFocustomsById(id,currendId);

                String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Focustoms"+File.separator+"Focustoms"+focustoms.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
                //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
                //不关闭文件会导致资源的泄露，读写文件都同理
                //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
                String line=null;

                String jsonString = "";


                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

                //网友推荐更加简洁的写法
                while ((line = br.readLine()) != null) {
                    // 一次读入一行数据
                    jsonString += line;


                }


                return jsonString;


            }

        }




        if(seniorkey==7){

            //红色地名

            Nolegaculture nolegaculture2=nolegacultureMapper.getNolegacultureById(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Nolegaculture"+File.separator+"Nolegaculture"+nolegaculture2.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";

//            FileReader reader = new FileReader(pathname);
//
//            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return jsonString;


        }


        if(seniorkey==8){


         //影像地名


            Imageplacenames imageplacenames=imageplacenamesMapper.getImageplacenamesById2(id,currendId);

            String pathname = ConfigBean.sourceRealPath+"txt"+ File.separator+"Imageplacenames"+File.separator+"Imageplacenames"+imageplacenames.getId()+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
            //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
            //不关闭文件会导致资源的泄露，读写文件都同理
            //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
            String line=null;

            String jsonString = "";


            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "utf-8"));

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                jsonString += line;


            }

            return jsonString;

        }





        return "";


    }



}
