package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Maintrecords;
import com.csdl.smartplacenew.pojo.Platenumber;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.two.PlateNumberNaUr;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.LonAndLatVo;
import com.csdl.smartplacenew.vo.PlateNumberVo;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(description = "门牌接口")
@RestController
@RequestMapping("/PlateNumber")
public class PlateNumberController {

    @Resource
    PlatenumberMapper platenumberMapper;

    @Resource
    FormtypeMapper formtypeMapper;

    @Resource
    PictureMapper pictureMapper;

    @Resource
    AudioMapper audioMapper;

    @Resource
    CountMapper countMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PlateNumberNaUrMapper plateNumberNaUrMapper;

    @Resource
    MaintrecordsMapper maintrecordsMapper;

    @Resource
    FeedbackMapper feedbackMapper;


    @ApiOperation(value = "添加门牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "lon", value = "经度", required = false, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = false, dataType = "double", paramType = "query"),

            @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "textofmater", value = "材质", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "尺寸", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "yearofesent", value = "设立年份", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainteunit", value = "维护单位", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "numofmaint", value = "维护次数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintperiod", value = "维护期限", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintcost", value = "维护费用", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintaccounts", value = "维护台账", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "englishword", value = "英语内容", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name = request.getParameter("name");

        String lonandlat=null;
           if(request.getParameter("lon")!=null&&!request.getParameter("lon").equals("")) {
               Double lon = Double.valueOf(request.getParameter("lon"));
               Double lat = Double.valueOf(request.getParameter("lat"));
               String londecimalFormat1 = new DecimalFormat("#,##0.000000").format(lon);
               String latdecimalFormat1 = new DecimalFormat("#,##0.000000").format(lat);
                lonandlat = londecimalFormat1 + "," + latdecimalFormat1;
           }else{
               lonandlat =null;
           }

        String type = request.getParameter("type");
        String textofmater=request.getParameter("textofmater");
        String size=request.getParameter("size");
        String yearofesent=request.getParameter("yearofesent");
        String mainteunit=request.getParameter("mainteunit");
        String numofmaint=request.getParameter("numofmaint");
        String maintperiod=request.getParameter("maintperiod");
        String maintcost=request.getParameter("maintcost");
        String maintaccounts=request.getParameter("maintaccounts");

        String sharelinks=request.getParameter("sharelinks");

        String englishword=request.getParameter("englishword");


        String imgVirtualPath=null;


        Integer platenumberId1=platenumberMapper.getMaxIdByAll(currentUserId);

        if(platenumberId1==null){

            platenumberId1=1;

        }

        if(platenumberId1>0){

            platenumberId1=platenumberId1+1;

        }


        platenumberMapper.addPlateNumber(name, lonandlat,type, currentUserId+"/"+platenumberId1,imgVirtualPath, 1,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,currentUserId,sharelinks,englishword);

        Platenumber platenumber=platenumberMapper.getPlateNumberByMaxId(currentUserId);


        formtypeMapper.addFormtype(type, 1,currentUserId);


        List<Platenumber>  platenumberList1 = platenumberMapper.getPlateNumByName(name,currentUserId);


         if(platenumberList1.size()==1){

             platenumberMapper.addPlateNumCount(name, platenumberList1.size(), 0, "platenumber",1,0,currentUserId);
         }

         if(platenumberList1.size()>1){

             countMapper.updatePlateCount2(name,"platenumber",currentUserId);

         }


        return ResultVOUtil.success(platenumber.getId());


    }



    @ApiOperation(value = "删除门牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "门牌id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id = Integer.valueOf(request.getParameter("id"));


        Platenumber pointposition = platenumberMapper.getNumberplateById(id,currentUserId);

        List<Platenumber> platenumberList=platenumberMapper.getPlateNumByName(pointposition.getName(),currentUserId);

        if(platenumberList.size()>1){
            countMapper.updatePlateCount3(pointposition.getName(),"platenumber",currentUserId);
        }

        if(platenumberList.size()==1){

            countMapper.updatePlateCount4(pointposition.getName(),"platenumber",currentUserId);
        }

        platenumberMapper.updateNumberplateStatus(id,currentUserId);

        return ResultVOUtil.success("成功删除:" + pointposition);


    }




    @ApiOperation(value = "修改门牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "点位id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "lon", value = "经度", required = false, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "lat", value = "纬度", required = false, dataType = "double", paramType = "query"),

            @ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "textofmater", value = "材质", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "尺寸", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "yearofesent", value = "设立年份", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "mainteunit", value = "维护单位", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "numofmaint", value = "维护次数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintperiod", value = "维护期限", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintcost", value = "维护费用", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintaccounts", value = "维护台账", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "englishword", value = "英语内容", required = false, dataType = "string", paramType = "query"),


    })
    @PostMapping("/update")
    private ResultVO update( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Platenumber pointposition=platenumberMapper.getNumberplateById(id,currentUserId);

        String name=null;

        Double lon=null;
        Double lat=null;

        String lonandlat=null;

        String type=null;

        String url=null;

        String imgVirtualPath=null;


        String textofmater=null;
        String size=null;
        String yearofesent=null;
        String mainteunit=null;
        String numofmaint=null;
        String maintperiod=null;
        String maintcost=null;
        String maintaccounts=null;

        String englishword=null;

        String sharelinks=request.getParameter("sharelinks");


        if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){

            name=request.getParameter("name");

        }else{
            name=pointposition.getName();
        }




        if(request.getParameter("lon")!=null&&!request.getParameter("lon").equals("")&&!request.getParameter("lon").equals("null")){

            lon=Double.valueOf(request.getParameter("lon"));

        }


        if(request.getParameter("lat")!=null&&!request.getParameter("lat").equals("")&&!request.getParameter("lat").equals("null")){

            lat=Double.valueOf(request.getParameter("lat"));

        }


        if(lon!=null&&lat!=null) {

            String londecimalFormat1 = new DecimalFormat("#,##0.000000").format(lon);
            String latdecimalFormat1 = new DecimalFormat("#,##0.000000").format(lat);

            lonandlat = londecimalFormat1 + "," + latdecimalFormat1;
        }



        if(request.getParameter("type")!=null&&!request.getParameter("type").equals("")){

            type=request.getParameter("type");

        }else{
            type=pointposition.getType();
        }



        if(request.getParameter("url")!=null&&!request.getParameter("url").equals("")){

            url=request.getParameter("url");

        }else{
            url=pointposition.getUrl();
        }



        if(request.getParameter("textofmater")!=null&&!request.getParameter("textofmater").equals("")){
            textofmater=request.getParameter("textofmater");

        }else{
            textofmater=pointposition.getTextofmater();
        }


        if(request.getParameter("size")!=null&&!request.getParameter("size").equals("")){
            size=request.getParameter("size");

        }else{
            size=pointposition.getSize();
        }


        if(request.getParameter("yearofesent")!=null&&!request.getParameter("yearofesent").equals("")){
            yearofesent=request.getParameter("yearofesent");

        }else{
            yearofesent=pointposition.getYearofesent();
        }


        if(request.getParameter("mainteunit")!=null&&!request.getParameter("mainteunit").equals("")){
            mainteunit=request.getParameter("mainteunit");

        }else{
            mainteunit=pointposition.getMainteunit();
        }



        if(request.getParameter("numofmaint")!=null&&!request.getParameter("numofmaint").equals("")){
            numofmaint=request.getParameter("numofmaint");

        }else{
            numofmaint=pointposition.getNumofmaint();
        }



        if(request.getParameter("maintperiod")!=null&&!request.getParameter("maintperiod").equals("")){
            maintperiod=request.getParameter("maintperiod");

        }else{
            maintperiod=pointposition.getMaintperiod();
        }


        if(request.getParameter("maintcost")!=null&&!request.getParameter("maintcost").equals("")){
            maintcost=request.getParameter("maintcost");

        }else{
            maintcost=pointposition.getMaintcost();
        }



        if(request.getParameter("maintaccounts")!=null&&!request.getParameter("maintaccounts").equals("")){
            maintaccounts=request.getParameter("maintaccounts");

        }else{
            maintaccounts=pointposition.getMaintaccounts();
        }



        if(request.getParameter("englishword")!=null&&!request.getParameter("englishword").equals("")){
            englishword=request.getParameter("englishword");

        }else{
            englishword=pointposition.getEnglishword();
        }

        imgVirtualPath=pointposition.getVoiceurl();


        platenumberMapper.updateNumberplateById(name,lonandlat,type,pointposition.getUrl(),imgVirtualPath,
                textofmater, size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,sharelinks,englishword,
                id,currentUserId);


         countMapper.updateCountName(name,pointposition.getName(),"platenumber",currentUserId);

         feedbackMapper.updateFeddBackFeedbackpath(name,pointposition.getName(),"门牌管理",currentUserId);


        Platenumber numberplate=platenumberMapper.getNumberplateById(id,currentUserId);

        return  ResultVOUtil.success(numberplate);


    }





    @ApiOperation(value = "获取点位", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "key", value = "关键字", required = false, dataType = "integer", paramType = "query"),
    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        if(user==null){
            return ResultVOUtil.byEnum2(CodeMessage.UserNotLogin,new ListVO());
        }
        Integer currentUserId=user.getId();


        String key=request.getParameter("key");

        if(key!=null&&!key.equals("")){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            Integer count=platenumberMapper.getNumberplateByKeyCount(currentUserId,"%"+key+"%");

            PageHelper.startPage(page,size);

            List<Platenumber> pointpositionList=platenumberMapper.getNumberplateByKey(currentUserId,"%"+key+"%");

            List<PlateNumberVo>  pointpositionVoList=new ArrayList<>();

            for(int i=0;i<pointpositionList.size();i++){

                Platenumber pointposition=pointpositionList.get(i);

                PlateNumberVo pointpositionVo=new PlateNumberVo();

                pointpositionVo.setId(pointposition.getId());
                pointpositionVo.setName(pointposition.getName());

//                pointpositionVo.setLonandlat(pointposition.getLonandlat());

                if(pointposition.getLonandlat()!=null&&!pointposition.getLonandlat().equals("")){

                    String[] strings=pointposition.getLonandlat().split(",");

                    pointpositionVo.setLon(strings[0]);
                    pointpositionVo.setLat(strings[1]);

                }

                pointpositionVo.setType(pointposition.getType());
                pointpositionVo.setUrl(pointposition.getUrl());
                pointpositionVo.setStatus(pointposition.getStatus());

                pointpositionVo.setMandarinWord(pointposition.getMandarinword());
                pointpositionVo.setEnglishWord(pointposition.getEnglishword());

                List<String> voice=audioMapper.getAudioByPlatenumberid(pointposition.getId(),currentUserId);

                if(voice!=null&&!voice.equals("")&&!voice.equals("null")){

                  for(int k=0;k<voice.size();k++){

                      if(voice.get(k).indexOf("mandarin")!=-1){
                          pointpositionVo.setMandarinVoice(voice.get(k));
                      }

                      if(voice.get(k).indexOf("english")!=-1){
                          pointpositionVo.setEnglishVoice(voice.get(k));
                      }

                      if(voice.get(k).indexOf("dialect")!=-1){
                          pointpositionVo.setDialectVoice(voice.get(k));
                      }

                  }



                }


                List<String> picture=pictureMapper.getPictureByPlatenumberid(pointposition.getId(),currentUserId);
                if(picture!=null&!picture.isEmpty()){

                    pointpositionVo.setPictureUrl(picture);
                }


                pointpositionVo.setTextOfMater(pointposition.getTextofmater());
                pointpositionVo.setSize(pointposition.getSize());
                pointpositionVo.setYearOfEsent(pointposition.getYearofesent());
                pointpositionVo.setMainteUnit(pointposition.getMainteunit());
                pointpositionVo.setNumofMaint(pointposition.getNumofmaint());
                pointpositionVo.setMaintPeriod(pointposition.getMaintperiod());
                pointpositionVo.setMaintCost(pointposition.getMaintcost());
                pointpositionVo.setMaintAccounts(pointposition.getMaintaccounts());

                pointpositionVo.setSharelinks(pointposition.getSharelinks());


                pointpositionVoList.add(pointpositionVo);


            }

            ListVO listVO=new ListVO();

            listVO.setList(pointpositionVoList);
            listVO.setCount(count);

            return  ResultVOUtil.success(listVO);


        }

        else {

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            Integer count=platenumberMapper.getNumberplateByKeyCount(currentUserId,null);

            PageHelper.startPage(page,size);

            List<Platenumber> pointpositionList=platenumberMapper.getNumberplateByKey(currentUserId,null);

            List<PlateNumberVo>  pointpositionVoList=new ArrayList<>();

            for(int i=0;i<pointpositionList.size();i++){

                Platenumber pointposition=pointpositionList.get(i);

                PlateNumberVo pointpositionVo=new PlateNumberVo();

                pointpositionVo.setId(pointposition.getId());
                pointpositionVo.setName(pointposition.getName());
                if(pointposition.getLonandlat()!=null&&!pointposition.getLonandlat().equals("")){

                    String[] strings=pointposition.getLonandlat().split(",");
                    pointpositionVo.setLon(strings[0]);
                    pointpositionVo.setLat(strings[1]);

                }
                pointpositionVo.setType(pointposition.getType());
                pointpositionVo.setUrl(pointposition.getUrl());
                pointpositionVo.setStatus(pointposition.getStatus());
                pointpositionVo.setMandarinWord(pointposition.getMandarinword());
                pointpositionVo.setEnglishWord(pointposition.getEnglishword());
                List<String> voice=audioMapper.getAudioByPlatenumberid(pointposition.getId(),currentUserId);
                if(voice!=null&&!voice.equals("")&&!voice.equals("null")){

                    for(int k=0;k<voice.size();k++){

                        if(voice.get(k).indexOf("mandarin")!=-1){
                            pointpositionVo.setMandarinVoice(voice.get(k));
                        }

                        if(voice.get(k).indexOf("english")!=-1){
                            pointpositionVo.setEnglishVoice(voice.get(k));
                        }

                        if(voice.get(k).indexOf("dialect")!=-1){
                            pointpositionVo.setDialectVoice(voice.get(k));
                        }

                    }



                }



                List<String> picture=pictureMapper.getPictureByPlatenumberid(pointposition.getId(),currentUserId);
                if(picture!=null&!picture.equals("")){

                    pointpositionVo.setPictureUrl(picture);
                }


                pointpositionVo.setTextOfMater(pointposition.getTextofmater());
                pointpositionVo.setSize(pointposition.getSize());
                pointpositionVo.setYearOfEsent(pointposition.getYearofesent());
                pointpositionVo.setMainteUnit(pointposition.getMainteunit());
                pointpositionVo.setNumofMaint(pointposition.getNumofmaint());
                pointpositionVo.setMaintPeriod(pointposition.getMaintperiod());
                pointpositionVo.setMaintCost(pointposition.getMaintcost());
                pointpositionVo.setMaintAccounts(pointposition.getMaintaccounts());

                pointpositionVo.setSharelinks(pointposition.getSharelinks());

                pointpositionVoList.add(pointpositionVo);


            }
            ListVO listVO=new ListVO();

            listVO.setList(pointpositionVoList);
            listVO.setCount(count);

            return  ResultVOUtil.success(listVO);



        }




    }






    @ApiOperation(value = "获取点位", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),


    })
    @GetMapping("/getListById")
    private ResultVO getListById(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Platenumber pointposition=platenumberMapper.getNumberplateById(id,currentUserId);



        PlateNumberVo pointpositionVo=new PlateNumberVo();

        pointpositionVo.setId(pointposition.getId());
        pointpositionVo.setName(pointposition.getName());
        pointpositionVo.setLonandlat(pointposition.getLonandlat());
        pointpositionVo.setType(pointposition.getType());
        pointpositionVo.setUrl(pointposition.getUrl());
        pointpositionVo.setStatus(pointposition.getStatus());
        List<String> picture=pictureMapper.getPictureByPlatenumberid(pointposition.getId(),currentUserId);
        if(picture!=null&!picture.equals("")){

            pointpositionVo.setPictureUrl(picture);
        }


        List<String> voice=audioMapper.getAudioByPlatenumberid(pointposition.getId(),currentUserId);
        if(voice!=null){

            for(int k=0;k<voice.size();k++){

                if(voice.get(k).indexOf("mandarin")!=-1){
                    pointpositionVo.setMandarinVoice(voice.get(k));
                }

                if(voice.get(k).indexOf("english")!=-1){
                    pointpositionVo.setEnglishVoice(voice.get(k));
                }

                if(voice.get(k).indexOf("dialect")!=-1){
                    pointpositionVo.setDialectVoice(voice.get(k));
                }

            }



        }

        pointpositionVo.setTextOfMater(pointposition.getTextofmater());
        pointpositionVo.setSize(pointposition.getSize());
        pointpositionVo.setYearOfEsent(pointposition.getYearofesent());
        pointpositionVo.setMainteUnit(pointposition.getMainteunit());
        pointpositionVo.setNumofMaint(pointposition.getNumofmaint());
        pointpositionVo.setMaintPeriod(pointposition.getMaintperiod());
        pointpositionVo.setMaintCost(pointposition.getMaintcost());
        pointpositionVo.setMaintAccounts(pointposition.getMaintaccounts());

        return  ResultVOUtil.success(pointpositionVo);




    }





    @ApiOperation(value = "添加门牌维护记录", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "门牌id", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "maintcontent", value = "维护内容", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintcompany", value = "维护公司", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contactsnum", value = "联系电话", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "cost", value = "费用", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/addMaintOfPlateNum")
    private ResultVO addMaintOfPlateNum( HttpServletRequest request) throws  IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        String maintcontent=request.getParameter("maintcontent");

        String maintcompany=request.getParameter("maintcompany");

        String contacts=request.getParameter("contacts");

        String contactsnum=request.getParameter("contactsnum");

        String cost=request.getParameter("cost");

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time=sdf.format(date);


        Platenumber platenumber=platenumberMapper.getNumberplateById(id,currentUserId);


        platenumberMapper.addMaintrecordsOfPlateNum("门牌",null,maintcontent,
                maintcompany,contacts,contactsnum,cost,time,currentUserId);


        Maintrecords maintrecords=maintrecordsMapper.getMaxMainrecord();


        return ResultVOUtil.success(maintrecords.getId());



    }




    @ApiOperation(value = "查询经纬度", notes = "")
    @ApiImplicitParams({


    })
    @GetMapping("/getLonAndLat")
    private ResultVO getLonAndLat(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        List<String> stringList=platenumberMapper.getLonAndLat(currentUserId);

        List<LonAndLatVo> lonAndLatVos=new ArrayList<>();

        for(int i=0;i<stringList.size();i++){
            String str=stringList.get(i);
            LonAndLatVo lonAndLatVo=new LonAndLatVo();

            if(str!=null&&!str.equals("")) {
                String[] strings = str.split(",");

                lonAndLatVo.setLng(strings[0]);
                lonAndLatVo.setLat(strings[1]);

                lonAndLatVos.add(lonAndLatVo);
            }

        }

        return ResultVOUtil.success(lonAndLatVos);


    }






    @ApiOperation(value = "根据经纬度来查询", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "key", value = "经纬度", required = false, dataType = "string", paramType = "query"),
    })
    @GetMapping("/getList2")
    private PlateNumberVo getList2(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String key=request.getParameter("key");

             Platenumber platenumber=platenumberMapper.getPlatenuByLonAndLat(key,currentUserId);

             PlateNumberVo plateNumberVo=new PlateNumberVo();

             plateNumberVo.setId(platenumber.getId());
             plateNumberVo.setName(platenumber.getName());
             plateNumberVo.setLonandlat(platenumber.getLonandlat());
             plateNumberVo.setType(platenumber.getType());
             plateNumberVo.setUrl(platenumber.getUrl());
             plateNumberVo.setStatus(platenumber.getStatus());
             List<String> picture=pictureMapper.getPictureByPlatenumberid(platenumber.getId(),currentUserId);
             if(picture!=null&!picture.equals("")){

                 plateNumberVo.setPictureUrl(picture);
             }


             List<String> voice=audioMapper.getAudioByPlatenumberid(platenumber.getId(),currentUserId);
        if(voice!=null){

            for(int k=0;k<voice.size();k++){


                if(voice.get(k).indexOf("mandarin")!=-1){
                    plateNumberVo.setMandarinVoice(voice.get(k));
                }


                if(voice.get(k).indexOf("english")!=-1){
                    plateNumberVo.setEnglishVoice(voice.get(k));
                }


                if(voice.get(k).indexOf("dialect")!=-1){
                    plateNumberVo.setDialectVoice(voice.get(k));
                }


            }

        }
             plateNumberVo.setTextOfMater(platenumber.getTextofmater());
             plateNumberVo.setSize(platenumber.getSize());
             plateNumberVo.setYearOfEsent(platenumber.getYearofesent());
             plateNumberVo.setMainteUnit(platenumber.getMainteunit());
             plateNumberVo.setNumofMaint(platenumber.getNumofmaint());
             plateNumberVo.setMaintPeriod(platenumber.getMaintperiod());
             plateNumberVo.setMaintCost(platenumber.getMaintcost());
             plateNumberVo.setMaintAccounts(platenumber.getMaintaccounts());

         return plateNumberVo;


    }





    @ApiOperation(value = "通过Url来获取信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "url", value = "url", required = false, dataType = "integer", paramType = "query"),
    })
    @GetMapping("/getPlateNumByUrl")
    private PlateNumberVo getPlateNumByUrl(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String url=request.getParameter("url");

        Platenumber platenumber=platenumberMapper.getPlateNumByUrl(url,currentUserId);


        PlateNumberVo plateNumberVo=new PlateNumberVo();

        plateNumberVo.setId(platenumber.getId());
        plateNumberVo.setName(platenumber.getName());
        plateNumberVo.setLonandlat(platenumber.getLonandlat());
        plateNumberVo.setType(platenumber.getType());
        plateNumberVo.setUrl(platenumber.getUrl());
        plateNumberVo.setStatus(platenumber.getStatus());
        List<String> picture=pictureMapper.getPictureByPlatenumberid(platenumber.getId(),currentUserId);
        if(picture!=null&!picture.equals("")){

            plateNumberVo.setPictureUrl(picture);
        }


        List<String> voice=audioMapper.getAudioByPlatenumberid(platenumber.getId(),currentUserId);

        if(voice!=null){

            for(int k=0;k<voice.size();k++){

                if(voice.get(k).indexOf("mandarin")!=-1){
                    plateNumberVo.setMandarinVoice(voice.get(k));
                }

                if(voice.get(k).indexOf("english")!=-1){
                    plateNumberVo.setEnglishVoice(voice.get(k));
                }

                if(voice.get(k).indexOf("dialect")!=-1){
                    plateNumberVo.setDialectVoice(voice.get(k));
                }

            }



        }



        plateNumberVo.setTextOfMater(platenumber.getTextofmater());
        plateNumberVo.setSize(platenumber.getSize());
        plateNumberVo.setYearOfEsent(platenumber.getYearofesent());
        plateNumberVo.setMainteUnit(platenumber.getMainteunit());
        plateNumberVo.setNumofMaint(platenumber.getNumofmaint());
        plateNumberVo.setMaintPeriod(platenumber.getMaintperiod());
        plateNumberVo.setMaintCost(platenumber.getMaintcost());
        plateNumberVo.setMaintAccounts(platenumber.getMaintaccounts());

        return plateNumberVo;


         }






    @ApiOperation(value = "获取门牌统计数量" , notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "条数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getPlateNumCountByName")
    private ResultVO getPlateNumCountByName(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name=request.getParameter("name");

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer  size=Integer.valueOf(request.getParameter("size"));


        if(name!=null){

            Integer count=countMapper.getCountOfAll("%"+name+"%","platenumber",currentUserId);

            PageHelper.startPage(page,size);
            List<Count>  plateNumberCount=countMapper.getPlateNumberCount("%"+name+"%","platenumber",currentUserId);

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(plateNumberCount);

            return ResultVOUtil.success(listVO);

        }

          else{

            Integer count=countMapper.getCountOfAll(null,"platenumber",currentUserId);

            PageHelper.startPage(page,size);

            List<Count>  plateNumberCount=countMapper.getPlateNumberCount(null,"platenumber",currentUserId);

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(plateNumberCount);

            return ResultVOUtil.success(listVO);


        }


    }





    @ApiOperation(value = "获取总的名称和链接" , notes = "")
    @ApiImplicitParams({

    })
    @GetMapping("/getNameAndUrl")
    private ResultVO getNameAndUrl(HttpServletRequest request) {


        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        List<PlateNumberNaUr> platenumberList=plateNumberNaUrMapper.getNameAndUrl(currentUserId);



        return ResultVOUtil.success(platenumberList);

    }

    }