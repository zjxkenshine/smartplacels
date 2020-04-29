package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.two.ScenicspotNaUr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScenicspotNaUrMapper {

    public List<ScenicspotNaUr> getNameAndUrl(@Param("userid") Integer userid);

}
