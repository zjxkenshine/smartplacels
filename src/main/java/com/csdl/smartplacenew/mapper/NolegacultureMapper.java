package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Nolegaculture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NolegacultureMapper {


    public void addNolegaculture(@Param("name") String name,
                                 @Param("pictureurl") String pictureurl,
                                 @Param("status") Integer status,
                                 @Param("userid") Integer userid,
                                 @Param("abbreviation") String abbreviation);



    public List<Nolegaculture> selectNolegaculture(@Param("name") String name, @Param("userid") Integer userid);

    public List<Nolegaculture> selectNolegaculture2(@Param("name") String name, @Param("userid") Integer userid);

    public void delNolegaculture(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateNolegaculture(@Param("name") String name,
                                    @Param("pictureurl") String pictureurl,
                                    @Param("abbreviation") String abbreviation,

                                    @Param("id") Integer id,
                                    @Param("userid") Integer userid);

    public Integer getNolegacultureCount(@Param("name") String name, @Param("userid") Integer userid);

    public Nolegaculture getNolegacultureById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Nolegaculture getNolegacultureById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Nolegaculture getNolegacultureByMaxId(@Param("userid") Integer userid);


}
