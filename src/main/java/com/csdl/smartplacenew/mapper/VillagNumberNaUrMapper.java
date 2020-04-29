package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.two.VillagenumberNaUr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillagNumberNaUrMapper {

    public List<VillagenumberNaUr> getNameAndUrl(@Param("userid") Integer userid);

}
