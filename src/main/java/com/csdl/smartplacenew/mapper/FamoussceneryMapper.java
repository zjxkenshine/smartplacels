package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Famousscenery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FamoussceneryMapper {

    public void addFamousscenery(@Param("name") String name, @Param("pictureurl") String pictureurl,
                                 @Param("status") Integer status, @Param("userid") Integer userid);

    public List<Famousscenery> selectFamousscenery(@Param("name") String name, @Param("userid") Integer userid);

    public List<Famousscenery> selectFamousscenery2(@Param("name") String name, @Param("userid") Integer userid);

    public void delFamousscenery(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateFamousscenery(@Param("name") String name, @Param("pictureurl") String pictureurl,
                                    @Param("id") Integer id
            , @Param("userid") Integer userid);

    public Integer getFamoussceneryCount(@Param("name") String name, @Param("userid") Integer userid);

    public Famousscenery getFamoussceneryById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Famousscenery getFamoussceneryById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Famousscenery getFamoussceneryByMaxId(@Param("userid") Integer userid);
}
