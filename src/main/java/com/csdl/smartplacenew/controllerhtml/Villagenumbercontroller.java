package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Cultuinsurunit;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Villagenumber;
import com.csdl.smartplacenew.pojo.VillagenumberSY;
import com.csdl.smartplacenew.service.Count2Service;
import com.csdl.smartplacenew.vo.PlateNumberVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/Villagenumber")
public class Villagenumbercontroller {

    @Resource
    AudioMapper audioMapper;

    @Resource
    PlatenumberMapper platenumberMapper;

    @Resource
    RoadMapper roadMapper;

    @Resource
    RoadnumberMapper roadnumberMapper;

    @Resource
    ScenicspotMapper scenicspotMapper;

    @Resource
    VillagenumberMapper  villagenumberMapper;

    @Resource
    PictureMapper pictureMapper;

    @Resource
    CountMapper countMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    private VillagenumberSYMapper villagenumberSYMapper;

    @Resource
    CultuinsurunitMapper cultuinsurunitMapper;

    @Resource
    Count2Service count2Service;


    @GetMapping(value = "/tt")
    public void tt(HttpServletRequest request, HttpServletResponse response,
                   @RequestParam(value = "url",required = false) String url) throws Exception {

        HttpSession session=request.getSession();


        String url1=(String)session.getAttribute("Villagenumberurl") ;


        response.sendRedirect("hh?url="+url1);

    }


    @GetMapping(value = "/dd")
    public String dd(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "url",required = false) String url) {

        HttpSession session=request.getSession();

        String name= (String) session.getAttribute("VillageName");

        request.setAttribute("villagenameKK",name);


        VillagenumberSY villagenumberSY=villagenumberSYMapper.getVillagenumberSYByName("%"+name+"%",15);

        if(villagenumberSY!=null) {
            if (villagenumberSY.getMinsuwenhua()!= null && !villagenumberSY.getMinsuwenhua().equals("")) {
                request.setAttribute("villagenumberSYMinsuwenhuaText", villagenumberSY.getMinsuwenhua());

            }

            if (villagenumberSY.getChuantongjianzhu()!= null && !villagenumberSY.getChuantongjianzhu().equals("")) {
                request.setAttribute("villagenumberSYChuantongjianzhuText", villagenumberSY.getChuantongjianzhu());

            }

            if (villagenumberSY.getFeiyiwenhua()!= null && !villagenumberSY.getFeiyiwenhua().equals("")) {
                request.setAttribute("villagenumberSYFeiyiwenhuyaText", villagenumberSY.getFeiyiwenhua());

            }

            String cultuname = name.substring(0, 2);

            Cultuinsurunit cultuinsurunit = cultuinsurunitMapper.getCultuinsurunit(cultuname + "%", 15);

            if (cultuinsurunit != null) {

                request.setAttribute("cultuinsurunitId", cultuinsurunit.getId());
            }
        }

        return "dmwh";

    }





    @GetMapping(value = "/kk")
    public String gg(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "url",required = false) String url) {

        HttpSession session=request.getSession();

        String name= (String) session.getAttribute("VillageName");

        request.setAttribute("villagenameKK",name);

        String afterName=name.replaceAll("\\d+", "");

        VillagenumberSY villagenumberSY=villagenumberSYMapper.getVillagenumberSYByName(afterName,15);

        if(villagenumberSY!=null) {
            request.setAttribute("villagenumberSYText", villagenumberSY.getText());
        }
        return "kcly";

    }



    @GetMapping(value="/xiuxianzhilv")
    public String xixianzhilv(HttpServletRequest request, HttpServletResponse response) throws IOException
    {


        String pathname1 = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+4+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        String line1=null;
        String jsonString1 = "";
        BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(pathname1), "utf-8"));
        //网友推荐更加简洁的写法
        while ((line1 = br1.readLine()) != null) {
            // 一次读入一行数据
            jsonString1 += line1;
        }
        request.setAttribute("xiuxianxianlu",jsonString1);



        String pathname2 = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+5+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        String line2=null;
        String jsonString2 = "";
        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(pathname2), "utf-8"));
        //网友推荐更加简洁的写法
        while ((line2 = br2.readLine()) != null) {
            // 一次读入一行数据
            jsonString2 += line2;
        }
        request.setAttribute("songyangsongsezhilv",jsonString2);



        String pathname3 = ConfigBean.sourceRealPath+"txt"+ File.separator+"Leisuretrip"+File.separator+"Leisuretrip"+8+".txt";// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        String line3=null;
        String jsonString3 = "";
        BufferedReader br3 = new BufferedReader(new InputStreamReader(new FileInputStream(pathname3), "utf-8"));

        while ((line3 = br3.readLine()) != null) {
            // 一次读入一行数据
            jsonString3 += line3;
        }
        request.setAttribute("xiangcunzhilv",jsonString3);



        return "xiuxianzhilv";

    }



    @GetMapping(value="/yingxiangdiming")
    public String yingxiangdiming(HttpServletRequest request, HttpServletResponse response) throws IOException
    {

        HttpSession session=request.getSession();

        String name= (String) session.getAttribute("VillageName");

        request.setAttribute("villagenameKK",name);

        VillagenumberSY villagenumberSY=villagenumberSYMapper.getVillagenumberSYByName(name,15);
        if(villagenumberSY!=null){
        if(villagenumberSY.getPictureurl()!=null) {

            request.setAttribute("sanweishijing", villagenumberSY.getPictureurl());
        }}

        return "yxdm";

    }



    @GetMapping(value = "/hh")
    public String hh(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "url",required = false) String url) throws ParseException {


        String url1=request.getParameter("url");


        HttpSession session=request.getSession();
        session.setAttribute("Villagenumberurl",url1);

        String[]  strings=url1.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);

        request.setAttribute("VillageNumbercurrentUserId",currentUserId);

        session.setAttribute("VillageNumbercurrentUserIdFreed",currentUserId);

        //添加总访问量
        count2Service.addCount(String.valueOf(currentUserId));

        if(currentUserId==15){

            Integer id=villagenumberMapper.getOrderVillageNumberByUseriId(url1,currentUserId);

            Integer maxId=villagenumberMapper.getMaxOrderVillageNumberByUseriId(currentUserId);

            Integer minId=villagenumberMapper.getMinOrderVillageNumberByUseriId(
                    currentUserId
            );

            if(id!=null){
            if(id.equals(minId)){

                Integer leftId=id+1;

                String leftnumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(leftId,currentUserId);


                String rightplatenumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(id,currentUserId);


                request.setAttribute("villagenumberleftnumberUrl",rightplatenumberUrl);

                request.setAttribute("villagenumberrightplatenumberUrl",leftnumberUrl);

                request.setAttribute("villagenumberMax","villagenumberMax");


            }


            if(id.equals(maxId))

            {


                String leftnumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(id,currentUserId);

                Integer trueNextId=id-1;

                String rightplatenumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(trueNextId,currentUserId);


                request.setAttribute("villagenumberleftnumberUrl",rightplatenumberUrl);

                request.setAttribute("villagenumberrightplatenumberUrl",leftnumberUrl);

                request.setAttribute("villagenumberMin","villagenumberMin");
            }


            if (!id.equals(minId) &&!id.equals(maxId)){

                Integer leftid=id+1;

                String leftnumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(leftid,currentUserId);

                if(leftnumberUrl==null){
                    leftnumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(leftid+1,currentUserId);
                }

                Integer trueNextId=id-1;

                String rightplatenumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(trueNextId,currentUserId);

                if(rightplatenumberUrl==null){

                    rightplatenumberUrl=villagenumberMapper.getUrlByOrderindexVillageNumberByUserId(trueNextId-1,currentUserId);
                }

                request.setAttribute("villagenumberleftnumberUrl",rightplatenumberUrl);

                request.setAttribute("villagenumberrightplatenumberUrl",leftnumberUrl);

            }


        }}



        else{

        Integer id=villagenumberMapper.getIdByUrl(url1,currentUserId);

        Integer maxId=villagenumberMapper.getMaxId(currentUserId);

        Integer minId=villagenumberMapper.getMinId(
                currentUserId
        );

        if(id.equals(minId)){

            Integer leftId=id+1;

            String leftnumberUrl=villagenumberMapper.getUrlById(leftId,currentUserId);


            String rightplatenumberUrl=villagenumberMapper.getUrlById(id,currentUserId);


            request.setAttribute("villagenumberleftnumberUrl",rightplatenumberUrl);

            request.setAttribute("villagenumberrightplatenumberUrl",leftnumberUrl);

            request.setAttribute("villagenumberMax","villagenumberMax");


        }


        if(id.equals(maxId))

        {


            String leftnumberUrl=villagenumberMapper.getUrlById(id,currentUserId);

            Integer trueNextId=id-1;

            String rightplatenumberUrl=villagenumberMapper.getUrlById(trueNextId,currentUserId);


            request.setAttribute("villagenumberleftnumberUrl",rightplatenumberUrl);

            request.setAttribute("villagenumberrightplatenumberUrl",leftnumberUrl);

            request.setAttribute("villagenumberMin","villagenumberMin");
        }


        if (!id.equals(minId) &&!id.equals(maxId)) {

            Integer leftid = id + 1;

            String leftnumberUrl = villagenumberMapper.getUrlById(leftid, currentUserId);

            if (leftnumberUrl == null) {
                leftnumberUrl = villagenumberMapper.getUrlById(leftid + 1, currentUserId);
            }

            Integer trueNextId = id - 1;

            String rightplatenumberUrl = villagenumberMapper.getUrlById(trueNextId, currentUserId);

            if (rightplatenumberUrl == null) {

                rightplatenumberUrl = villagenumberMapper.getUrlById(trueNextId - 1, currentUserId);
            }

            request.setAttribute("villagenumberleftnumberUrl", rightplatenumberUrl);

            request.setAttribute("villagenumberrightplatenumberUrl", leftnumberUrl);
        }
        }



        Villagenumber platenumber=villagenumberMapper.getVillageNumByUrl(url1,currentUserId);

        if(15==currentUserId) {
            if (platenumber != null) {

                if(platenumber.getLonandlat()!=null&&!platenumber.getLonandlat().equals("")) {

                    Count count = countMapper.getCountByNameAndType(platenumber.getName(), "villagenumber", currentUserId);

                    if (count != null) {

                        countMapper.updatePlateNumCount(count.getName(), count.getType(), currentUserId);

                    }


                    PlateNumberVo plateNumberVo = new PlateNumberVo();

                    plateNumberVo.setId(platenumber.getId());
                    plateNumberVo.setName(platenumber.getName());
                    plateNumberVo.setLonandlat(platenumber.getLonandlat());
                    plateNumberVo.setType(platenumber.getType());
                    plateNumberVo.setUrl(platenumber.getUrl());
                    plateNumberVo.setStatus(platenumber.getStatus());
                    List<String> picture = pictureMapper.getPictureByVillagenumberid(platenumber.getId(), currentUserId);
                    if (picture != null & !picture.equals("")) {

                        plateNumberVo.setPictureUrl(picture);
                    }


                    List<String> voice = audioMapper.getAudioByVillagenumberid(platenumber.getId(), currentUserId);

                    if (voice != null) {

                        for (int k = 0; k < voice.size(); k++) {

                            if (voice.get(k).indexOf("mandarin") != -1) {
                                plateNumberVo.setMandarinVoice(voice.get(k));
                            }

                            if (voice.get(k).indexOf("english") != -1) {
                                plateNumberVo.setEnglishVoice(voice.get(k));
                            }

                            if (voice.get(k).indexOf("dialect") != -1) {
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


                    request.setAttribute("villageid", plateNumberVo.getId());
                    request.setAttribute("villagename", plateNumberVo.getName());

                    session.setAttribute("VillageName", plateNumberVo.getName());

                    request.setAttribute("villagelonandlat", plateNumberVo.getLonandlat());
                    request.setAttribute("villagetype", plateNumberVo.getType());
                    request.setAttribute("villageurl", plateNumberVo.getUrl());


                    request.setAttribute("villagemandarinVoice", plateNumberVo.getMandarinVoice());
                    request.setAttribute("villageenglishVoice", plateNumberVo.getEnglishVoice());
                    request.setAttribute("villagedialectVoice", plateNumberVo.getDialectVoice());

                    //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


                    for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                        request.setAttribute("villagepictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                        request.setAttribute("villagepictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

                    }

                    request.setAttribute("villagepictureUrl", plateNumberVo.getPictureUrl());

                    request.setAttribute("villagetextofmater", plateNumberVo.getTextOfMater());

                    request.setAttribute("villagesize", plateNumberVo.getSize());
                    request.setAttribute("villageyearofesent", plateNumberVo.getYearOfEsent());
                    request.setAttribute("villagemainteunit", plateNumberVo.getMainteUnit());
                    request.setAttribute("villagenumofmaint", plateNumberVo.getNumofMaint());
                    request.setAttribute("villagemaintperiod", plateNumberVo.getMaintPeriod());
                    request.setAttribute("villagemaintcost", plateNumberVo.getMaintCost());
                    request.setAttribute("villagemaintaccounts", plateNumberVo.getMaintAccounts());

                    request.setAttribute("villagemandarinWord", platenumber.getMandarinword());
                    request.setAttribute("villageenglishWord", platenumber.getEnglishword());

                    request.setAttribute("sharelinks", platenumber.getSharelinks());


           //         System.out.println("哈哈哈哈哈哈哈");

                    return "villagenumberCopy";
                }else{
                    return "villagenumberLonLat";
                }

            }else{

                return "villagenumberLonLat";
            }


        }


        if(platenumber.getLonandlat()!=null&&!platenumber.getLonandlat().equals("")){

            Count count = countMapper.getCountByNameAndType(platenumber.getName(), "villagenumber",currentUserId);

            if(count!=null) {

                countMapper.updatePlateNumCount(count.getName(), count.getType(),currentUserId);
            }

            PlateNumberVo plateNumberVo = new PlateNumberVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getUrl());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByVillagenumberid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByVillagenumberid(platenumber.getId(),currentUserId);

            if (voice != null) {

                for (int k = 0; k < voice.size(); k++) {

                    if (voice.get(k).indexOf("mandarin") != -1) {
                        plateNumberVo.setMandarinVoice(voice.get(k));
                    }

                    if (voice.get(k).indexOf("english") != -1) {
                        plateNumberVo.setEnglishVoice(voice.get(k));
                    }

                    if (voice.get(k).indexOf("dialect") != -1) {
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


            request.setAttribute("villageid", plateNumberVo.getId());
            request.setAttribute("villagename", plateNumberVo.getName());
            request.setAttribute("villagelonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("villagetype", plateNumberVo.getType());
            request.setAttribute("villageurl", plateNumberVo.getUrl());


            request.setAttribute("villagemandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("villageenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("villagedialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("villagepictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("villagepictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("villagepictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("villagetextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("villagesize", plateNumberVo.getSize());
            request.setAttribute("villageyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("villagemainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("villagenumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("villagemaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("villagemaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("villagemaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("villagemandarinWord",platenumber.getMandarinword());
            request.setAttribute("villageenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());


//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

       //     System.out.println("哈哈哈哈哈哈哈");

            return "villagenumber";

        }else{


            PlateNumberVo plateNumberVo = new PlateNumberVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getUrl());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByVillagenumberid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByVillagenumberid(platenumber.getId(),currentUserId);

            if (voice != null) {

                for (int k = 0; k < voice.size(); k++) {

                    if (voice.get(k).indexOf("mandarin") != -1) {
                        plateNumberVo.setMandarinVoice(voice.get(k));
                    }

                    if (voice.get(k).indexOf("english") != -1) {
                        plateNumberVo.setEnglishVoice(voice.get(k));
                    }

                    if (voice.get(k).indexOf("dialect") != -1) {
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


            request.setAttribute("villageid", plateNumberVo.getId());
            request.setAttribute("villagename", plateNumberVo.getName());
            request.setAttribute("villagelonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("villagetype", plateNumberVo.getType());
            request.setAttribute("villageurl", plateNumberVo.getUrl());


            request.setAttribute("villagemandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("villageenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("villagedialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("villagepictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("villagepictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("villagepictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("villagetextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("villagesize", plateNumberVo.getSize());
            request.setAttribute("villageyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("villagemainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("villagenumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("villagemaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("villagemaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("villagemaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("villagemandarinWord",platenumber.getMandarinword());
            request.setAttribute("villageenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());


//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

        //    System.out.println("哈哈哈哈哈哈哈");

            return "villagenumberLonLat";
        }


    }

}
