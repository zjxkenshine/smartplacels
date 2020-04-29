package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.two.RoadNumberNaUr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoadNumberNaUrMapper {

    public List<RoadNumberNaUr> getNameAndUrl(@Param("userid") Integer userid);

}
