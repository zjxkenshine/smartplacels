package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.VillagenumberTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillagenumberTwoMapper {


    public List<VillagenumberTwo>  getNameAndLatLon(@Param("userid") Integer userid);


}
