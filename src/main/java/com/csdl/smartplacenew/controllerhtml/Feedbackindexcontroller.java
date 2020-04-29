package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class Feedbackindexcontroller {

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
    FeedbackMapper feedbackMapper;

    @Resource
    UserMapper userMapper;


    @GetMapping(value = "/PlatenumberFreedbindexshow")
    public String Freedbindexshow(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(value = "url",required = false) String url,
                                  @RequestParam(value = "planamkey",required = false) Integer planamkey) {

        HttpSession session=request.getSession();

        Integer PlateNumbercurrentUserIdFreed=(Integer) session.getAttribute("PlateNumbercurrentUserIdFreed");

        request.setAttribute("PlateNumbercurrentUserId",PlateNumbercurrentUserIdFreed);



        return "platenumberFreedbindex";

    }




    @GetMapping(value = "/RoadFreedbindexshow")
    public String RoadFreedbindexshow(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam(value = "url",required = false) String url,
                                      @RequestParam(value = "planamkey",required = false) Integer planamkey) {

        HttpSession session=request.getSession();

        Integer RoadcurrentUserIdFreed=(Integer) session.getAttribute("RoadcurrentUserIdFreed");

        request.setAttribute("RoadcurrentUserId",RoadcurrentUserIdFreed);



        return "roadFreedbindex";

    }



    @GetMapping(value = "/RoadNumberFreedbindexshow")
    public String RoadNumberFreedbindexshow(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(value = "url",required = false) String url,
                                            @RequestParam(value = "planamkey",required = false) Integer planamkey) {


        HttpSession session=request.getSession();

        Integer RoadNumbercurrentUserIdFreed=(Integer) session.getAttribute("RoadNumbercurrentUserIdFreed");

        request.setAttribute("RoadNumbercurrentUserId",RoadNumbercurrentUserIdFreed);




        return "roadNumberFreedbindex";

    }



    @GetMapping(value = "/ScenicspotFreedbindexshow")
    public String ScenicspotFreedbindexshow(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam(value = "url",required = false) String url,
                                            @RequestParam(value = "planamkey",required = false) Integer planamkey) {

        HttpSession session=request.getSession();

        Integer ScenicspotcurrentUserIdFreed=(Integer) session.getAttribute("ScenicspotcurrentUserIdFreed");

        request.setAttribute("ScenicspotcurrentUserId",ScenicspotcurrentUserIdFreed);


        return "scenicspotFreedbindex";

    }



    @GetMapping(value = "/VillagenumberFreedbindexshow")
    public String VillagenumberFreedbindexshow(HttpServletRequest request, HttpServletResponse response,
                                               @RequestParam(value = "url",required = false) String url,
                                               @RequestParam(value = "planamkey",required = false) Integer planamkey) {

        HttpSession session=request.getSession();

        Integer VillageNumbercurrentUserId=(Integer) session.getAttribute("VillageNumbercurrentUserIdFreed");

        request.setAttribute("VillageNumbercurrentUserId",VillageNumbercurrentUserId);



        return "villagenumberFreedbindex";

    }





    @GetMapping(value = "/PlatenumberFreedbindex")
    public void PlatenumberFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {


        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Platenumberurl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);


        String select=request.getParameter("selectoption");
        String Detaconte=request.getParameter("Detaconte");
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳：" + ss.getTime());
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime()).substring(0,10);//这个就是把


        Platenumber platenumber = platenumberMapper.getPlateNumByUrl(url,currentUserId);
        feedbackMapper.addFeedback(platenumber.getName(),"门牌管理",Detaconte,"已预约",time,select,currentUserId);


        response.sendRedirect("Platenumberindex/hh?url="+url);

    }




    @GetMapping(value = "/RoadFreedbindex")
    public void RoadFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {


        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Roadurl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);

        String select=request.getParameter("Roadselectoption");
        String Detaconte=request.getParameter("RoadDetaconte");
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳：" + ss.getTime());
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime()).substring(0,10);//这个就是把


        Road platenumber=roadMapper.getRoadByUrl(url,currentUserId);

        feedbackMapper.addFeedback(platenumber.getName(),"道路管理",Detaconte,"已预约",time,select,currentUserId);


        response.sendRedirect("Roadindex/hh?url="+url);

    }



    @GetMapping(value = "/RoadNumberFreedbindex")
    public void RoadNumberFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {


        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Roadnumberurl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);


        String select=request.getParameter("RoadNumberselectoption");
        String Detaconte=request.getParameter("RoadNumberDetaconte");
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳：" + ss.getTime());
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime()).substring(0,10);//这个就是把


        Roadnumber roadnumber = roadnumberMapper.getRoadNumberByUrl(url,currentUserId);

        feedbackMapper.addFeedback(roadnumber.getName(),"路牌管理",Detaconte,"已预约",time,select,currentUserId);


        response.sendRedirect("Roadnumber/hh?url="+url);


    }





    @GetMapping(value = "/ScenicspotFreedbindex")
    public void ScenicspotFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Scenicspoturl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);


        String select=request.getParameter("Scenicspotselectoption");
        String Detaconte=request.getParameter("ScenicspotDetaconte");
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳：" + ss.getTime());
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime()).substring(0,10);//这个就是把


        Scenicspot scenicspot=scenicspotMapper.getScenicspotByUrl(url,currentUserId);

        feedbackMapper.addFeedback(scenicspot.getName(),"景区管理",Detaconte,"已预约",time,select,currentUserId);


        response.sendRedirect("Scenicspot/hh?url="+url);

    }




    @GetMapping(value = "/VillagenumberFreedbindex")
    public void VillagenumberFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Villagenumberurl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);

        String select=request.getParameter("VillageNumberselectoption");
        String Detaconte=request.getParameter("VillageNumberDetaconte");
        Date ss = new Date();
        System.out.println("一般日期输出：" + ss);
        System.out.println("时间戳：" + ss.getTime());
        SimpleDateFormat format0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format0.format(ss.getTime()).substring(0,10);//这个就是把

        Villagenumber villagenumber=villagenumberMapper.getVillageNumByUrl(url,currentUserId);

        feedbackMapper.addFeedback(villagenumber.getName(),"村牌管理",Detaconte,"已预约",time,select,currentUserId);


        response.sendRedirect("Villagenumber/hh?url="+url);

    }




}
