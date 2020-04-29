package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Roadnumber;
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
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/Roadnumber")
public class Roadnumbercontroller {

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
    Count2Service count2Service;

    public static boolean isContainChinese(String str) {

                 Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                 Matcher m = p.matcher(str);
                if (m.find()) {
                        return true;
                     }
                 return false;
             }

    @GetMapping(value = "/hh")
    public String hh(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "url",required = false) String url) throws ParseException {


        String url1=request.getParameter("url");

        HttpSession session=request.getSession();
        session.setAttribute("Roadnumberurl",url1);


        String[]  strings=url1.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);

        request.setAttribute("RoadNumbercurrentUserId",currentUserId);

        session.setAttribute("RoadNumbercurrentUserIdFreed",currentUserId);

        //添加总访问量
        count2Service.addCount(String.valueOf(currentUserId));

        if(currentUserId==3||currentUserId==14||currentUserId==16){


            String currendName=roadnumberMapper.getNameByUrlAndUserId(url1,currentUserId);


            if(isContainChinese(currendName)) {

                String allName = currendName.replaceAll("\\d+", "");

                List<Roadnumber> roadnumberList = roadnumberMapper.getRoadnumberByNameAndUserId("%" + allName + "%", currentUserId);

                Roadnumber roadnumber = roadnumberMapper.getRoadNumberByUrl(url1, currentUserId);

                int index = roadnumberList.indexOf(roadnumber);

                if (index == 0 && roadnumberList.size() > 1) {

                    String leftnumberUrl = roadnumberMapper.getUrlByNameAndUserId(roadnumber.getName(), currentUserId);

                    String rightplatenumberUrl = roadnumberMapper.getUrlByNameAndUserId(
                            roadnumberList.get(index + 1).getName(), currentUserId);

                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);

                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);

                    request.setAttribute("roadnumberMin", "roadnumberMin");
                }


                if (index == 0 && roadnumberList.size() == 1) {

                    String leftnumberUrl = roadnumberMapper.getUrlByNameAndUserId(roadnumber.getName(), currentUserId);


                    String rightplatenumberUrl = roadnumberMapper.getUrlByNameAndUserId(roadnumber.getName(), currentUserId);


                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);

                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);

                    request.setAttribute("roadnumberMin", "roadnumberMin");
                    request.setAttribute("roadnumberMax", "roadnumberMax");

                }


                if (index == roadnumberList.size() - 1 && roadnumberList.size() > 1) {


                    String leftnumberUrl = roadnumberMapper.getUrlByNameAndUserId(roadnumberList.get(roadnumberList.size() - 2).getName(),

                            currentUserId);


                    String rightplatenumberUrl = roadnumberMapper.getUrlByNameAndUserId(roadnumber.getName(), currentUserId);


                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);

                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);

                    request.setAttribute("roadnumberMax", "roadnumberMax");
                }


                if (0 < index && index < roadnumberList.size() - 1) {


                    String leftnumberUrl = roadnumberMapper.getUrlByNameAndUserId(
                            roadnumberList.get(index - 1).getName(),
                            currentUserId);


                    String rightplatenumberUrl = roadnumberMapper.getUrlByNameAndUserId(
                            roadnumberList.get(index + 1).getName(),
                            currentUserId);

                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);

                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);

                }
            }


        }

//        if(currentUserId==14){
//
//            Integer id=roadnumberMapper.getOrderIndexRoadNumberByUserid(url1,currentUserId);
//
//            Integer maxId=roadnumberMapper.getMaxOrderIndexRoadNumberByUserId(currentUserId);
//
//            Integer minId=roadnumberMapper.getMinOrderIndexRoadNumberByUserId(currentUserId);
//
//            if(id!=null){
//
//                if(id.equals(minId)){
//
//                    Integer leftId=id+1;
//
//                    String leftnumberUrl=roadnumberMapper.getUrlByOrderIndexRoadNumberByUserId(leftId,currentUserId);
//
//                    String rightplatenumberUrl=roadnumberMapper.getUrlByOrderIndexRoadNumberByUserId(id,currentUserId);
//
//                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);
//
//                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);
//
//                    request.setAttribute("roadnumberMin", "roadnumberMin");
//                }
//
//
//                if(id.equals(maxId)){
//
//                    Integer trueIndexId=id-1;
//
//                    String leftnumberUrl=roadnumberMapper.getUrlByOrderIndexRoadNumberByUserId(id,currentUserId);
//
//                    String rightplatenumberUrl=roadnumberMapper.getUrlByOrderIndexRoadNumberByUserId(trueIndexId,currentUserId);
//
//                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);
//
//                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);
//
//                    request.setAttribute("roadnumberMax", "roadnumberMax");
//
//                }
//
//
//                if(!id.equals(minId)&&!id.equals(maxId)){
//
//                    Integer leftId=id+1;
//
//                    String leftnumberUrl=roadnumberMapper.getUrlByOrderIndexRoadNumberByUserId(leftId,currentUserId);
//
//                    Integer trueIndexId=id-1;
//
//
//                    String rightplatenumberUrl=roadnumberMapper.getUrlByOrderIndexRoadNumberByUserId(trueIndexId,currentUserId);
//
//                    request.setAttribute("roadnumberleftnumberUrl", leftnumberUrl);
//
//                    request.setAttribute("roadnumberrightplatenumberUrl", rightplatenumberUrl);
//
//                }
//            }
//
//
//
//
//        }


        Roadnumber platenumber = roadnumberMapper.getRoadNumberByUrl(url1,currentUserId);

        if(platenumber.getLonandlat()!=null&&!platenumber.getLonandlat().equals("")){


            Count count = countMapper.getCountByNameAndType(platenumber.getName(), "roadnumber",currentUserId);

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
            List<String> picture = pictureMapper.getPictureByRoadnumberid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByRoadnumberid(platenumber.getId(),currentUserId);

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


            request.setAttribute("roadnumberid", plateNumberVo.getId());
            request.setAttribute("roadnumbername", plateNumberVo.getName());
            request.setAttribute("roadnumberlonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("roadnumbertype", plateNumberVo.getType());
            request.setAttribute("roadnumberurl", plateNumberVo.getUrl());


            request.setAttribute("roadnumbermandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("roadnumberenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("roadnumberdialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("roadnumberpictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("roadnumberpictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("roadnumberpictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("roadnumbertextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("roadnumbersize", plateNumberVo.getSize());
            request.setAttribute("roadnumberyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("roadnumbermainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("roadnumbernumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("roadnumbermaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("roadnumbermaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("roadnumbermaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("roadnumbermandarinWord",platenumber.getMandarinword());
            request.setAttribute("roadnumberenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());


//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

         //   System.out.println("哈哈哈哈哈哈哈");


            return "roadnumber";

        }else{


            PlateNumberVo plateNumberVo = new PlateNumberVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getUrl());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByRoadnumberid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByRoadnumberid(platenumber.getId(),currentUserId);

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


            request.setAttribute("roadnumberid", plateNumberVo.getId());
            request.setAttribute("roadnumbername", plateNumberVo.getName());
            request.setAttribute("roadnumberlonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("roadnumbertype", plateNumberVo.getType());
            request.setAttribute("roadnumberurl", plateNumberVo.getUrl());


            request.setAttribute("roadnumbermandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("roadnumberenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("roadnumberdialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("roadnumberpictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("roadnumberpictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("roadnumberpictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("roadnumbertextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("roadnumbersize", plateNumberVo.getSize());
            request.setAttribute("roadnumberyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("roadnumbermainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("roadnumbernumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("roadnumbermaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("roadnumbermaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("roadnumbermaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("roadnumbermandarinWord",platenumber.getMandarinword());
            request.setAttribute("roadnumberenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());


//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

        //    System.out.println("哈哈哈哈哈哈哈");


            return "roadNumberLonLat";
        }


    }

}
