package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Count;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CountMapper {

    List<Count> getPlateNumberCount(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    Count getCountByNameAndType(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);


    void  updatePlateNumCount(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);



    void updatePlateNumStatus(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);


    void updatePlateCount2(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    void updatePlateCount3(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    void updatePlateCount4(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);


    Integer getCountOfAll(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);


    public void updateMonthlyvisits();


    public List<Count> getAlCount();


    void updateCountName(@Param("aftername") String aftername, @Param("oldname") String oldname, @Param("type") String type, @Param("userid") Integer userid);

    public Integer getPlateNumberMonthlyvisits(@Param("userid") Integer userid);

    public Integer getRoadMonthlyvisits(@Param("userid") Integer userid);

    public Integer getRoadNumberMonthlyvisits(@Param("userid") Integer userid);

    public Integer getScenicspotMonthlyvisits(@Param("userid") Integer userid);

    public Integer getVillageNumberMonthlyvisits(@Param("userid") Integer userid);
}
