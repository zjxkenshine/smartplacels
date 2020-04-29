package com.csdl.smartplacenew.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface Exceltest2Mapper {

   void addExceltest2(@Param("id") Integer id,
                      @Param("start") String start,
                      @Param("name") String name,
                      @Param("x") String x,
                      @Param("y") String y,
                      @Param("z") String z,
                      @Param("end") String end,
                      @Param("d") String d

   );

}
