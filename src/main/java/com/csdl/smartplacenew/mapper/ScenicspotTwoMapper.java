package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.pojo.ScenicspotTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScenicspotTwoMapper {



    public List<ScenicspotTwo> getNameAndLonLat(@Param("userid") Integer userid);


}
