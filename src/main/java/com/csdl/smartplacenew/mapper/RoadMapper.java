package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoadMapper {

    public void addRoad(@Param("streetnumber") String streetnumber,
                        @Param("name") String name,
                        @Param("lonandlat") String lonandlat,
                        @Param("type") String type,
                        @Param("codeaddress") String codeaddress,
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

    public Road getRoadById(@Param("id") Integer id, @Param("userid") Integer userid);

    void updateRoadStatus(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateRoadById(@Param("streetnumber") String streetnumber,
                               @Param("name") String name,
                               @Param("lonandlat") String lonandlat,
                               @Param("type") String type,
                               @Param("codeaddress") String codeaddress,
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
            , @Param("userid") Integer userid


    );

    List<Road> getRoadByKey(@Param("userid") Integer userid, @Param("key") String key);


    Integer getRoadByKeyCount(@Param("userid") Integer userid, @Param("key") String key);


    public  void addMaintrecordsOfRoad(@Param("mainttype") String mainttype,
                                       @Param("number") String number,
                                       @Param("maintcontent") String maintcontent,
                                       @Param("maintcompany") String maintcompany,
                                       @Param("contacts") String contacts,
                                       @Param("contactsnum") String contactsnum,
                                       @Param("cost") String cost,
                                       @Param("mainttime") String mainttime
            , @Param("userid") Integer userid

    );

    Integer getRoadIdByName(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);

    Integer getRoadIdByName2(@Param("name") String name, @Param("userid") Integer userid);

    List<String> getLonAndLat(@Param("userid") Integer userid);

      Road   getRoadByLonAndLat(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid) ;

    Road getRoadByUrl(@Param("url") String url, @Param("userid") Integer userid);


    public void addCountOfRoad(@Param("name") String name, @Param("count2") Integer count2,
                               @Param("monthlyvisits") Integer monthlyvisits, @Param("type") String type, @Param("status") Integer status,
                               @Param("yearlyvisits") Integer yearlyvisits, @Param("userid") Integer userid);


    List<Road>  getRoadByName(@Param("name") String name, @Param("userid") Integer userid);


    public void updateMandarinword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public void updateEnglishword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);

    public String getUrlById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getIdByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public Integer  getMaxId(@Param("userid") Integer userid);

    public Integer  getMinId(@Param("userid") Integer userid);

    public void addLonAndLatByUrl(@Param("LonAndLat") String LonAndLat, @Param("url") String url, @Param("userid") Integer userid);

    public List<RoadTwo> getNameAndLonLat(@Param("userid") Integer userid);


    public Integer getMaxIdByAll(@Param("userid") Integer userid);

    public Road getRoadByMaxId(@Param("userid") Integer userid);

    public List<Road> getNameAndUrl(@Param("userid") Integer userid);


}
