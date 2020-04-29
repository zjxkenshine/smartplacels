package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Platenumber;
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
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/Platenumberindex")
public class Platenumberindexcontroller {

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
    Count2Service count2Service;

    @Resource
    UserMapper userMapper;




    @GetMapping(value = "/hh")
    public String hh(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "url",required = false) String url) throws IOException, ParseException {

            HttpSession session=request.getSession();

            String url1=request.getParameter("url");

            session.setAttribute("Platenumberurl",url1);

            String[]  strings=url1.split("\\/");

            Integer currentUserId=Integer.valueOf(strings[0]);

            request.setAttribute("PlateNumbercurrentUserId",currentUserId);

           session.setAttribute("PlateNumbercurrentUserIdFreed",currentUserId);

            //添加总访问量
            count2Service.addCount(String.valueOf(currentUserId));

            if(currentUserId==3){

                Integer id=platenumberMapper.getOrderByUseriId(url1,currentUserId);

                Integer maxId=platenumberMapper.getMaxOrderByUseriId(currentUserId);

                Integer minId=platenumberMapper.getMinOrderByUseriId(currentUserId);


                if(id.equals(minId)){

                    Integer leftId=id+1;

                    String  leftnumberUrl=platenumberMapper.getUrlByOrderindexByUserId(leftId,currentUserId);

                    String rightplatenumberUrl=platenumberMapper.getUrlByOrderindexByUserId(id,currentUserId);

                    request.setAttribute("leftnumberUrl",leftnumberUrl);

                    request.setAttribute("rightplatenumberUrl",rightplatenumberUrl);

                    request.setAttribute("PlatenumberMin","PlatenumberMin");

                }


                if(id.equals(maxId))

                {


                    String leftnumberUrl=platenumberMapper.getUrlByOrderindexByUserId(id,currentUserId);


                    Integer trueNextId=id-1;


                    String   rightplatenumberUrl=platenumberMapper.getUrlByOrderindexByUserId(trueNextId,currentUserId);


                    request.setAttribute("leftnumberUrl",leftnumberUrl);

                    request.setAttribute("rightplatenumberUrl",rightplatenumberUrl);

                    request.setAttribute("PlatenumberMax","PlatenumberMax");

                }


                if (!id.equals(minId) &&!id.equals(maxId)){


                    Integer leftid=id+1;

                    String  leftnumberUrl=platenumberMapper.getUrlByOrderindexByUserId(leftid,currentUserId);


                    Integer trueNextId=id-1;

                    String  rightplatenumberUrl=platenumberMapper.getUrlByOrderindexByUserId(trueNextId,currentUserId);


                    request.setAttribute("leftnumberUrl",leftnumberUrl);

                    request.setAttribute("rightplatenumberUrl",rightplatenumberUrl);

                }



            }else {


//            String currentName=platenumberMapper.getNameByUrlAndUserId(url1,currentUserId);
//
//            String listName = currentName.replaceAll("\\d+", "");


                Integer id = platenumberMapper.getIdByUrl(url1, currentUserId);

                Integer maxId = platenumberMapper.getMaxId(currentUserId);

                Integer minId = platenumberMapper.getMinId(currentUserId);


                if (id.equals(minId)) {

                    Integer leftId = id + 1;

                    String leftnumberUrl = platenumberMapper.getUrlById(leftId, currentUserId);

                    String rightplatenumberUrl = platenumberMapper.getUrlById(id, currentUserId);

                    request.setAttribute("leftnumberUrl", leftnumberUrl);

                    request.setAttribute("rightplatenumberUrl", rightplatenumberUrl);

                    request.setAttribute("PlatenumberMin", "PlatenumberMin");

                }


                if (id.equals(maxId)) {


                    String leftnumberUrl = platenumberMapper.getUrlById(id, currentUserId);


                    Integer trueNextId = id - 1;


                    String rightplatenumberUrl = platenumberMapper.getUrlById(trueNextId, currentUserId);


                    request.setAttribute("leftnumberUrl", leftnumberUrl);

                    request.setAttribute("rightplatenumberUrl", rightplatenumberUrl);

                    request.setAttribute("PlatenumberMax", "PlatenumberMax");

                }


                if (!id.equals(minId) && !id.equals(maxId)) {


                    Integer leftid = id + 1;

                    String leftnumberUrl = platenumberMapper.getUrlById(leftid, currentUserId);


                    Integer trueNextId = id - 1;

                    String rightplatenumberUrl = platenumberMapper.getUrlById(trueNextId, currentUserId);


                    request.setAttribute("leftnumberUrl", leftnumberUrl);

                    request.setAttribute("rightplatenumberUrl", rightplatenumberUrl);

                }

            }

           Platenumber platenumber = platenumberMapper.getPlateNumByUrl(url1,currentUserId);



        if(platenumber.getLonandlat()!=null&&!platenumber.getLonandlat().equals("")){

            Count count = countMapper.getCountByNameAndType(platenumber.getName(), "platenumber",currentUserId);

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
            List<String> picture = pictureMapper.getPictureByPlatenumberid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByPlatenumberid(platenumber.getId(),currentUserId);

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

            plateNumberVo.setMandarinWord(platenumber.getMandarinword());

          //  System.out.println("中文:"+platenumber.getMandarinword());
            plateNumberVo.setEnglishWord(platenumber.getEnglishword());

            request.setAttribute("id", plateNumberVo.getId());
            request.setAttribute("name", plateNumberVo.getName());
            request.setAttribute("lonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("type", plateNumberVo.getType());
            request.setAttribute("url", plateNumberVo.getUrl());

            request.setAttribute("mandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("englishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("dialectVoice", plateNumberVo.getDialectVoice());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("pictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("pictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("pictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("textofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("size", plateNumberVo.getSize());
            request.setAttribute("yearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("mainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("numofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("maintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("maintcost", plateNumberVo.getMaintCost());
            request.setAttribute("maintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("mandarinWord",plateNumberVo.getMandarinWord());
            request.setAttribute("englishWord",plateNumberVo.getEnglishWord());

            request.setAttribute("sharelinks",platenumber.getSharelinks());

      //      System.out.println("分享链接:"+platenumber.getSharelinks());

         //   System.out.println("哈哈哈哈哈哈哈");


            return "platenumber";

        }else{

            PlateNumberVo plateNumberVo = new PlateNumberVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getUrl());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByPlatenumberid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByPlatenumberid(platenumber.getId(),currentUserId);

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

            plateNumberVo.setMandarinWord(platenumber.getMandarinword());
            plateNumberVo.setEnglishWord(platenumber.getEnglishword());

            request.setAttribute("id", plateNumberVo.getId());
            request.setAttribute("name", plateNumberVo.getName());
            request.setAttribute("lonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("type", plateNumberVo.getType());
            request.setAttribute("url", plateNumberVo.getUrl());

            request.setAttribute("mandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("englishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("dialectVoice", plateNumberVo.getDialectVoice());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("pictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("pictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("pictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("textofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("size", plateNumberVo.getSize());
            request.setAttribute("yearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("mainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("numofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("maintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("maintcost", plateNumberVo.getMaintCost());
            request.setAttribute("maintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("mandarinWord",plateNumberVo.getMandarinWord());
            request.setAttribute("englishWord",plateNumberVo.getEnglishWord());

            request.setAttribute("sharelinks",platenumber.getSharelinks());

      //      System.out.println("分享链接:"+platenumber.getSharelinks());

           // System.out.println("哈哈哈哈哈哈哈");


            return "platenumberLonLat";

        }


    }





}
