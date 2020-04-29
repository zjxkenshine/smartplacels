package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Maintrecords;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.pojo.Villagenumber;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.two.VillagenumberNaUr;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.LonAndLatVo;
import com.csdl.smartplacenew.vo.ResultVO;
import com.csdl.smartplacenew.vo.VillageNumberVo;
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

@Api(description = "村牌管理接口")
@RestController
@RequestMapping("/VillageNumber")
public class VillageNumberController {

    @Resource
    VillagenumberMapper villagenumberMapper;

    @Resource
    PictureMapper pictureMapper;

    @Resource
    AudioMapper audioMapper;

    @Resource
    CountMapper countMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    VillagNumberNaUrMapper villagNumberNaUrMapper;

    @Resource
    MaintrecordsMapper maintrecordsMapper;

    @Resource
    FeedbackMapper feedbackMapper;


    @ApiOperation(value = "添加村牌", notes = "")
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

            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "englishword", value = "英语内容", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name=request.getParameter("name");

        String lonandlat=null;
          if(request.getParameter("lon")!=null&&!request.getParameter("lon").equals("")) {
              Double lon = Double.valueOf(request.getParameter("lon"));
              Double lat = Double.valueOf(request.getParameter("lat"));
              String londecimalFormat1 = new DecimalFormat("#,##0.000000").format(lon);
              String latdecimalFormat1 = new DecimalFormat("#,##0.000000").format(lat);
               lonandlat = londecimalFormat1 + "," + latdecimalFormat1;
          }
          else{

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


        Integer villagenumberMapperMaxIdByAll=villagenumberMapper.getMaxIdByAll(currentUserId);

        if(villagenumberMapperMaxIdByAll==null){

            villagenumberMapperMaxIdByAll=1;

        }

        if(villagenumberMapperMaxIdByAll>0){

            villagenumberMapperMaxIdByAll=villagenumberMapperMaxIdByAll+1;

        }

        villagenumberMapper.addVillagenumber(name,lonandlat,type,currentUserId+"/"+villagenumberMapperMaxIdByAll,imgVirtualPath,1,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,currentUserId,sharelinks,englishword
                );

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberByMaxId(currentUserId);


        List<Villagenumber> villagenumberList=villagenumberMapper.getVilageNumByName(name,currentUserId);

        if(villagenumberList.size()==1){

            villagenumberMapper.addCountOfVilageNum(name,villagenumberList.size(),0,"villagenumber",1,0,currentUserId);
        }

        if(villagenumberList.size()>1){

            countMapper.updatePlateCount2(name,"villagenumber",currentUserId);

        }

        return ResultVOUtil.success(villagenumber.getId());


    }



    @ApiOperation(value = "添加村牌", notes = "")
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

            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "englishword", value = "英语内容", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "mandarin", value = "中文内容", required = false, dataType = "integer", paramType = "query"),
    })
    @PostMapping("/addAndMandarin")
    private ResultVO addAndMandarin( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name=request.getParameter("name");

        String lonandlat=null;
        if(request.getParameter("lon")!=null&&!request.getParameter("lon").equals("")) {
            Double lon = Double.valueOf(request.getParameter("lon"));
            Double lat = Double.valueOf(request.getParameter("lat"));
            String londecimalFormat1 = new DecimalFormat("#,##0.000000").format(lon);
            String latdecimalFormat1 = new DecimalFormat("#,##0.000000").format(lat);
            lonandlat = londecimalFormat1 + "," + latdecimalFormat1;
        }
        else{

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

        String mandarin=request.getParameter("mandarin");


        String imgVirtualPath=null;


        Integer villagenumberMapperMaxIdByAll=villagenumberMapper.getMaxIdByAll(currentUserId);

        if(villagenumberMapperMaxIdByAll==null){

            villagenumberMapperMaxIdByAll=1;

        }

        if(villagenumberMapperMaxIdByAll>0){

            villagenumberMapperMaxIdByAll=villagenumberMapperMaxIdByAll+1;

        }

        villagenumberMapper.addVillagenumberMandarin(name,lonandlat,type,currentUserId+"/"+villagenumberMapperMaxIdByAll,imgVirtualPath,1,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,currentUserId,sharelinks,englishword,mandarin
        );

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberByMaxId(currentUserId);


        List<Villagenumber> villagenumberList=villagenumberMapper.getVilageNumByName(name,currentUserId);

        if(villagenumberList.size()==1){

            villagenumberMapper.addCountOfVilageNum(name,villagenumberList.size(),0,"villagenumber",1,0,currentUserId);
        }

        if(villagenumberList.size()>1){

            countMapper.updatePlateCount2(name,"villagenumber",currentUserId);

        }

        return ResultVOUtil.success(villagenumber.getId());


    }




    @ApiOperation(value = "删除村牌", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "村牌id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del(HttpServletRequest request) {

        Integer id=Integer.valueOf(request.getParameter("id"));
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


       Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,currentUserId);


        List<Villagenumber> villagenumberList=villagenumberMapper.getVilageNumByName(villagenumber.getName(),currentUserId);

        if(villagenumberList.size()==1){

            countMapper.updatePlateCount4(villagenumber.getName(),"villagenumber",currentUserId);
        }

        if(villagenumberList.size()>1){

            countMapper.updatePlateCount3(villagenumber.getName(),"villagenumber",currentUserId);

        }

        villagenumberMapper.updateVillagenumberStatus(id,currentUserId);

        return  ResultVOUtil.success("成功删除:"+villagenumber);


    }






    @ApiOperation(value = "修改村牌", notes = "")
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


            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "englishword", value = "英语内容", required = false, dataType = "integer", paramType = "query"),
    })
    @PostMapping("/update")
    private ResultVO update( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,currentUserId);

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
        String englishword=null;

        String sharelinks=request.getParameter("sharelinks");

        if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){
            name=request.getParameter("name");
        }else{
            name=villagenumber.getName();
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
            type=villagenumber.getType();
        }



        if(request.getParameter("textofmater")!=null&&!request.getParameter("textofmater").equals("")){
            textofmater=request.getParameter("textofmater");

        }else{
            textofmater=villagenumber.getTextofmater();
        }


        if(request.getParameter("size")!=null&&!request.getParameter("size").equals("")){
            size=request.getParameter("size");

        }else{
            size=villagenumber.getSize();
        }


        if(request.getParameter("yearofesent")!=null&&!request.getParameter("yearofesent").equals("")){
            yearofesent=request.getParameter("yearofesent");

        }else{
            yearofesent=villagenumber.getYearofesent();
        }


        if(request.getParameter("mainteunit")!=null&&!request.getParameter("mainteunit").equals("")){
            mainteunit=request.getParameter("mainteunit");

        }else{
            mainteunit=villagenumber.getMainteunit();
        }



        if(request.getParameter("numofmaint")!=null&&!request.getParameter("numofmaint").equals("")){
            numofmaint=request.getParameter("numofmaint");

        }else{
            numofmaint=villagenumber.getNumofmaint();
        }



        if(request.getParameter("maintperiod")!=null&&!request.getParameter("maintperiod").equals("")){
            maintperiod=request.getParameter("maintperiod");

        }else{
            maintperiod=villagenumber.getMaintperiod();
        }


        if(request.getParameter("maintcost")!=null&&!request.getParameter("maintcost").equals("")){
            maintcost=request.getParameter("maintcost");

        }else{
            maintcost=villagenumber.getMaintcost();
        }


        if(request.getParameter("maintaccounts")!=null&&!request.getParameter("maintaccounts").equals("")){
            maintaccounts=request.getParameter("maintaccounts");

        }else{
            maintaccounts=villagenumber.getMaintaccounts();
        }


        if(request.getParameter("englishword")!=null&&!request.getParameter("englishword").equals("")){
            englishword=request.getParameter("englishword");

        }else{
            englishword=villagenumber.getEnglishword();
        }

        String imgVirtualPath=null;

        imgVirtualPath=villagenumber.getVoiceurl();

        villagenumberMapper.updateVillagenumberById(name,lonandlat,type,villagenumber.getUrl(),imgVirtualPath,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,sharelinks,englishword,id,currentUserId);

        countMapper.updateCountName(name,villagenumber.getName(),"villagenumber",currentUserId);

        feedbackMapper.updateFeddBackFeedbackpath(name,villagenumber.getName(),"村牌管理",currentUserId);

        Villagenumber villagenumber1=villagenumberMapper.getVillagenumberById(id,currentUserId);

        return  ResultVOUtil.success("成功更新:"+villagenumber1);

    }



    @ApiOperation(value = "修改村牌", notes = "")
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


            @ApiImplicitParam(name = "sharelinks", value = "分享链接", required = true, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "englishword", value = "英语内容", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "mandarin", value = "中文内容", required = false, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/updateAndMandarin")
    private ResultVO updateAndMandarin( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,currentUserId);

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
        String englishword=null;

        String mandarin=null;

        String sharelinks=request.getParameter("sharelinks");

        if(request.getParameter("name")!=null&&!request.getParameter("name").equals("")){
            name=request.getParameter("name");
        }else{
            name=villagenumber.getName();
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
            type=villagenumber.getType();
        }



        if(request.getParameter("textofmater")!=null&&!request.getParameter("textofmater").equals("")){
            textofmater=request.getParameter("textofmater");

        }else{
            textofmater=villagenumber.getTextofmater();
        }


        if(request.getParameter("size")!=null&&!request.getParameter("size").equals("")){
            size=request.getParameter("size");

        }else{
            size=villagenumber.getSize();
        }


        if(request.getParameter("yearofesent")!=null&&!request.getParameter("yearofesent").equals("")){
            yearofesent=request.getParameter("yearofesent");

        }else{
            yearofesent=villagenumber.getYearofesent();
        }


        if(request.getParameter("mainteunit")!=null&&!request.getParameter("mainteunit").equals("")){
            mainteunit=request.getParameter("mainteunit");

        }else{
            mainteunit=villagenumber.getMainteunit();
        }



        if(request.getParameter("numofmaint")!=null&&!request.getParameter("numofmaint").equals("")){
            numofmaint=request.getParameter("numofmaint");

        }else{
            numofmaint=villagenumber.getNumofmaint();
        }



        if(request.getParameter("maintperiod")!=null&&!request.getParameter("maintperiod").equals("")){
            maintperiod=request.getParameter("maintperiod");

        }else{
            maintperiod=villagenumber.getMaintperiod();
        }


        if(request.getParameter("maintcost")!=null&&!request.getParameter("maintcost").equals("")){
            maintcost=request.getParameter("maintcost");

        }else{
            maintcost=villagenumber.getMaintcost();
        }


        if(request.getParameter("maintaccounts")!=null&&!request.getParameter("maintaccounts").equals("")){
            maintaccounts=request.getParameter("maintaccounts");

        }else{
            maintaccounts=villagenumber.getMaintaccounts();
        }


        if(request.getParameter("englishword")!=null&&!request.getParameter("englishword").equals("")){
            englishword=request.getParameter("englishword");

        }else{
            englishword=villagenumber.getEnglishword();
        }


        if(request.getParameter("mandarin")!=null&&!request.getParameter("mandarin").equals("")){

            mandarin=request.getParameter("mandarin");

        }else{
            mandarin=villagenumber.getMandarinword();

        }

        String imgVirtualPath=null;

        imgVirtualPath=villagenumber.getVoiceurl();

        villagenumberMapper.updateVillagenumberByIdAndMandarin(name,lonandlat,type,villagenumber.getUrl(),imgVirtualPath,
                textofmater,size,yearofesent,mainteunit,numofmaint,maintperiod,maintcost,maintaccounts,sharelinks,englishword,mandarin,id,currentUserId);

        countMapper.updateCountName(name,villagenumber.getName(),"villagenumber",currentUserId);

        feedbackMapper.updateFeddBackFeedbackpath(name,villagenumber.getName(),"村牌管理",currentUserId);

        Villagenumber villagenumber1=villagenumberMapper.getVillagenumberById(id,currentUserId);

        return  ResultVOUtil.success("成功更新:"+villagenumber1);

    }




    @ApiOperation(value = "获取村牌信息", notes = "")
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

            Integer count=villagenumberMapper.getVillagenumberByKeyCount(currentUserId,"%"+key+"%");

            PageHelper.startPage(page,size);

            List<Villagenumber> villagenumberList=villagenumberMapper.getVillagenumberByKey(currentUserId,"%"+key+"%");

            List<VillageNumberVo> villageNumberVoList=new ArrayList<>();

            for(int i=0;i<villagenumberList.size();i++){

                Villagenumber villagenumber=villagenumberList.get(i);

                VillageNumberVo villagenumberVo=new VillageNumberVo();

                villagenumberVo.setId(villagenumber.getId());
                villagenumberVo.setName(villagenumber.getName());

                if(villagenumber.getLonandlat()!=null&&!villagenumber.getLonandlat().equals("")){

                    String[] strings=villagenumber.getLonandlat().split(",");

                    villagenumberVo.setLon(strings[0]);
                    villagenumberVo.setLat(strings[1]);

                }

                villagenumberVo.setType(villagenumber.getType());
                villagenumberVo.setUrl(villagenumber.getUrl());

                villagenumberVo.setMandarinWord(villagenumber.getMandarinword());
                villagenumberVo.setEnglishWord(villagenumber.getEnglishword());

                List<String> stringList=audioMapper.getAudioByVillagenumberid(villagenumber.getId(),currentUserId);

                if(stringList!=null&&!stringList.equals("")&&!stringList.equals("null")){

                    for(int k=0;k<stringList.size();k++){

                        if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                            villagenumberVo.setMandarinVoice(stringList.get(k));
                        }


                        if(stringList.get(k).lastIndexOf("english")!=-1){
                            villagenumberVo.setEnglishVoice(stringList.get(k));
                        }

                        if(stringList.get(k).lastIndexOf("dialect")!=-1){
                            villagenumberVo.setDialectVoice(stringList.get(k));
                        }



                    }
                }


                List<String> picture=pictureMapper.getPictureByVillagenumberid(villagenumber.getId(),currentUserId);

                if(picture!=null&&!picture.equals("")){

                    villagenumberVo.setPictureUrl(picture);
                }

                villagenumberVo.setTextOfMater(villagenumber.getTextofmater());
                villagenumberVo.setSize(villagenumber.getSize());
                villagenumberVo.setYearOfEsent(villagenumber.getYearofesent());
                villagenumberVo.setMainteUnit(villagenumber.getMainteunit());
                villagenumberVo.setNumofMaint(villagenumber.getNumofmaint());
                villagenumberVo.setMaintPeriod(villagenumber.getMaintperiod());
                villagenumberVo.setMaintCost(villagenumber.getMaintcost());
                villagenumberVo.setMaintAccounts(villagenumber.getMaintaccounts());
                villagenumberVo.setSharelinks(villagenumber.getSharelinks());

                villageNumberVoList.add(villagenumberVo);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(villageNumberVoList);

            return ResultVOUtil.success(listVO);
        }
           else{

            Integer count=villagenumberMapper.getVillagenumberByKeyCount(currentUserId,null);

            PageHelper.startPage(page,size);

            List<Villagenumber> villagenumberList=villagenumberMapper.getVillagenumberByKey(currentUserId,null);

            List<VillageNumberVo> villageNumberVoList=new ArrayList<>();

            for(int i=0;i<villagenumberList.size();i++){

                Villagenumber villagenumber=villagenumberList.get(i);

                VillageNumberVo villagenumberVo=new VillageNumberVo();

                villagenumberVo.setId(villagenumber.getId());
                villagenumberVo.setName(villagenumber.getName());

                if(villagenumber.getLonandlat()!=null&&!villagenumber.getLonandlat().equals("")){

                    String[] strings=villagenumber.getLonandlat().split(",");

                    villagenumberVo.setLon(strings[0]);
                    villagenumberVo.setLat(strings[1]);

                }

                villagenumberVo.setType(villagenumber.getType());
                villagenumberVo.setUrl(villagenumber.getUrl());

                villagenumberVo.setMandarinWord(villagenumber.getMandarinword());
                villagenumberVo.setEnglishWord(villagenumber.getEnglishword());

                List<String> stringList=audioMapper.getAudioByVillagenumberid(villagenumber.getId(),currentUserId);

                if(stringList!=null&&!stringList.equals("")&&!stringList.equals("null")){

                    for(int k=0;k<stringList.size();k++){

                        if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                            villagenumberVo.setMandarinVoice(stringList.get(k));
                        }


                        if(stringList.get(k).lastIndexOf("english")!=-1){
                            villagenumberVo.setEnglishVoice(stringList.get(k));
                        }

                        if(stringList.get(k).lastIndexOf("dialect")!=-1){
                            villagenumberVo.setDialectVoice(stringList.get(k));
                        }


                    }
                }

                List<String> picture=pictureMapper.getPictureByVillagenumberid(villagenumber.getId(),currentUserId);

                if(picture!=null&&!picture.equals("")){

                    villagenumberVo.setPictureUrl(picture);
                }

                villagenumberVo.setTextOfMater(villagenumber.getTextofmater());
                villagenumberVo.setSize(villagenumber.getSize());
                villagenumberVo.setYearOfEsent(villagenumber.getYearofesent());
                villagenumberVo.setMainteUnit(villagenumber.getMainteunit());
                villagenumberVo.setNumofMaint(villagenumber.getNumofmaint());
                villagenumberVo.setMaintPeriod(villagenumber.getMaintperiod());
                villagenumberVo.setMaintCost(villagenumber.getMaintcost());
                villagenumberVo.setMaintAccounts(villagenumber.getMaintaccounts());
                villagenumberVo.setSharelinks(villagenumber.getSharelinks());

                villageNumberVoList.add(villagenumberVo);

            }

            ListVO listVO=new ListVO();

            listVO.setList(villageNumberVoList);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);
        }

    }





    @ApiOperation(value = "获取村牌信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),

    })
    @GetMapping("/getListById")
    private ResultVO getListById(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,currentUserId);

        VillageNumberVo villagenumberVo=new VillageNumberVo();

        villagenumberVo.setId(villagenumber.getId());
        villagenumberVo.setName(villagenumber.getName());
        villagenumberVo.setLonandlat(villagenumber.getLonandlat());
        villagenumberVo.setType(villagenumber.getType());
        villagenumberVo.setUrl(villagenumber.getUrl());
        List<String> stringList=audioMapper.getAudioByVillagenumberid(villagenumber.getId(),currentUserId);
        if(stringList!=null){

            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    villagenumberVo.setMandarinVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("english")!=-1){
                    villagenumberVo.setEnglishVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    villagenumberVo.setMandarinVoice(stringList.get(k));
                }

            }
        }


        List<String> picture=pictureMapper.getPictureByVillagenumberid(villagenumber.getId(),currentUserId);

        if(picture!=null&&!picture.equals("")){

            villagenumberVo.setPictureUrl(picture);
        }

        villagenumberVo.setTextOfMater(villagenumber.getTextofmater());
        villagenumberVo.setSize(villagenumber.getSize());
        villagenumberVo.setYearOfEsent(villagenumber.getYearofesent());
        villagenumberVo.setMainteUnit(villagenumber.getMainteunit());
        villagenumberVo.setNumofMaint(villagenumber.getNumofmaint());
        villagenumberVo.setMaintPeriod(villagenumber.getMaintperiod());
        villagenumberVo.setMaintCost(villagenumber.getMaintcost());
        villagenumberVo.setMaintAccounts(villagenumber.getMaintaccounts());

        return ResultVOUtil.success(villagenumberVo);


    }






    @ApiOperation(value = "添加村牌维护信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "id", value = "id", required = false, dataType = "integer", paramType = "query"),

            @ApiImplicitParam(name = "maintcontent", value = "维护内容", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "maintcompany", value = "维护公司", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contacts", value = "联系人", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "contactsnum", value = "联系电话", required = false, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "cost", value = "费用", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/addMaintOfVillage")
    private ResultVO addMaintOfVillage(HttpServletRequest request) throws  IOException{
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Villagenumber villagenumber=villagenumberMapper.getVillagenumberById(id,currentUserId);

        String maintcontent=request.getParameter("maintcontent");
        String maintcompany=request.getParameter("maintcompany");
        String contacts=request.getParameter("contacts");
        String contactsnum=request.getParameter("contactsnum");
        String cost=request.getParameter("cost");

        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time=sdf.format(date);



        villagenumberMapper.addMaintOfVillage(
                "村牌",null,maintcontent,maintcompany,contacts,contactsnum,cost,time,currentUserId);



        Maintrecords maintrecords=maintrecordsMapper.getMaxMainrecord();

        return ResultVOUtil.success(maintrecords.getId());

    }










    @ApiOperation(value = "获取所有经纬度", notes = "")
    @ApiImplicitParams({

    })
    @GetMapping("/getLonAndLat")
    private ResultVO getLonAndLat(HttpServletRequest request) {
        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();



        List<String> stringList=villagenumberMapper.getLonAndLat(currentUserId);

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





    @ApiOperation(value = "根据经纬度获取所有信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "key", value = "经纬度", required = false, dataType = "integer", paramType = "query"),

    })
    @GetMapping("/getList2")
    private VillageNumberVo getList2(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String key=request.getParameter("key");

        Villagenumber  villagenumber=villagenumberMapper.getVillageByLonAndLat(key,currentUserId);



            VillageNumberVo villagenumberVo=new VillageNumberVo();

            villagenumberVo.setId(villagenumber.getId());
            villagenumberVo.setName(villagenumber.getName());
            villagenumberVo.setLonandlat(villagenumber.getLonandlat());

            villagenumberVo.setType(villagenumber.getType());
            villagenumberVo.setUrl(villagenumber.getUrl());
            List<String> stringList=audioMapper.getAudioByVillagenumberid(villagenumber.getId(),currentUserId);
        if(stringList!=null){

            for(int k=0;k<stringList.size();k++){

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    villagenumberVo.setMandarinVoice(stringList.get(k));
                }


                if(stringList.get(k).lastIndexOf("english")!=-1){
                    villagenumberVo.setEnglishVoice(stringList.get(k));
                }

                if(stringList.get(k).lastIndexOf("mandarin")!=-1){
                    villagenumberVo.setMandarinVoice(stringList.get(k));
                }


            }
        }


            List<String> picture=pictureMapper.getPictureByVillagenumberid(villagenumber.getId(),currentUserId);

            if(picture!=null&&!picture.equals("")){

                villagenumberVo.setPictureUrl(picture);
            }

            villagenumberVo.setTextOfMater(villagenumber.getTextofmater());
            villagenumberVo.setSize(villagenumber.getSize());
            villagenumberVo.setYearOfEsent(villagenumber.getYearofesent());
            villagenumberVo.setMainteUnit(villagenumber.getMainteunit());
            villagenumberVo.setNumofMaint(villagenumber.getNumofmaint());
            villagenumberVo.setMaintPeriod(villagenumber.getMaintperiod());
            villagenumberVo.setMaintCost(villagenumber.getMaintcost());
            villagenumberVo.setMaintAccounts(villagenumber.getMaintaccounts());



        return villagenumberVo;
    }






    @ApiOperation(value = "通过Url来获取信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "url", value = "url", required = false, dataType = "integer", paramType = "query"),
    })
    @GetMapping("/getVillageNumberUrl")
    private VillageNumberVo getVillageNumberUrl(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String url=request.getParameter("url");

        Villagenumber villagenumber=villagenumberMapper.getVillageNumByUrl(url,currentUserId);

        VillageNumberVo villagenumberVo=new VillageNumberVo();

        villagenumberVo.setId(villagenumber.getId());
        villagenumberVo.setName(villagenumber.getName());
        villagenumberVo.setLonandlat(villagenumber.getLonandlat());
        villagenumberVo.setType(villagenumber.getType());
        villagenumberVo.setUrl(villagenumber.getUrl());



        List<String> picture=pictureMapper.getPictureByVillagenumberid(villagenumber.getId(),currentUserId);

        if(picture!=null&&!picture.equals("")){

            villagenumberVo.setPictureUrl(picture);
        }

        villagenumberVo.setTextOfMater(villagenumber.getTextofmater());
        villagenumberVo.setSize(villagenumber.getSize());
        villagenumberVo.setYearOfEsent(villagenumber.getYearofesent());
        villagenumberVo.setMainteUnit(villagenumber.getMainteunit());
        villagenumberVo.setNumofMaint(villagenumber.getNumofmaint());
        villagenumberVo.setMaintPeriod(villagenumber.getMaintperiod());
        villagenumberVo.setMaintCost(villagenumber.getMaintcost());
        villagenumberVo.setMaintAccounts(villagenumber.getMaintaccounts());

        return villagenumberVo;


             }




    @ApiOperation(value = "获取村牌统计数量" , notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "条数", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),
    })
    @GetMapping("/getVillageNumberCountByName")
    private ResultVO getVillageNumberCountByName(HttpServletRequest request) {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        String name=request.getParameter("name");

        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer  size=Integer.valueOf(request.getParameter("size"));


        if(name!=null){

            Integer count=countMapper.getCountOfAll("%"+name+"%","villagenumber",currentUserId);
            PageHelper.startPage(page,size);

            List<Count>  plateNumberCount=countMapper.getPlateNumberCount("%"+name+"%","villagenumber",currentUserId);

            ListVO listVO=new ListVO();

            listVO.setList(plateNumberCount);
            listVO.setCount(count);

            return ResultVOUtil.success(listVO);
        }
        else{
            Integer count=countMapper.getCountOfAll(null,"villagenumber",currentUserId);
            PageHelper.startPage(page,size);
            List<Count>  plateNumberCount=countMapper.getPlateNumberCount(null,"villagenumber",currentUserId);

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


        List<VillagenumberNaUr> villagenumberList=villagNumberNaUrMapper.getNameAndUrl(currentUserId);


        return ResultVOUtil.success(villagenumberList);


    }

}

