package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Roadnumber;
import com.csdl.smartplacenew.pojo.RoadnumberTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoadnumberMapper {

    public void addRoadnumber(
            @Param("roadnum") String roadnum,
            @Param("name") String name,
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

    public Roadnumber getRoadnumberById(@Param("id") Integer id, @Param("userid") Integer userid);

    void updateRoadnumberStatus(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateRoadnumberById(
            @Param("roadnum") String roadnum,
            @Param("name") String name,
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
            @Param("id") Integer id,
            @Param("userid") Integer userid);

    List<Roadnumber> getRoadnumberByKey(@Param("userid") Integer userid, @Param("key") String key);

    Integer getRoadnumberByKeyCount(@Param("userid") Integer userid, @Param("key") String key);



    public  void addMaintrecordsOfRoadNum(
            @Param("mainttype") String mainttype,
            @Param("number") String number,
            @Param("maintcontent") String maintcontent,
            @Param("maintcompany") String maintcompany,
            @Param("contacts") String contacts,
            @Param("contactsnum") String contactsnum,
            @Param("cost") String cost,
            @Param("mainttime") String mainttime
            , @Param("userid") Integer userid


    );

    Integer getRoadNumIdByName(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);

    Integer getRoadNumIdByName2(@Param("name") String name, @Param("userid") Integer userid);

    List<String> getLonAndLat(@Param("userid") Integer userid);

    Roadnumber getRoadNumberByLonAndLat(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);

    Roadnumber getRoadNumberByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public void addCountOfRoadNumber(@Param("name") String name, @Param("count2") Integer count2,
                                     @Param("monthlyvisits") Integer monthlyvisits, @Param("type") String type, @Param("status") Integer status,
                                     @Param("yearlyvisits") Integer yearlyvisits, @Param("userid") Integer userid);



    List<Roadnumber> getRoadNumByName(@Param("name") String name, @Param("userid") Integer userid);


    public void updateMandarinword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public void updateEnglishword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public String getUrlById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getIdByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public Integer  getMaxId(@Param("userid") Integer userid);

    public Integer  getMinId(@Param("userid") Integer userid);

    public void addLonAndLatByUrl(@Param("LonAndLat") String LonAndLat, @Param("url") String url, @Param("userid") Integer userid);

    public List<RoadnumberTwo> getNameAndLonLat(@Param("userid") Integer userid);


    public Integer getMaxIdByAll(@Param("userid") Integer userid);

    public Roadnumber getRoadnumberByMaxId(@Param("userid") Integer userid) ;

    public List<Roadnumber> getNameAndUrl(@Param("userid") Integer userid);



    public String getNameByUrlAndUserId(@Param("url") String url, @Param("userid") Integer userid);

    public  List<Roadnumber> getRoadnumberByNameAndUserId(@Param("name") String name, @Param("userid") Integer userid);

    public String getUrlByNameAndUserId(@Param("name") String name, @Param("userid") Integer userid);


    public  Integer getOrderIndexRoadNumberByUserid(@Param("url") String url, @Param("userid") Integer userid);

    public  Integer  getMaxOrderIndexRoadNumberByUserId(@Param("userid") Integer userid);

    public  Integer getMinOrderIndexRoadNumberByUserId(@Param("userid") Integer userid);

    public String getUrlByOrderIndexRoadNumberByUserId(@Param("orderindex") Integer orderindex,
                                                       @Param("userid") Integer userid
    ) ;



}
