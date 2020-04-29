package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Chafood;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChafoodMapper {

    public void addChafood(@Param("name") String name, @Param("pictureurl") String pictureurl,
                           @Param("status") Integer status, @Param("userid") Integer userid);

    public List<Chafood> selectChafoodByName(@Param("name") String name, @Param("userid") Integer userid);

    public List<Chafood> selectChafoodByName2(@Param("name") String name, @Param("userid") Integer userid);

    public void delChafood(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateChafood(@Param("name") String name, @Param("pictureurl") String pictureurl,
                              @Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getChafoodCount(@Param("name") String name, @Param("userid") Integer userid);

    public Chafood getChafoodById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Chafood getChafoodById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Chafood getChafoodByMaxId(@Param("userid") Integer userid);


}
