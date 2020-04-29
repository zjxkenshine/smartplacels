package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.information.Stangeogrnames;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StangeogrnamesMapper {

    public void addStangeogrnames(@Param("name") String name,
                                  @Param("pictureurl") String pictureurl,
                                  @Param("status") Integer status,
                                  @Param("userid") Integer userid);

    public List<Stangeogrnames> selectStangeogrnames(@Param("name") String name,
                                                     @Param("userid") Integer userid);

    public List<Stangeogrnames> selectStangeogrnames2(@Param("name") String name,
                                                      @Param("userid") Integer userid);

    public void delStangeogrnames(@Param("id") Integer id,
                                  @Param("userid") Integer userid);

    public void updateStangeogrnames(@Param("name") String name,
                                     @Param("pictureurl") String pictureurl,
                                     @Param("id") Integer id,
                                     @Param("userid") Integer userid);

    public Integer getStangeogrnamesCount(@Param("name") String name, @Param("userid") Integer userid);

    public Stangeogrnames getStangeogrnamesById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Stangeogrnames getStangeogrnamesById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Stangeogrnames getStangeogrnamesByMaxId(@Param("userid") Integer userid);

}
