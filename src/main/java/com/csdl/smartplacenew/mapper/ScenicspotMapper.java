package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.pojo.Scenicspot;
import com.csdl.smartplacenew.pojo.ScenicspotTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScenicspotMapper {

    public void addScenicspot(@Param("name") String name,
                              @Param("lonandlat") String lonandlat,
                              @Param("type") String type,
                              @Param("url") String url,
                              @Param("voiceurl") String voiceurl,
                              @Param("status") Integer status,

                              @Param("textofmater") String textofmater,
                              @Param("size") String size,
                              @Param("yearofesent") String yearofesent,
                              @Param("mainteunit") String mainteunit,
                              @Param("numofmaint") String numofmaint,
                              @Param("maintperiod") String maintperiod,
                              @Param("maintcost") String maintcost,
                              @Param("maintaccounts") String maintaccounts
            , @Param("userid") Integer userid,
                              @Param("sharelinks") String sharelinks,
                              @Param("englishword") String englishword

    );


    public Scenicspot getScenicspotById(@Param("id") Integer id, @Param("userid") Integer userid);


    void  updateScenicspotStatus(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateScenicspotById(@Param("name") String name,
                                     @Param("lonandlat") String lonandlat,
                                     @Param("type") String type,
                                     @Param("url") String url,
                                     @Param("voiceurl") String voiceurl,

                                     @Param("textofmater") String textofmater,
                                     @Param("size") String size,
                                     @Param("yearofesent") String yearofesent,
                                     @Param("mainteunit") String mainteunit,
                                     @Param("numofmaint") String numofmaint,
                                     @Param("maintperiod") String maintperiod,
                                     @Param("maintcost") String maintcost,
                                     @Param("maintaccounts") String maintaccounts,
                                     @Param("sharelinks") String sharelinks,
                                     @Param("englishword") String englishword,
                                     @Param("id") Integer id
            , @Param("userid") Integer userid);


    List<Scenicspot>  getScenicspotByKey(@Param("userid") Integer userid, @Param("key") String key);

   Integer  getScenicspotByKeyCount(@Param("userid") Integer userid, @Param("key") String key);


   public void addMaintrecordsOfScen(@Param("mainttype") String mainttype,
                                     @Param("number") String number,
                                     @Param("maintcontent") String maintcontent,
                                     @Param("maintcompany") String maintcompany,
                                     @Param("contacts") String contacts,
                                     @Param("contactsnum") String contactsnum,
                                     @Param("cost") String cost,
                                     @Param("mainttime") String mainttime
           , @Param("userid") Integer userid

   );


   Integer getScenidcIdByName(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);



    Integer getScenidcIdByName2(@Param("name") String name, @Param("userid") Integer userid);


    List<String>  getLonAndLat(@Param("userid") Integer userid);

    Scenicspot  getScenicpoByLonAndLat(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);

    Scenicspot getScenicspotByUrl(@Param("url") String url, @Param("userid") Integer userid);


    public void addCountOfScenicspot(@Param("name") String name, @Param("count2") Integer count2,
                                     @Param("monthlyvisits") Integer monthlyvisits, @Param("type") String type, @Param("status") Integer status,
                                     @Param("yearlyvisits") Integer yearlyvisits, @Param("userid") Integer userid);



    List<Scenicspot> getScenicspotByName(@Param("name") String name, @Param("userid") Integer userid);

    public void updateMandarinword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public void updateEnglishword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public String getUrlById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getIdByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public Integer  getMaxId(@Param("userid") Integer userid);

    public Integer  getMinId(@Param("userid") Integer userid);


    public void addLonAndLatByUrl(@Param("LonAndLat") String LonAndLat, @Param("url") String url, @Param("userid") Integer userid);

    public List<ScenicspotTwo> getNameAndLonLat(@Param("userid") Integer userid);


    public Integer getMaxIdByAll(@Param("userid") Integer userid);

    public Scenicspot getScenicspotByMaxId(@Param("userid") Integer userid);

    public  List<Scenicspot>  getNameAndUrl(@Param("userid") Integer userid);



}
