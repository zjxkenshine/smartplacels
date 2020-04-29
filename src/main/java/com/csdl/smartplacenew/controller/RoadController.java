package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Maintrecords;
import com.csdl.smartplacenew.pojo.Road;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.two.RoadNaUr;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.LonAndLatVo;
import com.csdl.smartplacenew.vo.ResultVO;
import com.csdl.smartplacenew.vo.RoadVo;
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

@Api(description = "道路管理接口")
@RestController
@RequestMapping("/Road")
public class RoadController {


    @Resource
    private RoadMapper roadMapper;


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
    RoadNaUrMapper roadNaUrMapper;

    @Resource
    MaintrecordsMapper maintrecordsMapper;

    @Resource
    FeedbackMapper feedbackMapper;

    @ApiOperation(value = "添加道路", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "streetnumber", value = "街道编号", required = false, dataType = "string", paramType = "query"),
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

        String streetnumber = request.getParameter("streetnumber");

        String name = request.getParameter("name");

        String lonandlat=null;

         if(request.getParameter("lon")!=null&&!request.getParameter("lon").equals("")) {
             Double lon = Double.valueOf(request.getParameter("lon"));
             Double lat = Double.valueOf(request.getParameter("lat"));
             String londecimalFormat1 = new DecimalFormat("#,##0.000000").format(lon);
             String latdecimalFormat1 = new DecimalFormat("#,##0.000000").format(lat);
              lonandlat = londecimalFormat1 + "," + latdecimalFormat1;

         }else{
             lonandlat=null;
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

        String    sharelinks=request.getParameter("sharelinks");

        String englishword=request.getParameter("englishword");

        String imgVirtualPath=null;

        Integer roadMapperMaxId=roadMapper.getMaxIdByAll(currentUserId);

        if(roadMapperMaxId==null){

            roadMapperMaxId=1;

        }

        if(roadMapperMaxId>0){

            roadMapperMaxId=roadMapperMaxId+1;

        }



        roadMapper.addRoad(streetnumber,name, lonandlat, type, currentUserId+"/"+roadMapperMaxId,imgVirtualPath, 1,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,currentUserId,sharelinks,englishword
                );


        Road road=roadMapper.getRoadByMaxId(currentUserId);

        formtypeMapper.addFormtype(type, 1,currentUserId);


        List<Road> roadList=roadMapper.getRoadByName(name,currentUserId);

        if(roadList.size()==1){

            roadMapper.addCountOfRoad(name,roadList.size(),0,"road",1,0,currentUserId);

        }


        if(roadList.size()>1){

            countMapper.updatePlateCount2(name,"road",currentUserId);

        }

        return ResultVOUtil.success(road.getId());


    }


    @ApiOperation(value = "删除道路信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "删除道路信息id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id = Integer.valueOf(request.getParameter("id"));


        Road road=roadMapper.getRoadById(id,currentUserId);


        List<Road> roadList=roadMapper.getRoadByName(road.getName(),currentUserId);

        if(roadList.size()==1){
            countMapper.updatePlateCount4(road.getName(),"road",currentUserId);
        }

        if(roadList.size()>1){

            countMapper.updatePlateCount3(road.getName(),"road",currentUserId);

        }

        roadMapper.updateRoadStatus(id,currentUserId);

        return ResultVOUtil.success("成功删除:" + road);


    }




    @ApiOperation(value = "修改道路", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "点位id", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "streetnumber", value = "街道编号", required = false, dataType = "string", paramType = "query"),
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

            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = true, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "englishword", value = "英语内容", required = true, dataType = "string", paramType = "query"),
    })
    @PostMapping("/update")
    private ResultVO update( HttpServletRequest request) throws IOException {


        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Road road=roadMapper.getRoadById(id,currentUserId);

        String streetnumber=null;

        String name=null;

        Double lon=null;
        Double lat=null;

        String lonandlat=null;

        String type=null;

        String codeaddress=null;


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



        if(request.getParameter("streetnumber")!=null&&!request.getParameter("streetnumber").equals("")){

            streetnumber=request.getParameter("streetnumber");

        }else{
            streetnumber=road.getStreetnumber();
        }



        if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){

            name=request.getParameter("name");

        }else{
            name=road.getName();
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
            type=road.getType();
        }



        if(request.getParameter("url")!=null&&!request.getParameter("url").equals("")){

            codeaddress=request.getParameter("url");

        }else{
            codeaddress=road.getCodeaddress();
        }


        if(request.getParameter("textofmater")!=null&&!request.getParameter("textofmater").equals("")){
            textofmater=request.getParameter("textofmater");

        }else{
            textofmater=road.getTextofmater();
        }


        if(request.getParameter("size")!=null&&!request.getParameter("size").equals("")){
            size=request.getParameter("size");

        }else{
            size=road.getSize();
        }


        if(request.getParameter("yearofesent")!=null&&!request.getParameter("yearofesent").equals("")){
            yearofesent=request.getParameter("yearofesent");

        }else{
            yearofesent=road.getYearofesent();
        }


        if(request.getParameter("mainteunit")!=null&&!request.getParameter("mainteunit").equals("")){
            mainteunit=request.getParameter("mainteunit");

        }else{
            mainteunit=road.getMainteunit();
        }



        if(request.getParameter("numofmaint")!=null&&!request.getParameter("numofmaint").equals("")){
            numofmaint=request.getParameter("numofmaint");

        }else{
            numofmaint=road.getNumofmaint();
        }



        if(request.getParameter("maintperiod")!=null&&!request.getParameter("maintperiod").equals("")){
            maintperiod=request.getParameter("maintperiod");

        }else{
            maintperiod=road.getMaintperiod();
        }


        if(request.getParameter("maintcost")!=null&&!request.getParameter("maintcost").equals("")){
            maintcost=request.getParameter("maintcost");

        }else{
            maintcost=road.getMaintcost();
        }



        if(request.getParameter("maintaccounts")!=null&&!request.getParameter("maintaccounts").equals("")){
            maintaccounts=request.getParameter("maintaccounts");

        }else{
            maintaccounts=road.getMaintaccounts();
        }


        if(request.getParameter("englishword")!=null&&!request.getParameter("englishword").equals("")){
            englishword=request.getParameter("englishword");

        }else{
            englishword=road.getEnglishword();
        }


        String imgVirtualPath=null;


            imgVirtualPath=road.getVoiceurl();


        roadMapper.updateRoadById(streetnumber,name,lonandlat,type,road.getCodeaddress(),imgVirtualPath,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,sharelinks,englishword,id,
                currentUserId
                );

        countMapper.updateCountName(name,road.getName(),"road",currentUserId);

        feedbackMapper.updateFeddBackFeedbackpath(name,road.getName(),"道路管理",currentUserId);


        Road road1=roadMapper.getRoadById(id,currentUserId);

        return  ResultVOUtil.success(road1);


    }





    @ApiOperation(value = "获取道路信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "key", value = "关键字", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "integer", paramType = "query"),

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

            Integer count=roadMapper.getRoadByKeyCount(currentUserId,"%"+key+"%");

            PageHelper.startPage(page,size);

            List<Road> roadList=roadMapper.getRoadByKey(currentUserId,"%"+key+"%");

            List<RoadVo> roadVoList=new ArrayList<>();

            for(int i=0;i<roadList.size();i++){
                Road road=roadList.get(i);

                RoadVo roadVo=new RoadVo();
                roadVo.setId(road.getId());
                roadVo.setStreetNumber(road.getStreetnumber());
                roadVo.setName(road.getName());

                if(road.getLonandlat()!=null&&!road.getLonandlat().equals("")){

                    String[] strings=road.getLonandlat().split(",");

                    roadVo.setLon(strings[0]);
                    roadVo.setLat(strings[1]);

                }

                roadVo.setType(road.getType());
                roadVo.setUrl(road.getCodeaddress());

                roadVo.setMandarinWord(road.getMandarinword());
                roadVo.setEnglishWord(road.getEnglishword());

                List<String>  stringList=audioMapper.getAudioByRoadid(road.getId(),currentUserId);

                if(stringList!=null&&!stringList.equals("")&&!stringList.equals("null")){

                    for(int k=0;k<stringList.size();k++){

                        if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                            roadVo.setMandarinVoice(stringList.get(k));
                        }

                        if(stringList.get(k).lastIndexOf("english")!=-1){
                            roadVo.setEnglishVoice(stringList.get(k));
                        }

                        if(stringList.get(k).lastIndexOf("dialect")!=-1){
                            roadVo.setDialectVoice(stringList.get(k));
                        }

                    }

                }



                List<String> picture=pictureMapper.getPictureByRoadid(road.getId(),currentUserId);
                if(picture!=null&&!picture.equals("")){
                    roadVo.setPictureUrl(picture);
                }
                roadVo.setStatus(road.getStatus());


                roadVo.setTextOfMater(road.getTextofmater());
                roadVo.setSize(road.getSize());
                roadVo.setYearOfEsent(road.getYearofesent());
                roadVo.setMainteUnit(road.getMainteunit());
                roadVo.setNumofMaint(road.getNumofmaint());
                roadVo.setMaintPeriod(road.getMaintperiod());
                roadVo.setMaintCost(road.getMaintcost());
                roadVo.setMaintAccounts(road.getMaintaccounts());
                roadVo.setSharelinks(road.getSharelinks());



                roadVoList.add(roadVo);

            }

            ListVO listVO=new ListVO();
            listVO.setList(roadVoList);
            listVO.setCount(count);

            return  ResultVOUtil.success(listVO);

        }

        else {

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            Integer count=roadMapper.getRoadByKeyCount(currentUserId,null);


            PageHelper.startPage(page,size);

            List<Road> roadList=roadMapper.getRoadByKey(currentUserId,null);

            List<RoadVo> roadVoList=new ArrayList<>();

            for(int i=0;i<roadList.size();i++){
                Road road=roadList.get(i);

                RoadVo roadVo=new RoadVo();
                roadVo.setId(road.getId());
                roadVo.setStreetNumber(road.getStreetnumber());
                roadVo.setName(road.getName());

                if(road.getLonandlat()!=null&&!road.getLonandlat().equals("")){

                    String[] strings=road.getLonandlat().split(",");

                    roadVo.setLon(strings[0]);
                    roadVo.setLat(strings[1]);

                }
                roadVo.setType(road.getType());
                roadVo.setUrl(road.getCodeaddress());
                roadVo.setMandarinWord(road.getMandarinword());
                roadVo.setEnglishWord(road.getEnglishword());

                List<String>  stringList=audioMapper.getAudioByRoadid(road.getId(),currentUserId);

                if(stringList!=null&&!stringList.equals("")&&!stringList.equals("null")){

                    for(int k=0;k<stringList.size();k++){

                        if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                            roadVo.setMandarinVoice(stringList.get(k));
                        }

                        if(stringList.get(k).lastIndexOf("english")!=-1){
                            roadVo.setEnglishVoice(stringList.get(k));
                        }

                        if(stringList.get(k).lastIndexOf("dialect")!=-1){
                            roadVo.setDialectVoice(stringList.get(k));
                        }

                    }

                }


                List<String> picture=pictureMapper.getPictureByRoadid(road.getId(),currentUserId);
                if(picture!=null&&!picture.equals("")){
                    roadVo.setPictureUrl(picture);
                }

                roadVo.setStatus(road.getStatus());

                roadVo.setTextOfMater(road.getTextofmater());
                roadVo.setSize(road.getSize());
                roadVo.setYearOfEsent(road.getYearofesent());
                roadVo.setMainteUnit(road.getMainteunit());
                roadVo.setNumofMaint(road.getNumofmaint());
                roadVo.setMaintPeriod(road.getMaintperiod());
                roadVo.setMaintCost(road.getMaintcost());
                roadVo.setMaintAccounts(road.getMaintaccounts());
                roadVo.setSharelinks(road.getSharelinks());


                roadVoList.add(roadVo);

            }


            ListVO listVO=new ListVO();
            listVO.setList(roadVoList);
            listVO.setCount(count);

            return  ResultVOUtil.success(listVO);

        }




    }






    @ApiOperation(value = "获取道路信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),


    })
    @GetMapping("/getListById")
    private ResultVO getListById(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Road road=roadMapper.getRoadById(id,currentUserId);

        RoadVo roadVo=new RoadVo();

            roadVo.setId(road.getId());
            roadVo.setStreetNumber(road.getStreetnumber());
            roadVo.setName(road.getName());
            roadVo.setLonandlat(road.getLonandlat());
            roadVo.setType(road.getType());
            roadVo.setUrl(road.getCodeaddress());
        List<String>  stringList=audioMapper.getAudioByRoadid(road.getId(),currentUserId);
        if(stringList!=null){

            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    roadVo.setMandarinVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("english")!=-1){
                    roadVo.setEnglishVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("dialect")!=-1){
                    roadVo.setDialectVoice(stringList.get(k));
                }

            }

        }


        roadVo.setStatus(road.getStatus());

         List<String> picture=pictureMapper.getPictureByRoadid(road.getId(),currentUserId);
        if(picture!=null&&!picture.equals("")){
            roadVo.setPictureUrl(picture);
        }

        roadVo.setTextOfMater(road.getTextofmater());
        roadVo.setSize(road.getSize());
        roadVo.setYearOfEsent(road.getYearofesent());
        roadVo.setMainteUnit(road.getMainteunit());
        roadVo.setNumofMaint(road.getNumofmaint());
        roadVo.setMaintPeriod(road.getMaintperiod());
        roadVo.setMaintCost(road.getMaintcost());
        roadVo.setMaintAccounts(road.getMaintaccounts());


        return  ResultVOUtil.success(roadVo);



    }





    @ApiOperation(value = "添加道路维护记录", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "道路id", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "maintcontent", value = "维护内容", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "maintcompany", value = "维护公司", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "contactsnum", value = "联系电话", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "cost", value = "费用", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/addMaintOfRoad")
    private ResultVO addMaintOfRoad(HttpServletRequest request) throws  IOException {

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



        Road road=roadMapper.getRoadById(id,currentUserId);

        roadMapper.addMaintrecordsOfRoad("道路",null,maintcontent,
                maintcompany,contacts,contactsnum,cost,time,currentUserId);


        Maintrecords maintrecords=maintrecordsMapper.getMaxMainrecord();


        return ResultVOUtil.success(maintrecords.getId());


    }



    @ApiOperation(value = "查询所有经纬度信息", notes = "")
    @ApiImplicitParams({

    })
    @GetMapping("/getLonAndLat")
    private ResultVO getLonAndLat(HttpServletRequest request) {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        List<String> stringList=roadMapper.getLonAndLat(currentUserId);

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




    @ApiOperation(value = "根据经纬度来查询信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "key", value = "经纬度", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList2")
    private RoadVo getList2(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);

        Integer currentUserId=user.getId();

        String key=request.getParameter("key");


        Road  road=roadMapper.getRoadByLonAndLat(key,currentUserId);

            RoadVo roadVo=new RoadVo();
            roadVo.setId(road.getId());
            roadVo.setStreetNumber(road.getStreetnumber());
            roadVo.setName(road.getName());
            roadVo.setLonandlat(road.getLonandlat());
            roadVo.setType(road.getType());
            roadVo.setUrl(road.getCodeaddress());
            List<String>  stringList=audioMapper.getAudioByRoadid(road.getId(),currentUserId);
        if(stringList!=null){

            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    roadVo.setMandarinVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("english")!=-1){
                    roadVo.setEnglishVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("dialect")!=-1){
                    roadVo.setDialectVoice(stringList.get(k));
                }

            }

        }



        List<String> picture=pictureMapper.getPictureByRoadid(road.getId(),currentUserId);
            if(picture!=null&&!picture.equals("")){
                roadVo.setPictureUrl(picture);
            }

            roadVo.setStatus(road.getStatus());

            roadVo.setTextOfMater(road.getTextofmater());
            roadVo.setSize(road.getSize());
            roadVo.setYearOfEsent(road.getYearofesent());
            roadVo.setMainteUnit(road.getMainteunit());
            roadVo.setNumofMaint(road.getNumofmaint());
            roadVo.setMaintPeriod(road.getMaintperiod());
            roadVo.setMaintCost(road.getMaintcost());
            roadVo.setMaintAccounts(road.getMaintaccounts());



        return roadVo;

    }



    @ApiOperation(value = "通过Url来获取信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "url", value = "url", required = false, dataType = "integer", paramType = "query"),
    })
    @GetMapping("/getRoadByUrl")
    private RoadVo getRoadByUrl(HttpServletRequest request) {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String url=request.getParameter("url");

        Road road=roadMapper.getRoadByUrl(url,currentUserId);



        RoadVo roadVo=new RoadVo();

        roadVo.setId(road.getId());
        roadVo.setStreetNumber(road.getStreetnumber());
        roadVo.setName(road.getName());
        roadVo.setLonandlat(road.getLonandlat());
        roadVo.setType(road.getType());
        roadVo.setUrl(road.getCodeaddress());

        List<String>  stringList=audioMapper.getAudioByRoadid(road.getId(),currentUserId);
        if(stringList!=null){

            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    roadVo.setMandarinVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("english")!=-1){
                    roadVo.setEnglishVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("dialect")!=-1){
                    roadVo.setDialectVoice(stringList.get(k));
                }

            }

        }


        roadVo.setStatus(road.getStatus());

        List<String> picture=pictureMapper.getPictureByRoadid(road.getId(),currentUserId);
        if(picture!=null&&!picture.equals("")){
            roadVo.setPictureUrl(picture);
        }

        roadVo.setTextOfMater(road.getTextofmater());
        roadVo.setSize(road.getSize());
        roadVo.setYearOfEsent(road.getYearofesent());
        roadVo.setMainteUnit(road.getMainteunit());
        roadVo.setNumofMaint(road.getNumofmaint());
        roadVo.setMaintPeriod(road.getMaintperiod());
        roadVo.setMaintCost(road.getMaintcost());
        roadVo.setMaintAccounts(road.getMaintaccounts());


        return  roadVo;


    }





    @ApiOperation(value = "获取道路统计数量" , notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "条数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getRoadCountByName")
    private ResultVO getRoadCountByName(HttpServletRequest request) {

        String name=request.getParameter("name");

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer  size=Integer.valueOf(request.getParameter("size"));

        if(name!=null){

            Integer count=countMapper.getCountOfAll("%"+name+"%","road",currentUserId);

            PageHelper.startPage(page,size);

            List<Count>  plateNumberCount=countMapper.getPlateNumberCount("%"+name+"%","road",currentUserId);

            ListVO listVO=new ListVO();

            listVO.setList(plateNumberCount);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);
        }
        else{

            Integer count=countMapper.getCountOfAll(null,"road",currentUserId);

            PageHelper.startPage(page,size);
            List<Count>  plateNumberCount=countMapper.getPlateNumberCount(null,"road",currentUserId);

            ListVO listVO=new ListVO();

            listVO.setList(plateNumberCount);
            listVO.setCount(count);

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


        List<RoadNaUr> roadList=roadNaUrMapper.getNameAndUrl(currentUserId);

        return ResultVOUtil.success(roadList);

    }


}
