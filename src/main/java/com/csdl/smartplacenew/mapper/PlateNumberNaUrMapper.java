package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.two.PlateNumberNaUr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlateNumberNaUrMapper {

    public List<PlateNumberNaUr> getNameAndUrl(@Param("userid") Integer userid);

}
