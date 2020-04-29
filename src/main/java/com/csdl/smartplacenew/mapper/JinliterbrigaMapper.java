package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.information.Jinliterbriga;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JinliterbrigaMapper {

    public void addJinliterbriga(@Param("name") String name,
                                 @Param("type") String type,
                                 @Param("pictureurl") String pictureurl,
                                 @Param("status") Integer status,
                                 @Param("userid") Integer userid);

    public List<Jinliterbriga> selectJinliterbriga(@Param("name") String name,
                                                   @Param("type") String type,
                                                   @Param("userid") Integer userid);

    public List<Jinliterbriga> selectJinliterbriga2(@Param("name") String name,
                                                    @Param("type") String type,
                                                    @Param("userid") Integer userid);

    public List<Jinliterbriga> selectAllJinliterbriga();

    public void delJinliterbriga(@Param("id") Integer id,
                                 @Param("userid") Integer userid);

    public void updateJinliterbriga(@Param("name") String name,
                                    @Param("pictureurl") String pictureurl,
                                    @Param("id") Integer id,
                                    @Param("userid") Integer userid);

    public Integer getJinliterbrigaCount(@Param("name") String name,
                                         @Param("type") String type,
                                         @Param("userid") Integer userid);

    public Jinliterbriga getJinliterbrigaById(@Param("id") Integer id,
                                              @Param("userid") Integer userid);

    public Jinliterbriga getJinliterbrigaById2(@Param("id") Integer id,
                                               @Param("userid") Integer userid);

    public Jinliterbriga getJinliterbrigaByMaxId(@Param("userid") Integer userid);

    public List<Jinliterbriga> getJinliterbrigaByNameAndUserid(@Param("name") String name, @Param("type") String type, @Param("userid") Integer userid);


}
