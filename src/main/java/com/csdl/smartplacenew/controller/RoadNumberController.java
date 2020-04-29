package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Maintrecords;
import com.csdl.smartplacenew.pojo.Roadnumber;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.two.RoadNumberNaUr;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.LonAndLatVo;
import com.csdl.smartplacenew.vo.ResultVO;
import com.csdl.smartplacenew.vo.RoadNumberVo;
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

@Api(description = "路牌管理接口")
@RestController
@RequestMapping("/RoadNumber")
public class RoadNumberController {


    @Resource
    private RoadnumberMapper roadnumberMapper;

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
    RoadNumberNaUrMapper roadNumberNaUrMapper;

    @Resource
    MaintrecordsMapper maintrecordsMapper;

    @Resource
    FeedbackMapper feedbackMapper;

    @Resource
    VillagenumberSYMapper villagenumberSYMapper;


    @ApiOperation(value = "添加路牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "roadnum", value = "路牌编号", required = false, dataType = "string", paramType = "query"),
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

        String roadnum=request.getParameter("roadnum");

        String name=request.getParameter("name");
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



        String type=request.getParameter("type");

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




        Integer roadnumberMapperMaxId=roadnumberMapper.getMaxIdByAll(currentUserId);

        if(roadnumberMapperMaxId==null){

            roadnumberMapperMaxId=1;

        }

        if(roadnumberMapperMaxId>0){

            roadnumberMapperMaxId=roadnumberMapperMaxId+1;

        }

        roadnumberMapper.addRoadnumber(roadnum,name,lonandlat,type,currentUserId+"/"+roadnumberMapperMaxId,imgVirtualPath,1,

                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,currentUserId,sharelinks,englishword);


        Roadnumber roadnumber=roadnumberMapper.getRoadnumberByMaxId(currentUserId);


        formtypeMapper.addFormtype(type, 1,currentUserId);


        List<Roadnumber> roadnumberList=roadnumberMapper.getRoadNumByName(name,currentUserId);

        if(roadnumberList.size()==1){

            roadnumberMapper.addCountOfRoadNumber(name,roadnumberList.size(),0,"roadnumber",1,0,currentUserId);
        }

        if(roadnumberList.size()>1){

            countMapper.updatePlateCount2(name,"roadnumber",currentUserId);

        }

        return ResultVOUtil.success(roadnumber.getId());

    }



    @ApiOperation(value = "删除路牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "路牌id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del(HttpServletRequest request) {

        Integer id=Integer.valueOf(request.getParameter("id"));

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Roadnumber roadnumber=roadnumberMapper.getRoadnumberById(id,currentUserId);


        List<Roadnumber > roadnumberList=roadnumberMapper.getRoadNumByName(roadnumber.getName(),currentUserId);

        if(roadnumberList.size()==1){

            countMapper.updatePlateCount4(roadnumber.getName(),"roadnumber",currentUserId);

        }

        if(roadnumberList.size()>1){

            countMapper.updatePlateCount3(roadnumber.getName(),"roadnumber",currentUserId);

        }
        roadnumberMapper.updateRoadnumberStatus(id,currentUserId);

        return ResultVOUtil.success("成功删除:" + roadnumber);


    }






    @ApiOperation(value = "修改路牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "点位id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "roadnum", value = "路牌编号", required = false, dataType = "string", paramType = "query"),
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
        if(user==null){
            return ResultVOUtil.byEnum(CodeMessage.UserNotLogin);
        }
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

         Roadnumber roadnumber=roadnumberMapper.getRoadnumberById(id,currentUserId);

        String name=null;

        Double lon=null;
        Double lat=null;


        String lonandlat=null;

        String type=null;


        String textofmater=null;
        String size=null;
        String yearofesent=null;
        String mainteunit=null;
        String numofmaint=null;
        String maintperiod=null;
        String maintcost=null;
        String maintaccounts=null;

        String roadnum=null;

        String englishword=null;

        String sharelinks=request.getParameter("sharelinks");


        if(request.getParameter("roadnum")!=null&&!request.getParameter("roadnum").equals("")){

            roadnum=request.getParameter("roadnum");

        }else{
            roadnum=roadnumber.getRoadnum();
        }


        if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){

            name=request.getParameter("name");

        }else{
            name=roadnumber.getName();
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
            type=roadnumber.getType();
        }




        if(request.getParameter("textofmater")!=null&&!request.getParameter("textofmater").equals("")){
            textofmater=request.getParameter("textofmater");

        }else{
            textofmater=roadnumber.getTextofmater();
        }


        if(request.getParameter("size")!=null&&!request.getParameter("size").equals("")){
            size=request.getParameter("size");

        }else{
            size=roadnumber.getSize();
        }


        if(request.getParameter("yearofesent")!=null&&!request.getParameter("yearofesent").equals("")){
            yearofesent=request.getParameter("yearofesent");

        }else{
            yearofesent=roadnumber.getYearofesent();
        }


        if(request.getParameter("mainteunit")!=null&&!request.getParameter("mainteunit").equals("")){
            mainteunit=request.getParameter("mainteunit");

        }else{
            mainteunit=roadnumber.getMainteunit();
        }



        if(request.getParameter("numofmaint")!=null&&!request.getParameter("numofmaint").equals("")){
            numofmaint=request.getParameter("numofmaint");

        }else{
            numofmaint=roadnumber.getNumofmaint();
        }


        if(request.getParameter("maintperiod")!=null&&!request.getParameter("maintperiod").equals("")){
            maintperiod=request.getParameter("maintperiod");

        }else{
            maintperiod=roadnumber.getMaintperiod();
        }


        if(request.getParameter("maintcost")!=null&&!request.getParameter("maintcost").equals("")){
            maintcost=request.getParameter("maintcost");

        }else{
            maintcost=roadnumber.getMaintcost();
        }


        if(request.getParameter("maintaccounts")!=null&&!request.getParameter("maintaccounts").equals("")){
            maintaccounts=request.getParameter("maintaccounts");

        }else{
            maintaccounts=roadnumber.getMaintaccounts();
        }


        if(request.getParameter("englishword")!=null&&!request.getParameter("englishword").equals("")){
            englishword=request.getParameter("englishword");

        }else{
            englishword=roadnumber.getEnglishword();
        }

        String imgVirtualPath=null;


        imgVirtualPath=roadnumber.getVoiceurl();


        roadnumberMapper.updateRoadnumberById(roadnum,name,lonandlat,type,roadnumber.getUrl(),imgVirtualPath,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,sharelinks,englishword,id,currentUserId
                );

        countMapper.updateCountName(name,roadnumber.getName(),"roadnumber",currentUserId);

        feedbackMapper.updateFeddBackFeedbackpath(name,roadnumber.getName(),"路牌管理",currentUserId);

        Roadnumber roadnumber1=roadnumberMapper.getRoadnumberById(id,currentUserId);

        return  ResultVOUtil.success("成功更新:"+roadnumber1);



    }




    @ApiOperation(value = "获取路牌信息", notes = "")
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

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        if(key!=null&&!key.equals("")){

            Integer count=roadnumberMapper.getRoadnumberByKeyCount(currentUserId,"%"+key+"%");

            PageHelper.startPage(page,size);

            List<Roadnumber> roadnumberList=roadnumberMapper.getRoadnumberByKey(currentUserId,"%"+key+"%");

            List<RoadNumberVo> roadNumberVoList=new ArrayList<>();

            for(int i=0;i<roadnumberList.size();i++){

                Roadnumber roadnumber=roadnumberList.get(i);

                RoadNumberVo roadNumberVo=new RoadNumberVo();

                roadNumberVo.setId(roadnumber.getId());
                roadNumberVo.setName(roadnumber.getName());
                roadNumberVo.setRoadNum(roadnumber.getRoadnum());

                if(roadnumber.getLonandlat()!=null&&!roadnumber.getLonandlat().equals("")){

                    String[] string=roadnumber.getLonandlat().split(",");
                    roadNumberVo.setLon(string[0]);
                    roadNumberVo.setLat(string[1]);

                }

                roadNumberVo.setType(roadnumber.getType());
                roadNumberVo.setUrl(roadnumber.getUrl());

                roadNumberVo.setMandarinWord(roadnumber.getMandarinword());
                roadNumberVo.setEnglishWord(roadnumber.getEnglishword());


                List<String> voice=audioMapper.getAudioByRoadnumberid(roadnumber.getId(),currentUserId);
                if(voice!=null&&!voice.equals("")&&!voice.equals("null")) {

                    for(int k=0;k<voice.size();k++){

                        if(voice.get(k).lastIndexOf("mandarin")!=-1){
                            roadNumberVo.setMandarinVoice(voice.get(k));
                        }

                        if(voice.get(k).lastIndexOf("english")!=-1){
                            roadNumberVo.setEnglishVoice(voice.get(k));
                        }

                        if(voice.get(k).lastIndexOf("dialect")!=-1){
                            roadNumberVo.setDialectVoice(voice.get(k));
                        }


                    }

                }


                List<String> picture=pictureMapper.getPictureByRoadnumberid(roadnumber.getId(),currentUserId
                );
                if(picture!=null&&!picture.equals("")){
                    roadNumberVo.setPictureUrl(picture);
                }


                roadNumberVo.setTextOfMater(roadnumber.getTextofmater());
                roadNumberVo.setSize(roadnumber.getSize());
                roadNumberVo.setYearOfEsent(roadnumber.getYearofesent());
                roadNumberVo.setMainteUnit(roadnumber.getMainteunit());
                roadNumberVo.setNumofMaint(roadnumber.getNumofmaint());
                roadNumberVo.setMaintPeriod(roadnumber.getMaintperiod());
                roadNumberVo.setMaintCost(roadnumber.getMaintcost());
                roadNumberVo.setMaintAccounts(roadnumber.getMaintaccounts());

                roadNumberVo.setSharelinks(roadnumber.getSharelinks());

                roadNumberVoList.add(roadNumberVo);
            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(roadNumberVoList);

            return ResultVOUtil.success(listVO);

        }
        else{

            Integer count=roadnumberMapper.getRoadnumberByKeyCount(currentUserId,null);

            PageHelper.startPage(page,size);

            List<Roadnumber> roadnumberList=roadnumberMapper.getRoadnumberByKey(currentUserId,null);

            List<RoadNumberVo> roadNumberVoList=new ArrayList<>();

            for(int i=0;i<roadnumberList.size();i++){

                Roadnumber roadnumber=roadnumberList.get(i);

                RoadNumberVo roadNumberVo=new RoadNumberVo();

                roadNumberVo.setId(roadnumber.getId());
                roadNumberVo.setName(roadnumber.getName());
                roadNumberVo.setRoadNum(roadnumber.getRoadnum());

                if(roadnumber.getLonandlat()!=null&&!roadnumber.getLonandlat().equals("")){

                    String[] string=roadnumber.getLonandlat().split(",");
                    roadNumberVo.setLon(string[0]);
                    roadNumberVo.setLat(string[1]);


                }
                roadNumberVo.setType(roadnumber.getType());
                roadNumberVo.setUrl(roadnumber.getUrl());

                roadNumberVo.setMandarinWord(roadnumber.getMandarinword());
                roadNumberVo.setEnglishWord(roadnumber.getEnglishword());

                List<String> voice=audioMapper.getAudioByRoadnumberid(roadnumber.getId(),currentUserId);
                if(voice!=null&&!voice.equals("")&&!voice.equals("null")) {

                    for(int k=0;k<voice.size();k++){

                        if(voice.get(k).lastIndexOf("mandarin")!=-1){
                            roadNumberVo.setMandarinVoice(voice.get(k));
                        }

                        if(voice.get(k).lastIndexOf("english")!=-1){
                            roadNumberVo.setEnglishVoice(voice.get(k));
                        }

                        if(voice.get(k).lastIndexOf("dialect")!=-1){
                            roadNumberVo.setDialectVoice(voice.get(k));
                        }


                    }

                }



                List<String> picture=pictureMapper.getPictureByRoadnumberid(roadnumber.getId(),currentUserId);
                if(picture!=null&&!picture.equals("")){
                    roadNumberVo.setPictureUrl(picture);
                }


                roadNumberVo.setTextOfMater(roadnumber.getTextofmater());
                roadNumberVo.setSize(roadnumber.getSize());
                roadNumberVo.setYearOfEsent(roadnumber.getYearofesent());
                roadNumberVo.setMainteUnit(roadnumber.getMainteunit());
                roadNumberVo.setNumofMaint(roadnumber.getNumofmaint());
                roadNumberVo.setMaintPeriod(roadnumber.getMaintperiod());
                roadNumberVo.setMaintCost(roadnumber.getMaintcost());
                roadNumberVo.setMaintAccounts(roadnumber.getMaintaccounts());

                roadNumberVo.setSharelinks(roadnumber.getSharelinks());


                roadNumberVoList.add(roadNumberVo);
            }

            ListVO listVO=new ListVO();
            listVO.setList(roadNumberVoList);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);

        }


    }





    @ApiOperation(value = "获取路牌信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),

    })
    @GetMapping("/getListById")
    private ResultVO getListById(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Roadnumber roadnumber=roadnumberMapper.getRoadnumberById(id,currentUserId);

        RoadNumberVo roadNumberVo=new RoadNumberVo();

        roadNumberVo.setId(roadnumber.getId());
        roadNumberVo.setName(roadnumber.getName());
        roadNumberVo.setRoadNum(roadnumber.getRoadnum());
        roadNumberVo.setLonandlat(roadnumber.getLonandlat());
        roadNumberVo.setType(roadnumber.getType());
        roadNumberVo.setUrl(roadnumber.getUrl());
        List<String> voice=audioMapper.getAudioByRoadnumberid(roadnumber.getId(),currentUserId);

        if(voice!=null) {

            for(int k=0;k<voice.size();k++){

                if(voice.get(k).lastIndexOf("mandarin")!=-1){
                    roadNumberVo.setMandarinVoice(voice.get(k));
                }

                if(voice.get(k).lastIndexOf("english")!=-1){
                    roadNumberVo.setEnglishVoice(voice.get(k));
                }

                if(voice.get(k).lastIndexOf("dialect")!=-1){
                    roadNumberVo.setDialectVoice(voice.get(k));
                }


            }

        }


        List<String> picture=pictureMapper.getPictureByRoadnumberid(roadnumber.getId(),currentUserId);
        if(picture!=null&&!picture.equals("")){
            roadNumberVo.setPictureUrl(picture);
        }
        roadNumberVo.setTextOfMater(roadnumber.getTextofmater());
        roadNumberVo.setSize(roadnumber.getSize());
        roadNumberVo.setYearOfEsent(roadnumber.getYearofesent());
        roadNumberVo.setMainteUnit(roadnumber.getMainteunit());
        roadNumberVo.setNumofMaint(roadnumber.getNumofmaint());
        roadNumberVo.setMaintPeriod(roadnumber.getMaintperiod());
        roadNumberVo.setMaintCost(roadnumber.getMaintcost());
        roadNumberVo.setMaintAccounts(roadnumber.getMaintaccounts());

        return ResultVOUtil.success(roadNumberVo);


    }










    @ApiOperation(value = "添加路牌维护信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "maintcontent", value = "维护内容", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintcompany", value = "维护公司", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contactsnum", value = "联系电话", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "cost", value = "费用", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/addMaintrecordsOfRoadNum")
    private ResultVO addMaintrecordsOfRoadNum(HttpServletRequest request) throws  IOException{

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Roadnumber roadnumber=roadnumberMapper.getRoadnumberById(id,currentUserId);

        String maintcontent=request.getParameter("maintcontent");

        String maintcompany=request.getParameter("maintcompany");

        String contacts=request.getParameter("contacts");

        String contactsnum=request.getParameter("contactsnum");

        String cost=request.getParameter("cost");

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time=sdf.format(date);




        roadnumberMapper.addMaintrecordsOfRoadNum("路牌",roadnumber.getRoadnum(),maintcontent,maintcompany,contacts,contactsnum,
                cost,time,currentUserId
                );

        Maintrecords maintrecords=maintrecordsMapper.getMaxMainrecord();


          return ResultVOUtil.success(maintrecords.getId());

    }





    @ApiOperation(value = "获取经纬度", notes = "")
    @ApiImplicitParams({


    })
    @GetMapping("/getLonAndLat")
    private ResultVO getLonAndLat(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        List<String> stringList=roadnumberMapper.getLonAndLat(currentUserId);

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




    @ApiOperation(value = "根据经纬度获取信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "key", value = "经纬度", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList2")
    private RoadNumberVo getList2(HttpServletRequest request) {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String key=request.getParameter("key");

        Roadnumber  roadnumber=roadnumberMapper.getRoadNumberByLonAndLat(key,currentUserId);


            RoadNumberVo roadNumberVo=new RoadNumberVo();

            roadNumberVo.setId(roadnumber.getId());
            roadNumberVo.setName(roadnumber.getName());
        roadNumberVo.setRoadNum(roadnumber.getRoadnum());
            roadNumberVo.setLonandlat(roadnumber.getLonandlat());
            roadNumberVo.setType(roadnumber.getType());
            roadNumberVo.setUrl(roadnumber.getUrl());
            List<String> voice=audioMapper.getAudioByRoadnumberid(roadnumber.getId(),currentUserId);
        if(voice!=null) {

            for(int k=0;k<voice.size();k++){

                if(voice.get(k).lastIndexOf("mandarin")!=-1){
                    roadNumberVo.setMandarinVoice(voice.get(k));
                }

                if(voice.get(k).lastIndexOf("english")!=-1){
                    roadNumberVo.setEnglishVoice(voice.get(k));
                }

                if(voice.get(k).lastIndexOf("dialect")!=-1){
                    roadNumberVo.setDialectVoice(voice.get(k));
                }


            }

        }



            List<String> picture=pictureMapper.getPictureByRoadnumberid(roadnumber.getId(),currentUserId);
            if(picture!=null&&!picture.equals("")){
                roadNumberVo.setPictureUrl(picture);
            }


            roadNumberVo.setTextOfMater(roadnumber.getTextofmater());
            roadNumberVo.setSize(roadnumber.getSize());
            roadNumberVo.setYearOfEsent(roadnumber.getYearofesent());
            roadNumberVo.setMainteUnit(roadnumber.getMainteunit());
            roadNumberVo.setNumofMaint(roadnumber.getNumofmaint());
            roadNumberVo.setMaintPeriod(roadnumber.getMaintperiod());
            roadNumberVo.setMaintCost(roadnumber.getMaintcost());
            roadNumberVo.setMaintAccounts(roadnumber.getMaintaccounts());



        return roadNumberVo;

    }





    @ApiOperation(value = "通过Url来获取信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "url", value = "url", required = false, dataType = "integer", paramType = "query"),
    })
    @GetMapping("/getRoadNumberByUrl")
    private RoadNumberVo getRoadNumberByUrl(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String url=request.getParameter("url");

        Roadnumber roadnumber=roadnumberMapper.getRoadNumberByUrl(url,currentUserId);

        RoadNumberVo roadNumberVo=new RoadNumberVo();

        roadNumberVo.setId(roadnumber.getId());
        roadNumberVo.setName(roadnumber.getName());
        roadNumberVo.setRoadNum(roadnumber.getRoadnum());
        roadNumberVo.setLonandlat(roadnumber.getLonandlat());
        roadNumberVo.setType(roadnumber.getType());
        roadNumberVo.setUrl(roadnumber.getUrl());
        List<String> voice=audioMapper.getAudioByRoadnumberid(roadnumber.getId(),currentUserId);
        if(voice!=null) {

            for(int k=0;k<voice.size();k++){

                if(voice.get(k).lastIndexOf("mandarin")!=-1){
                    roadNumberVo.setMandarinVoice(voice.get(k));
                }

                if(voice.get(k).lastIndexOf("english")!=-1){
                    roadNumberVo.setEnglishVoice(voice.get(k));
                }

                if(voice.get(k).lastIndexOf("dialect")!=-1){
                    roadNumberVo.setDialectVoice(voice.get(k));
                }


            }

        }

        List<String> picture=pictureMapper.getPictureByRoadnumberid(roadnumber.getId(),currentUserId);
        if(picture!=null&&!picture.equals("")){
            roadNumberVo.setPictureUrl(picture);
        }
        roadNumberVo.setTextOfMater(roadnumber.getTextofmater());
        roadNumberVo.setSize(roadnumber.getSize());
        roadNumberVo.setYearOfEsent(roadnumber.getYearofesent());
        roadNumberVo.setMainteUnit(roadnumber.getMainteunit());
        roadNumberVo.setNumofMaint(roadnumber.getNumofmaint());
        roadNumberVo.setMaintPeriod(roadnumber.getMaintperiod());
        roadNumberVo.setMaintCost(roadnumber.getMaintcost());
        roadNumberVo.setMaintAccounts(roadnumber.getMaintaccounts());

        return roadNumberVo;

    }



    @ApiOperation(value = "获取道路统计数量" , notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "条数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getRoadCountByName")
    private ResultVO getRoadCountByName(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String name=request.getParameter("name");



        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer  size=Integer.valueOf(request.getParameter("size"));

        if(name!=null){

            Integer count=countMapper.getCountOfAll("%"+name+"%","roadnumber",currentUserId);
            PageHelper.startPage(page,size);

            List<Count>  plateNumberCount=countMapper.getPlateNumberCount("%"+name+"%","roadnumber",currentUserId);

            ListVO listVO=new ListVO();

            listVO.setList(plateNumberCount);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);
        }
        else{

            Integer count=countMapper.getCountOfAll(null,"roadnumber",currentUserId);

            PageHelper.startPage(page,size);

            List<Count>  plateNumberCount=countMapper.getPlateNumberCount(null,"roadnumber",currentUserId);

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


        List<RoadNumberNaUr> roadnumberList=roadNumberNaUrMapper.getNameAndUrl(currentUserId);

        return ResultVOUtil.success(roadnumberList);


    }




    }
