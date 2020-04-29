package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.RoadnumberTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoadnumberTwoMapper {


    public List<RoadnumberTwo> getNameAndLonLat(@Param("userid") Integer userid);



}
