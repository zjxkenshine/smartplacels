package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.information.Exceltest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExceltestMapper {

    List<String> getExceltestStart();



    List<Exceltest> getAllExceltest();



}
