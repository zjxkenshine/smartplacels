package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Audio;
import com.csdl.smartplacenew.pojo.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AudioMapper {

     public void addAudio(@Param("url") String url,
                          @Param("platenumberid") Integer platenumberid,
                          @Param("roadnumberid") Integer roadnumberid,
                          @Param("villagenumberid") Integer villagenumberid,
                          @Param("scenicspotid") Integer scenicspotid,
                          @Param("roadid") Integer roadid,
                          @Param("status") Integer status,
                          @Param("userId") Integer userid
     );


     Picture getAudioByUrl(@Param("url") String url);


    List<String> getAudioByPlatenumberid(@Param("platenumberid") Integer platenumberid, @Param("userid") Integer userid);

    List<String>  getAudioByRoadnumberid(@Param("roadnumberid") Integer roadnumberid, @Param("userid") Integer userid);

    List<String>  getAudioByVillagenumberid(@Param("villagenumberid") Integer villagenumberid, @Param("userid") Integer userid);

    List<String>  getAudioByScenicspotid(@Param("scenicspotid") Integer scenicspotid, @Param("userid") Integer userid);

    List<String>  getAudioByRoadid(@Param("roadid") Integer roadid, @Param("userid") Integer userid);




    List<Audio> getAudioListByPlatenumberid(@Param("platenumberid") Integer platenumberid);

    List<Audio>  getAudioListByRoadnumberid(@Param("roadnumberid") Integer roadnumberid);

    List<Audio>  getAudioListByVillagenumberid(@Param("villagenumberid") Integer villagenumberid);

    List<Audio>  getAudioListByScenicspotid(@Param("scenicspotid") Integer scenicspotid);

    List<Audio>  getAudioListByRoadid(@Param("roadid") Integer roadid);



    public void delAudioByPlatenumberUrl(@Param("url") String url, @Param("userid") Integer userid);

    public void delAudioByRoadnumberUrl(@Param("url") String url, @Param("userid") Integer userid);

    public void delAudioByVillagenumberUrl(@Param("url") String url, @Param("userid") Integer userid);

    public void delAudioByScenicspotUrl(@Param("url") String url, @Param("userid") Integer userid);

    public void delAudioByRoadUrl(@Param("url") String url, @Param("userid") Integer userid);



}
