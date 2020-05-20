package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Picture;
import com.csdl.smartplacenew.pojo.Picture2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PictureMapper {

     public void addPicture(@Param("url") String url,
                            @Param("platenumberid") Integer platenumberid,
                            @Param("roadnumberid") Integer roadnumberid,
                            @Param("villagenumberid") Integer villagenumberid,
                            @Param("scenicspotid") Integer scenicspotid,
                            @Param("roadid") Integer roadid,
                            @Param("status") Integer status
             , @Param("userid") Integer userid
             , @Param("maintrecordsid") Integer maintrecordsid
     );


     Picture getPictureByUrl(@Param("url") String url, @Param("userid") Integer userid);


    List<String> getPictureByPlatenumberid(@Param("platenumberid") Integer platenumberid, @Param("userid") Integer userid);

    List<String>  getPictureByRoadnumberid(@Param("roadnumberid") Integer roadnumberid, @Param("userid") Integer userid);

    List<String>  getPictureByVillagenumberid(@Param("villagenumberid") Integer villagenumberid, @Param("userid") Integer userid);

    List<String>  getPictureByScenicspotid(@Param("scenicspotid") Integer scenicspotid, @Param("userid") Integer userid);

    List<String>  getPictureByRoadid(@Param("roadid") Integer roadid, @Param("userid") Integer userid);

    List<String>  getPictureByMaintreid(@Param("maintrecordsid") Integer maintrecordsid, @Param("userid") Integer userid);


    public void delPictureByPlatenumberid(@Param("platenumberid") Integer platenumberid, @Param("userid") Integer userid);

    public void delPictureByRoadnumberid(@Param("roadnumberid") Integer roadnumberid, @Param("userid") Integer userid);

    public void delPictureByVillagenumberid(@Param("villagenumberid") Integer villagenumberid, @Param("userid") Integer userid);

    public void delPictureByScenicspotid(@Param("scenicspotid") Integer scenicspotid, @Param("userid") Integer userid);

    public void delPictureByRoadid(@Param("roadid") Integer roadid, @Param("userid") Integer userid);


    public void delPictureByMaintrecordsid(@Param("maintrecordsid") Integer maintrecordsid, @Param("userid") Integer userid);

    //查询图片
    List<String>  getPictures(Picture2 picture2);

    //删除图片
    void deletePictureRoadnumberId(int roadnumberid, int userid);
}
