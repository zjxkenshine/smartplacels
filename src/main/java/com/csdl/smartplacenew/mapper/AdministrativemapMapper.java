package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.information.Administrativemap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AdministrativemapMapper {

    public void addAdministrativemap(@Param("name") String name,
                                     @Param("pictureurl") String pictureurl,
                                     @Param("status") Integer status,
                                     @Param("userid") Integer userid);

    public List<Administrativemap> selectAdministrativemap(@Param("name") String name,
                                                           @Param("userid") Integer userid);

    public List<Administrativemap> selectAdministrativemap2(@Param("name") String name,
                                                            @Param("userid") Integer userid);

    public void delAdministrativemape(@Param("id") Integer id,
                                      @Param("userid") Integer userid);

    public void updateAdministrativemap(@Param("name") String name,
                                        @Param("pictureurl") String pictureurl,
                                        @Param("id") Integer id,
                                        @Param("userid") Integer userid);

    public Integer getAdministrativemapCount(@Param("name") String name,
                                             @Param("userid") Integer userid);

    public Administrativemap getAdministrativemapById(@Param("id") Integer id,
                                                      @Param("userid") Integer userid);

    public Administrativemap getAdministrativemapById2(@Param("id") Integer id,
                                                       @Param("userid") Integer userid);

    public Administrativemap getAdministrativemapByMaxId(@Param("userid") Integer userid);
}
