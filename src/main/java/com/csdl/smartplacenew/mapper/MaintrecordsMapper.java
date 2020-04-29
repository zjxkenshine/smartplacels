package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Maintrecords;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MaintrecordsMapper {

   List<Maintrecords>  findAllMaintrecores(@Param("maintType") String maintType, @Param("userid") Integer userid);

   Integer getAllCount(@Param("maintType") String maintType, @Param("userid") Integer userid);

   public Integer getCostCount(@Param("userid") Integer userid, @Param("time") String time);

   List<Maintrecords> getAllMaintrecores(@Param("userid") Integer userid, @Param("time") String time);


   public Maintrecords getMaxMainrecord();




}
