package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.PlatenumberTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlatenumberTwoMapper {



    public List<PlatenumberTwo>  getNameAndLonLat(@Param("userid") Integer userid);



}
