package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Streetplana;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StreetplanaMapper {

    public void addStreetplana(@Param("name") String name,
                               @Param("pictureurl") String pictureurl,
                               @Param("status") Integer status,
                               @Param("userid") Integer userid,
                               @Param("type") String type,
                               @Param("abbreviation") String abbreviation

    );

    public List<Streetplana> selectStreetplana(@Param("name") String name, @Param("userid") Integer userid);

    public List<Streetplana> selectStreetplana2(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public void delStreetplana(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateStreetplana(@Param("name") String name,
                                  @Param("pictureurl") String pictureurl,
                                  @Param("type") String type,
                                  @Param("abbreviation") String abbreviation,

                                  @Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getStreetplanaCount(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public Streetplana getStreetplanaById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Streetplana getStreetplanaById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Streetplana getStreetplanaByMaxId(@Param("userid") Integer userid);

}
