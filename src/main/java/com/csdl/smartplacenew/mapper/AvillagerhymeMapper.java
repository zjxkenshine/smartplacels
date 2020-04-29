package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Avillagerhyme;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AvillagerhymeMapper  {

   public void addAvillagerhyme(@Param("name") String name,
                                @Param("pictureurl") String pictureurl,
                                @Param("status") Integer status,
                                @Param("lonAndLat") String lonAndLat,
                                @Param("link") String link,
                                @Param("userid") Integer userid);

   public List<Avillagerhyme> selectAvillagerhymeByName(@Param("name") String name, @Param("userid") Integer userid);

   public List<Avillagerhyme> selectAvillagerhymeByName2(@Param("name") String name, @Param("userid") Integer userid);

   public void delAvillagerhyme(@Param("id") Integer id, @Param("userid") Integer userid);

   public void updateAvillagerhyme(@Param("name") String name,
                                   @Param("pictureurl") String pictureurl,
                                   @Param("lonAndLat") String lonAndLat,
                                   @Param("link") String link,
                                   @Param("id") Integer id,

                                   @Param("userid") Integer userid);

   public Integer getAvillagerhymeCount(@Param("name") String name, @Param("userid") Integer userid);

   public Avillagerhyme getAvillagerhymeById(@Param("id") Integer id, @Param("userid") Integer userid);

   public Avillagerhyme getAvillagerhymeById2(@Param("id") Integer id, @Param("userid") Integer userid);

   public Avillagerhyme getAvillagerhymeByMaxId(@Param("userid") Integer userid);

   public  Avillagerhyme getAvillagerhymeByName(@Param("name") String name, @Param("userid") Integer userid);

   public  Avillagerhyme getAvillagerhymeByName2(@Param("name") String name);


}
