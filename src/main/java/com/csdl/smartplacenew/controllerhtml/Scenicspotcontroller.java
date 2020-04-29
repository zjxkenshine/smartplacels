package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.Count;
import com.csdl.smartplacenew.pojo.Scenicspot;
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

@Controller
@RequestMapping("/Scenicspot")
public class Scenicspotcontroller {

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
        session.setAttribute("Scenicspoturl",url1);


        String[]  strings=url1.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);


        request.setAttribute("ScenicspotcurrentUserId",currentUserId);


        session.setAttribute("ScenicspotcurrentUserIdFreed",currentUserId);


        Integer id=scenicspotMapper.getIdByUrl(url1,currentUserId);

        Integer maxId=scenicspotMapper.getMaxId(currentUserId);

        Integer minId=scenicspotMapper.getMinId(
                currentUserId
        );

        //添加总访问量
        count2Service.addCount(String.valueOf(currentUserId));

        if(id.equals(minId)){

            Integer leftId=id+1;

            String leftnumberUrl=scenicspotMapper.getUrlById(leftId,currentUserId);


            String rightplatenumberUrl=scenicspotMapper.getUrlById(id,currentUserId);


            request.setAttribute("scenicspotleftnumberUrl",leftnumberUrl);

            request.setAttribute("scenicspotrightplatenumberUrl",rightplatenumberUrl);

            request.setAttribute("scenicspotMin","scenicspotMin");
        }


        if(id.equals(maxId))

        {


            String leftnumberUrl=scenicspotMapper.getUrlById(id,currentUserId);

            Integer trueNextId=id-1;

            String rightplatenumberUrl=scenicspotMapper.getUrlById(trueNextId,currentUserId);


            request.setAttribute("scenicspotleftnumberUrl",leftnumberUrl);

            request.setAttribute("scenicspotrightplatenumberUrl",rightplatenumberUrl);

            request.setAttribute("scenicspotMax","scenicspotMax");
        }


        if (!id.equals(minId) &&!id.equals(maxId)){

            Integer leftid=id+1;

            String leftnumberUrl=scenicspotMapper.getUrlById(leftid,currentUserId);

            Integer trueNextId=id-1;

            String rightplatenumberUrl=scenicspotMapper.getUrlById(trueNextId,currentUserId);


            request.setAttribute("scenicspotleftnumberUrl",leftnumberUrl);

            request.setAttribute("scenicspotrightplatenumberUrl",rightplatenumberUrl);

        }


        Scenicspot platenumber=scenicspotMapper.getScenicspotByUrl(url1,currentUserId);


        if(platenumber.getLonandlat()!=null&&!platenumber.getLonandlat().equals("")){

            Count count = countMapper.getCountByNameAndType(platenumber.getName(), "scenicspot",currentUserId);

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
            List<String> picture = pictureMapper.getPictureByScenicspotid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByScenicspotid(platenumber.getId(),currentUserId);

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


            request.setAttribute("scenicspotid", plateNumberVo.getId());
            request.setAttribute("scenicspotname", plateNumberVo.getName());
            request.setAttribute("scenicspotlonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("scenicspottype", plateNumberVo.getType());
            request.setAttribute("scenicspoturl", plateNumberVo.getUrl());


            request.setAttribute("scenicspotmandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("scenicspotenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("scenicspotdialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("scenicspotpictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("scenicspotpictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("scenicspotpictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("scenicspottextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("scenicspotsize", plateNumberVo.getSize());
            request.setAttribute("scenicspotyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("scenicspotmainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("scenicspotnumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("scenicspotmaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("scenicspotmaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("scenicspotmaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("scenicspotmandarinWord",platenumber.getMandarinword());
            request.setAttribute("scenicspotenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());

//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

         //   System.out.println("哈哈哈哈哈哈哈");

            return "scenicspot";

        }else{


            PlateNumberVo plateNumberVo = new PlateNumberVo();

            plateNumberVo.setId(platenumber.getId());
            plateNumberVo.setName(platenumber.getName());
            plateNumberVo.setLonandlat(platenumber.getLonandlat());
            plateNumberVo.setType(platenumber.getType());
            plateNumberVo.setUrl(platenumber.getUrl());
            plateNumberVo.setStatus(platenumber.getStatus());
            List<String> picture = pictureMapper.getPictureByScenicspotid(platenumber.getId(),currentUserId);
            if (picture != null & !picture.equals("")) {

                plateNumberVo.setPictureUrl(picture);
            }


            List<String> voice = audioMapper.getAudioByScenicspotid(platenumber.getId(),currentUserId);

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


            request.setAttribute("scenicspotid", plateNumberVo.getId());
            request.setAttribute("scenicspotname", plateNumberVo.getName());
            request.setAttribute("scenicspotlonandlat", plateNumberVo.getLonandlat());
            request.setAttribute("scenicspottype", plateNumberVo.getType());
            request.setAttribute("scenicspoturl", plateNumberVo.getUrl());


            request.setAttribute("scenicspotmandarinVoice", plateNumberVo.getMandarinVoice());
            request.setAttribute("scenicspotenglishVoice", plateNumberVo.getEnglishVoice());
            request.setAttribute("scenicspotdialectVoice", plateNumberVo.getDialectVoice());

            //   request.setAttribute("voiceUrl",plateNumberVo.getVoiceUrl());


            for (int i = 0; i < plateNumberVo.getPictureUrl().size(); i++) {

                request.setAttribute("scenicspotpictureUrl" + i, plateNumberVo.getPictureUrl().get(i));

                request.setAttribute("scenicspotpictureUrl" + i + i, "http://localhost:1243/API" + plateNumberVo.getPictureUrl().get(i));

            }

            request.setAttribute("scenicspotpictureUrl", plateNumberVo.getPictureUrl());

            request.setAttribute("scenicspottextofmater", plateNumberVo.getTextOfMater());

            request.setAttribute("scenicspotsize", plateNumberVo.getSize());
            request.setAttribute("scenicspotyearofesent", plateNumberVo.getYearOfEsent());
            request.setAttribute("scenicspotmainteunit", plateNumberVo.getMainteUnit());
            request.setAttribute("scenicspotnumofmaint", plateNumberVo.getNumofMaint());
            request.setAttribute("scenicspotmaintperiod", plateNumberVo.getMaintPeriod());
            request.setAttribute("scenicspotmaintcost", plateNumberVo.getMaintCost());
            request.setAttribute("scenicspotmaintaccounts", plateNumberVo.getMaintAccounts());

            request.setAttribute("scenicspotmandarinWord",platenumber.getMandarinword());
            request.setAttribute("scenicspotenglishWord",platenumber.getEnglishword());

            request.setAttribute("sharelinks",platenumber.getSharelinks());


//        List<PlateNumberVo> plateNumberVoList=new ArrayList<>();
//        plateNumberVoList.add(plateNumberVo);
//
//        JSONArray data = JSONArray.fromObject(plateNumberVoList).t;

        //    System.out.println("哈哈哈哈哈哈哈");


            return "scenicspotLonLat";
        }


    }

}
