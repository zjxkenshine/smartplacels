package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.pojo.Admindivsion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdmindivsionMapper {


    Admindivsion getAdminvisionByName(@Param("name") String name, @Param("userid") Integer userid);

     Admindivsion getByText(@Param("text") String text);

    List<Admindivsion> getAllAdminvision(@Param("userid") Integer userid);

    public void updatePictUrl(@Param("pictureurl") String pictureurl, @Param("name") String name);

    public void addAllAdminvision(@Param("name") String name,
                                  @Param("text") String text,

                                  @Param("lon") String lon,
                                  @Param("lat") String lat,

                                  @Param("userid") Integer userid

    );


    public void updateAllAdminvision(

            @Param("name") String name,
            @Param("text") String text,
            @Param("lon") String lon,
            @Param("lat") String lat,
            @Param("userid") Integer userid,
            @Param("id") Integer id

    );


    Admindivsion getAdmindivsionById(@Param("id") Integer id,
                                     @Param("userid") Integer userid

    );

    List<Admindivsion> getAdmindivsionByNameAndUserId(@Param("name") String name,
                                                      @Param("userid") Integer userid
    );


    void delAdmindivsionByIdAndUserid(@Param("id") Integer id,
                                      @Param("userid") Integer userid
    );



}


