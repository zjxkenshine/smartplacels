package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.RoadTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoadTwoMapper {



    public List<RoadTwo> getNameAndLonLat(@Param("userid") Integer userid);



}
