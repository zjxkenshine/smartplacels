package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.information.Leisuretrip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LeisuretripMapper {

    public void addLeisuretrip(@Param("name") String name, @Param("pictureurl") String pictureurl,
                               @Param("status") Integer status, @Param("userid") Integer userid);

    public List<Leisuretrip> selectLeisuretripByName(@Param("name") String name, @Param("userid") Integer userid);

    public List<Leisuretrip> selectLeisuretripByName2(@Param("name") String name, @Param("userid") Integer userid);

    public void delLeisuretrip(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateLeisuretrip(@Param("name") String name, @Param("pictureurl") String pictureurl,
                                  @Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getLeisuretripCount(@Param("name") String name, @Param("userid") Integer userid);

    public Leisuretrip getLeisuretripById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Leisuretrip getLeisuretripById2(@Param("id") Integer id, @Param("userid") Integer userid);

    public Leisuretrip getLeisuretripByMaxId(@Param("userid") Integer userid);

    List<Leisuretrip>  getLeisuretripByNameAndUserid(@Param("name") String name, @Param("userid") Integer userid);


}
