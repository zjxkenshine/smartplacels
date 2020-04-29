package com.csdl.smartplacenew.controllerhtml;


import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.pojo.*;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.CountVoOnlyFour;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Api(description = "统计总的数量")
@RestController
@RequestMapping("/Count")
public class CountController {

    @Resource
    PlatenumberMapper platenumberMapper;

    @Resource
    private RoadMapper roadMapper;

    @Resource
    private RoadnumberMapper roadnumberMapper;

    @Resource
    ScenicspotMapper scenicspotMapper;

    @Resource
    VillagenumberMapper villagenumberMapper;


    @Resource
    CountMapper countMapper;


    @Resource
    VillagenumberTwoMapper villagenumberTwoMapper;

    @Resource
    ScenicspotTwoMapper scenicspotTwoMapper;

    @Resource
    RoadnumberTwoMapper roadnumberTwoMapper;

    @Resource
    PlatenumberTwoMapper platenumberTwoMapper;

    @Resource
    UserMapper userMapper;





    @ApiOperation(value = "获取总的统计", notes = "")
    @ApiImplicitParams({


    })
    @GetMapping("/getAllCount")
    private ResultVO getAllCount(HttpServletRequest request) throws IOException {

//        User user= UserSecurity.getCurrentUser(request,userMapper);
//        Integer currentUserId=user.getId();

        HttpSession session=request.getSession();
        String account=(String) session.getAttribute("account");
        User user1=userMapper.getUserByLoginName(account);
        Integer currentUserId=user1.getId();

        List<PlatenumberTwo> platenumberList= platenumberTwoMapper.getNameAndLonLat(currentUserId);

        List<RoadnumberTwo> roadnumberTwoList=roadnumberTwoMapper.getNameAndLonLat(currentUserId);

        List<ScenicspotTwo> scenicspotTwoList=scenicspotTwoMapper.getNameAndLonLat(currentUserId);


        List<VillagenumberTwo> villagenumberTwoList=villagenumberTwoMapper.getNameAndLatLon(currentUserId);


        List<CountVoOnlyFour> countVoOnlyFourList=new ArrayList();

          for(int i=0;i<platenumberList.size();i++) {

              PlatenumberTwo platenumber = platenumberList.get(i);


              if (platenumber.getLonandlat() != null&&!platenumber.getLonandlat().equals("")) {

                  CountVoOnlyFour countVoOnlyFour = new CountVoOnlyFour();

                  countVoOnlyFour.setName(platenumber.getName());
                  countVoOnlyFour.setType("platenumber");

                  String[] strings = platenumber.getLonandlat().split(",");
                  countVoOnlyFour.setLon(Double.valueOf(strings[0]));
                  countVoOnlyFour.setLat(Double.valueOf(strings[1]));
                  Count count = countMapper.getCountByNameAndType(platenumber.getName(), "platenumber",currentUserId);

                  if (count != null) {

                      countVoOnlyFour.setMonthlyvisits(count.getMonthlyvisits());

                      countVoOnlyFour.setYearlyvisits(count.getYearlyvisits());

                  } else {

                      countVoOnlyFour.setMonthlyvisits(null);

                      countVoOnlyFour.setYearlyvisits(null);

                  }


                  if (platenumber.getLonandlat() != null) {

                      countVoOnlyFourList.add(countVoOnlyFour);

                  }

              }



          }






          for(int i=0;i<roadnumberTwoList.size();i++) {

              RoadnumberTwo roadnumber = roadnumberTwoList.get(i);


              if (roadnumber.getLonandlat() != null&&!roadnumber.getLonandlat().equals("")) {

                  CountVoOnlyFour countVoOnlyFour = new CountVoOnlyFour();

                  countVoOnlyFour.setName(roadnumber.getName());
                  countVoOnlyFour.setType("roadnumber");

                  String[] strings = roadnumber.getLonandlat().split(",");

                  countVoOnlyFour.setLon(Double.valueOf(strings[0]));
                  countVoOnlyFour.setLat(Double.valueOf(strings[1]));

                  Count count = countMapper.getCountByNameAndType(roadnumber.getName(), "roadnumber",currentUserId);

                  if (count != null) {
                      countVoOnlyFour.setMonthlyvisits(count.getMonthlyvisits());
                      countVoOnlyFour.setYearlyvisits(count.getYearlyvisits());
                  } else {
                      countVoOnlyFour.setMonthlyvisits(null);

                      countVoOnlyFour.setYearlyvisits(null);
                  }

                  if (roadnumber.getLonandlat() != null) {

                      countVoOnlyFourList.add(countVoOnlyFour);

                  }
              }



          }

          for(int i=0;i<scenicspotTwoList.size();i++){

              ScenicspotTwo scenicspot=scenicspotTwoList.get(i);



              if(scenicspot.getLonandlat()!=null&&!scenicspot.getLonandlat().equals("")) {
                  CountVoOnlyFour countVoOnlyFour=new CountVoOnlyFour();

                  countVoOnlyFour.setName(scenicspot.getName());
                  String[] strings = scenicspot.getLonandlat().split(",");

                  countVoOnlyFour.setLon(Double.valueOf(strings[0]));
                  countVoOnlyFour.setLat(Double.valueOf(strings[1]));
                  countVoOnlyFour.setType("scenicspot");

                  Count count=countMapper.getCountByNameAndType(scenicspot.getName(),"scenicspot",currentUserId);

                  if(count!=null){
                      countVoOnlyFour.setMonthlyvisits(count.getMonthlyvisits());
                      countVoOnlyFour.setYearlyvisits(count.getYearlyvisits());
                  }else{
                      countVoOnlyFour.setMonthlyvisits(null);

                      countVoOnlyFour.setYearlyvisits(null);
                  }

                  if(scenicspot.getLonandlat()!=null) {
                      countVoOnlyFourList.add(countVoOnlyFour);
                  }

              }



          }


          for(int i=0;i<villagenumberTwoList.size();i++){

              VillagenumberTwo villagenumber=villagenumberTwoList.get(i);



              if(villagenumber.getLonandlat()!=null&&!villagenumber.getLonandlat().equals("")) {
                  CountVoOnlyFour countVoOnlyFour=new CountVoOnlyFour();

                  countVoOnlyFour.setName(villagenumber.getName());
                  String[] strings = villagenumber.getLonandlat().split(",");

                  countVoOnlyFour.setLon(Double.valueOf(strings[0]));
                  countVoOnlyFour.setLat(Double.valueOf(strings[1]));
                  countVoOnlyFour.setType("villagenumber");

                  Count count=countMapper.getCountByNameAndType(villagenumber.getName(),"villagenumber",currentUserId);

                  if(count!=null){
                      countVoOnlyFour.setMonthlyvisits(count.getMonthlyvisits());
                      countVoOnlyFour.setYearlyvisits(count.getYearlyvisits());
                  }else{
                      countVoOnlyFour.setMonthlyvisits(null);

                      countVoOnlyFour.setYearlyvisits(null);
                  }

                  if(villagenumber.getLonandlat()!=null){
                      countVoOnlyFourList.add(countVoOnlyFour);

                  }
              }



          }


        //List<Count> countList=countMapper.getAlCount();

        return ResultVOUtil.success(countVoOnlyFourList );


    }



    @ApiOperation(value = "统计月访问量总量", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "key", value = "关键值判断1.门牌 2.道路 3.路牌 4.景区 5.村牌", required = false, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getAllMonthlyvisits")
    private ResultVO getAllMonthlyvisits(HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        Integer key=Integer.valueOf(request.getParameter("key"));

        if(key==1) {
            Integer count=null;
             count = countMapper.getPlateNumberMonthlyvisits(currentUserId);

             if(count==null){
                 count=0;
             }


            return ResultVOUtil.success(count);
        }

        if(key==2){

            Integer count=0;
             count=countMapper.getRoadMonthlyvisits(currentUserId);

            if(count==null){
                count=0;
            }
            return ResultVOUtil.success(count);

        }

        if(key==3){
            Integer count=0;
             count=countMapper.getRoadNumberMonthlyvisits(currentUserId);
            if(count==null){
                count=0;
            }
            return ResultVOUtil.success(count);

        }

        if(key==4){
            Integer count=0;
             count=countMapper.getScenicspotMonthlyvisits(currentUserId);
            if(count==null){
                count=0;
            }
            return ResultVOUtil.success(count);

        }

        if(key==5){
            Integer count=0;
             count=countMapper.getVillageNumberMonthlyvisits(currentUserId);
            if(count==null){
                count=0;
            }
            return ResultVOUtil.success(count);

        }

        return  ResultVOUtil.success();
    }




}
