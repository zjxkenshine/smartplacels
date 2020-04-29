package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LonAndLatcontroller {

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


    @GetMapping(value = "/PlatenumberAddLon")
    public String Freedbindexshow(HttpServletRequest request, HttpServletResponse response) {

        return "platenumberLonLat";

    }




    @GetMapping(value = "/RoadAddLon")
    public String RoadFreedbindexshow(HttpServletRequest request, HttpServletResponse response) {

        return "roadLonLat";

    }



    @GetMapping(value = "/RoadNumberAddLon")
    public String RoadNumberFreedbindexshow(HttpServletRequest request, HttpServletResponse response) {

        return "roadNumberLonLat";

    }



    @GetMapping(value = "/ScenicspotAddLon")
    public String ScenicspotFreedbindexshow(HttpServletRequest request, HttpServletResponse response) {

        return "scenicspotLonLat";

    }



    @GetMapping(value = "/VillagenumberAddLon")
    public String VillagenumberFreedbindexshow(HttpServletRequest request, HttpServletResponse response) {

        return "villagenumberLonLat";

    }





    @GetMapping(value = "/PlatenumberAddLonIndex")
    public void PlatenumberFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {



        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Platenumberurl");


        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);


        String lonAndLat=request.getParameter("platenumberLonAndLat");

        platenumberMapper.addLonAndLatByUrl(lonAndLat,url,currentUserId);

        response.sendRedirect("Platenumberindex/hh?url="+url);

    }




    @GetMapping(value = "/RoadAddLonIndex")
    public void RoadFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {



        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Roadurl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);



        String lonAndLat=request.getParameter("roadLonAndLat");

        roadMapper.addLonAndLatByUrl(lonAndLat,url,currentUserId);


        response.sendRedirect("Roadindex/hh?url="+url);

    }



    @GetMapping(value = "/RoadNumberAddLonIndex")
    public void RoadNumberFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {



        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Roadnumberurl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);

        String lonAndLat=request.getParameter("roadNumberLonAndLat");

        roadnumberMapper.addLonAndLatByUrl(lonAndLat,url,currentUserId);


        response.sendRedirect("Roadnumber/hh?url="+url);

    }





    @GetMapping(value = "/ScenicspotAddLonIndex")
    public void ScenicspotFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {



        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Scenicspoturl");

        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);


        String lonAndLat=request.getParameter("scenicspotLonAndLat");

        scenicspotMapper.addLonAndLatByUrl(lonAndLat,url,currentUserId);


        response.sendRedirect("Scenicspot/hh?url="+url);

    }




    @GetMapping(value = "/VillagenumberAddLonIndex")
    public void VillagenumberFreedbindex(HttpServletRequest request, HttpServletResponse response) throws IOException {



        HttpSession session=request.getSession();

        String url=(String) session.getAttribute("Villagenumberurl");


        String[]  strings=url.split("\\/");

        Integer currentUserId=Integer.valueOf( strings[0]);



        String lonAndLat=request.getParameter("villagenumberLonAndLat");

        villagenumberMapper.addLonAndLatByUrl(lonAndLat,url,currentUserId);

        response.sendRedirect("Villagenumber/hh?url="+url);

    }






}
