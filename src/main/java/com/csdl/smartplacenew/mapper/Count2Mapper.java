package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Count2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface Count2Mapper {

    //添加访问量
    void addCount(@Param("userId") int userId, @Param("date") Date date);

    //获取7天的访问数据
    List<Count2> getSevenCounts(@Param("userId") int userId, @Param("date") Date date);

    //获取总访问量
    int getTotalCounts(@Param("userId") int userId);

}
