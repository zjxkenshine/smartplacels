package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Imageplacenames;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ImageplacenamesMapper {


    public void addImageplacenames(@Param("name") String name,
                                   @Param("pictureurl") String pictureurl,
                                   @Param("link") String link,
                                   @Param("status") Integer status,
                                   @Param("userid") Integer userid);

    public List<Imageplacenames> selectImageplacenames(@Param("name") String name,

                                                       @Param("userid") Integer userid);

    public List<Imageplacenames> selectImageplacenames2(@Param("name") String name,

                                                        @Param("userid") Integer userid);

    public List<Imageplacenames> selectAllImageplacenames();

    public void delImageplacenames(@Param("id") Integer id,
                                   @Param("userid") Integer userid);

    public void updateImageplacenames(@Param("name") String name,
                                      @Param("pictureurl") String pictureurl,
                                      @Param("link") String link,
                                      @Param("id") Integer id,
                                      @Param("userid") Integer userid);

    public Integer getImageplacenamesCount(@Param("name") String name,

                                           @Param("userid") Integer userid);

    public Imageplacenames getImageplacenamesById(@Param("id") Integer id,
                                                  @Param("userid") Integer userid);

    public Imageplacenames getImageplacenamesById2(@Param("id") Integer id,
                                                   @Param("userid") Integer userid);

    public Imageplacenames getImageplacenamesByMaxId(@Param("userid") Integer userid);

    public List<Imageplacenames> getImageplacenamesByNameAndUserid(@Param("name") String name, @Param("userid") Integer userid);


}
