package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Gazetteer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GazetteerMapper {

    public void addGazetteer(@Param("name") String name,
                             @Param("pictureurl") String pictureurl,
                             @Param("status") Integer status,
                             @Param("userid") Integer userid);

    public List<Gazetteer> selectGazetteer(@Param("name") String name,
                                           @Param("userid") Integer userid);

    public List<Gazetteer> selectGazetteer2(@Param("name") String name,
                                            @Param("userid") Integer userid);

    public void delGazetteer(@Param("id") Integer id,
                             @Param("userid") Integer userid);

    public void updateGazetteer(@Param("name") String name,
                                @Param("pictureurl") String pictureurl,
                                @Param("id") Integer id,
                                @Param("userid") Integer userid);

    public Integer getGazetteerCount(@Param("name") String name,
                                     @Param("userid") Integer userid);

    public Gazetteer getGazetteerById(@Param("id") Integer id,
                                      @Param("userid") Integer userid);

    public Gazetteer getGazetteerById2(@Param("id") Integer id,
                                       @Param("userid") Integer userid);

    public Gazetteer getGazetteerByMaxId(@Param("userid") Integer userid);

}
