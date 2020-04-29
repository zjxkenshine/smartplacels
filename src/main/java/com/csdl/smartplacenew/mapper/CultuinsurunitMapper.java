package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.information.Cultuinsurunit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CultuinsurunitMapper {

    public void addCultuinsurunit(@Param("name") String name, @Param("pictureurl") String pictureurl,
                                  @Param("status") Integer status, @Param("userid") Integer userid);

    public List<Cultuinsurunit> selectCultuinsurunitByName(@Param("name") String name, @Param("userid") Integer userid);

    public List<Cultuinsurunit> selectCultuinsurunitByName2(@Param("name") String name, @Param("userid") Integer userid);

    public void delCultuinsurunit(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateCultuinsurunit(@Param("name") String name, @Param("pictureurl") String pictureurl,
                                     @Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getCultuinsurunitCount(@Param("name") String name, @Param("userid") Integer userid);

    public Cultuinsurunit getCultuinsurunitById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Cultuinsurunit getCultuinsurunitById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Cultuinsurunit getCultuinsurunitByMaxId(@Param("userid") Integer userid);

    public  List<Cultuinsurunit>  getCultuinsurunitByNameAndUserid(@Param("name") String name, @Param("userid") Integer userid);


    public Cultuinsurunit getCultuinsurunit(@Param("name") String name, @Param("userid") Integer userid);

}
