package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Focustoms;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FocustomsMapper {

    public void addFocustoms(@Param("name") String name,
                             @Param("pictureurl") String pictureurl,
                             @Param("status") Integer status,
                             @Param("userid") Integer userid,
                             @Param("type") String type,
                             @Param("abbreviation") String abbreviation

    );

    public List<Focustoms> selectFocustoms(@Param("name") String name, @Param("userid") Integer userid);

    public List<Focustoms> selectFocustoms2(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public void delFocustoms(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateFocustoms(@Param("name") String name,
                                @Param("pictureurl") String pictureurl,
                                @Param("type") String type,
                                @Param("abbreviation") String abbreviation,

                                @Param("id") Integer id,
                                @Param("userid") Integer userid);


    public Integer getFocustomsCount(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public Focustoms getFocustomsById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Focustoms getFocustomsById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Focustoms getFocustomsByMaxId(@Param("userid") Integer userid);



}
