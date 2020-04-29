package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.two.RoadNaUr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoadNaUrMapper {

    public List<RoadNaUr> getNameAndUrl(@Param("userid") Integer userid);

}
