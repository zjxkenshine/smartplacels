package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.information.Dictionaryplacenames;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictionaryplacenamesMapper {

    public void addDictionaryplacenames(@Param("name") String name,
                                        @Param("pictureurl") String pictureurl,
                                        @Param("status") Integer status,
                                        @Param("userid") Integer userid);

    public List<Dictionaryplacenames> selectDictionaryplacenames(@Param("name") String name,
                                                                 @Param("userid") Integer userid);

    public List<Dictionaryplacenames> selectDictionaryplacenames2(@Param("name") String name,
                                                                  @Param("userid") Integer userid);

    public void delDictionaryplacenames(@Param("id") Integer id,
                                        @Param("userid") Integer userid);

    public void updateDictionaryplacenames(@Param("name") String name,
                                           @Param("pictureurl") String pictureurl,
                                           @Param("id") Integer id,
                                           @Param("userid") Integer userid);

    public Integer getDictionaryplacenamesCount(@Param("name") String name,
                                                @Param("userid") Integer userid);

    public Dictionaryplacenames getDictionaryplacenamesById(@Param("id") Integer id,
                                                            @Param("userid") Integer userid);

    public Dictionaryplacenames getDictionaryplacenamesById2(@Param("id") Integer id,
                                                             @Param("userid") Integer userid);

    public Dictionaryplacenames getDictionaryplacenamesByMaxId(@Param("userid") Integer userid);
}
