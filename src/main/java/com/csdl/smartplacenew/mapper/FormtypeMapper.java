package com.csdl.smartplacenew.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FormtypeMapper {

   public void addFormtype(@Param("pointpositiontype") String pointpositiontype, @Param("status") Integer status
           , @Param("userid") Integer userid);

}
