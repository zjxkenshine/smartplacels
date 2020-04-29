package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Ocplacenames;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OcplacenamesMapper {


    public void addOcplacenames(@Param("name") String name,
                                @Param("pictureurl") String pictureurl,
                                @Param("status") Integer status,
                                @Param("userid") Integer userid,
                                @Param("abbreviation") String abbreviation

    );

    public List<Ocplacenames> selectOcplacenames(@Param("name") String name, @Param("userid") Integer userid);

    public List<Ocplacenames> selectOcplacenames2(@Param("name") String name, @Param("userid") Integer userid);

    public void delOcplacenames(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateOcplacenames(@Param("name") String name,
                                   @Param("pictureurl") String pictureurl,
                                   @Param("abbreviation") String abbreviation,
                                   @Param("id") Integer id,
                                   @Param("userid") Integer userid);

    public Integer getOcplacenamesCount(@Param("name") String name, @Param("userid") Integer userid);

    public Ocplacenames getOcplacenamesById(@Param("id") Integer id, @Param("userid") Integer userid);


    public Ocplacenames getOcplacenamesById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Ocplacenames getOcplacenamesByMaxId(@Param("userid") Integer userid);



}
