package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Road;
import com.csdl.smartplacenew.service.Count2Service;
import com.csdl.smartplacenew.vo.RoadVo;
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

@Controller
@RequestMapping("/Roadindex")
public class Roadcontroller {

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

    @GetMapping(value = "/hh")
    public String hh(HttpServletRequest request, HttpServletResponse response,
                     @RequestParam(value = "url",required = false) String url) throws ParseException {

        String url1=request.getParameter("url");


        HttpSession session=request.getSession();
        session.setAttribute("Roadurl",url1);

        String[]  strings=url1.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);

        request.setAttribute("RoadcurrentUserId",currentUserId);


        session.setAttribute("RoadcurrentUserIdFreed",currentUserId);

        //添加总访问量
        count2Service.addCount(String.valueOf(currentUserId));

        Integer id=roadMapper.getIdByUrl(url1,currentUserId);

        Integer maxId=roadMapper.getMaxId(currentUserId);

        Integer minId=roadMapper.getMinId(currentUserId);

        if(id.equals(minId)){

            Integer leftId=id+1;

            String   leftnumberUrl=roadMapper.getUrlById(leftId,currentUserId);


            String rightplatenumberUrl=roadMapper.getUrlById(id,currentUserId);

            request.setAttribute("roadleftnumberUrl",leftnumberUrl);

            request.setAttribute("roadrightplatenumberUrl",rightplatenumberUrl);

            request.setAttribute("roadMin","roadMin");
        }


        if(id.equals(maxId))

        {


            String leftnumberUrl=roadMapper.getUrlById(id,currentUserId);

            Integer trueNextId=id-1;


            String     rightplatenumberUrl=roadMapper.getUrlById(trueNextId,currentUserId);



            request.setAttribute("roadleftnumberUrl",leftnumberUrl);

            request.setAttribute("roadrightplatenumberUrl",rightplatenumberUrl);

            request.setAttribute("roadMax","roadMax");
        }


        if (!id.equals(minId) &&!id.equals(maxId)){

            Integer leftid=id+1;


            String   leftnumberUrl=roadMapper.getUrlById(leftid,currentUserId);


            Integer trueNextId=id-1;

            String rightplatenumberUrl=roadMapper.getUrlById(trueNextId,currentUserId);


            request.setAttribute("roadleftnumberUrl",leftnumberUrl);

            request.setAttribute("roadrightplatenumberUrl",rightplatenumberUrl);

        }




        Road platenumber=roadMapper.getRoadByUrl(url1,currentUserId);



        if(platenumber.getLonandlat()!=null&&!platenumber.getLonandlat().equals("")){


            Count count = countMapper.getCountByNameAndType(platenumber.getName(), "road",currentUserId);

            if(count!=null) {

                countMapper.updatePlateNumCount(count.getName(), count.getType(),currentUserId);
            }

            RoadVo plateNumberVo = new RoadVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getCodeaddress());
            plateNumberVo.setStreetNumber(platenumber.getStreetnumber());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByRoadid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByRoadid(platenumber.getId(),currentUserId);

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


            request.setAttribute("roadid", plateNumberVo.getId());
            request.setAttribute("roadname", plateNumberVo.getName());
            request.setAttribute("roadlonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("roadtype", plateNumberVo.getType());
            request.setAttribute("roadurl", plateNumberVo.getUrl());


            request.setAttribute("roadmandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("roadenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("roaddialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("roadpictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("roadpictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("roadpictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("roadtextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("roadsize", plateNumberVo.getSize());
            request.setAttribute("roadyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("roadmainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("roadnumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("roadmaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("roadmaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("roadmaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("roadmandarinWord",platenumber.getMandarinword());
            request.setAttribute("roadenglishWord",platenumber.getEnglishword());


            request.setAttribute("sharelinks",platenumber.getSharelinks());


//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

        //    System.out.println("哈哈哈哈哈哈哈");


            return "road";

        }else{

            RoadVo plateNumberVo = new RoadVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getCodeaddress());
            plateNumberVo.setStreetNumber(platenumber.getStreetnumber());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByRoadid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByRoadid(platenumber.getId(),currentUserId);

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


            request.setAttribute("roadid", plateNumberVo.getId());
            request.setAttribute("roadname", plateNumberVo.getName());
            request.setAttribute("roadlonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("roadtype", plateNumberVo.getType());
            request.setAttribute("roadurl", plateNumberVo.getUrl());


            request.setAttribute("roadmandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("roadenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("roaddialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("roadpictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("roadpictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("roadpictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("roadtextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("roadsize", plateNumberVo.getSize());
            request.setAttribute("roadyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("roadmainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("roadnumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("roadmaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("roadmaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("roadmaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("roadmandarinWord",platenumber.getMandarinword());
            request.setAttribute("roadenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());



//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

      //      System.out.println("哈哈哈哈哈哈哈");


            return "roadLonLat";

        }


    }

}
