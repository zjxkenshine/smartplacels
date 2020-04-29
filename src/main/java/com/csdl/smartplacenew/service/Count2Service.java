package com.csdl.smartplacenew.service;

import com.csdl.smartplacenew.constant.PlaceEnum;
import com.csdl.smartplacenew.mapper.Count2Mapper;
import com.csdl.smartplacenew.pojo.Count2;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.csdl.smartplacenew.util.DateUtils.getPastDate;

@Service
public class Count2Service {

    @Resource
    Count2Mapper count2Mapper;


    //添加总数
    public ResultVO addCount(String place) throws ParseException {
        Date addTime=getPastDate(0);
        String code= PlaceEnum.getCode(place);
        if(code==null){
            return ResultVOUtil.error("404","无此地区");
        }
        count2Mapper.addCount(Integer.parseInt(code),addTime);
        return ResultVOUtil.success(null);
    }


    //7天访问量
    public ResultVO getSevenCounts(Integer userId) throws ParseException {
        //获取七天前日期
        Date time= getPastDate(7);
        List<Count2> count2List=count2Mapper.getSevenCounts(userId,time);
        return ResultVOUtil.success(count2List);
    }

    //总访问量
    public ResultVO getTotalCounts(Integer userId){
        int count=count2Mapper.getTotalCounts(userId);
        Map<String,Object> hashMap=new HashMap<>();
        hashMap.put("count",count);
        return ResultVOUtil.success(hashMap);
    }


}
