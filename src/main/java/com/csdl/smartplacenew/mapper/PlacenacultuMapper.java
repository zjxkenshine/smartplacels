package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Placenacultu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlacenacultuMapper {

    public void addPlacenacultu(@Param("name") String name,
                                @Param("type") String type,
                                @Param("pictureurl") String pictureurl,
                                @Param("status") Integer status,
                                @Param("userid") Integer userid,
                                @Param("abbreviation") String abbreviation);

    public List<Placenacultu> selectPlacenacultu(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public List<Placenacultu> selectPlacenacultu2(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public List<Placenacultu> selectAllPlacenacultu();

    public void delPlacenacultu(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updatePlacenacultu(@Param("name") String name,
                                   @Param("pictureurl") String pictureurl,

                                   @Param("abbreviation") String abbreviation,
                                   @Param("id") Integer id,
                                   @Param("userid") Integer userid);

    public Integer getPlacenacultuCount(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);

    public Placenacultu getPlacenacultuById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Placenacultu getPlacenacultuById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Placenacultu getPlacenacultuByMaxId(@Param("userid") Integer userid);
}
