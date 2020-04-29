package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.VillagenumberSY;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillagenumberSYMapper {

    VillagenumberSY getVillagenumberSYByName(@Param("name") String name, @Param("userid") Integer userid);

    VillagenumberSY getByText(@Param("text") String text);

    List<VillagenumberSY> getAllVillagenumberSY(@Param("userid") Integer userid);

    public void updatePictUrl(@Param("pictureurl") String pictureurl, @Param("name") String name);

    public VillagenumberSY getFeiYiAndMinSu(@Param("id") Integer id);

    public void addVillageNumberSy(@Param("name") String name,
                                   @Param("text") String text,
                                   @Param("url") String url,
                                   @Param("userid") Integer userid,
                                   @Param("chuantongjianzhu") String chuantongjianzhu,
                                   @Param("feiyiwenhua") String feiyiwenhuam,
                                   @Param("minsuwenhua") String minsuwenhua
    );

    public VillagenumberSY getVillagenumberSYById(@Param("userid") Integer userid);

    public void updateVillageNumberSy(

            @Param("feiyiwenhua") String feiyiwenhuam,
            @Param("minsuwenhua") String minsuwenhua,
            @Param("id") Integer id);

    public VillagenumberSY getVillagenumberSYByName2(@Param("name") String name);
}
