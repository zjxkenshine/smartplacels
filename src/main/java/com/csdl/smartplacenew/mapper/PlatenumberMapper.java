package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.Platenumber;
import com.csdl.smartplacenew.pojo.PlatenumberTwo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlatenumberMapper {

    public void addPlateNumber(@Param("name") String name,

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

    public Platenumber getNumberplateById(@Param("id") Integer id, @Param("userid") Integer userid);

    void updateNumberplateStatus(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateNumberplateById(
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

            @Param("id") Integer id
            , @Param("userid") Integer userid

    );

    List<Platenumber> getNumberplateByKey(@Param("userid") Integer userid, @Param("key") String key);



    Integer  getNumberplateByKeyCount(@Param("userid") Integer userid, @Param("key") String key);


    Integer getNameCount(@Param("name") String name, @Param("userid") Integer userid);

    List<Platenumber> getPlateNumber(@Param("userid") Integer userid);


    public void addMaintrecordsOfPlateNum(@Param("mainttype") String mainttype,
                                          @Param("number") String number,
                                          @Param("maintcontent") String maintcontent,
                                          @Param("maintcompany") String maintcompany,
                                          @Param("contacts") String contacts,
                                          @Param("contactsnum") String contactsnum,
                                          @Param("cost") String cost,
                                          @Param("mainttime") String mainttime
            , @Param("userid") Integer userid

    );//维护记录

    Integer getPlateNumIdByName(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid) ;

    Integer getPlateNumIdByName2(@Param("name") String name, @Param("userid") Integer userid) ;


    List<String>  getLonAndLat(@Param("userid") Integer userid);

    Platenumber getPlatenuByLonAndLat(@Param("lonAndLat") String lonAndLat, @Param("userid") Integer userid);


    Platenumber getPlatenuByUrl(@Param("url") String url, @Param("userid") Integer userid);


    Platenumber  getPlateNumByUrl(@Param("url") String url, @Param("userid") Integer userid);


    public void addPlateNumCount(@Param("name") String name, @Param("count2") Integer count2,
                                 @Param("monthlyvisits") Integer monthlyvisits, @Param("type") String type, @Param("status") Integer status,
                                 @Param("yearlyvisits") Integer yearlyvisits, @Param("userid") Integer userid);


    List<Platenumber>  getPlateNumByName(@Param("name") String name, @Param("userid") Integer userid);


   public void updateMandarinword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public void updateEnglishword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public String getUrlById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getIdByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public Integer  getMaxId(@Param("userid") Integer userid);

    public Integer  getMinId(@Param("userid") Integer userid);

    public Platenumber getPlateNumberByLonAndLat(@Param("lonAndlat") String lonAndLat, @Param("userid") Integer userid);

    public void addLonAndLatByUrl(@Param("LonAndLat") String LonAndLat, @Param("url") String url, @Param("userid") Integer userid);

    public List<PlatenumberTwo>  getNameAndLonLat(@Param("userid") Integer userid);

    public Integer getMaxIdByAll(@Param("userid") Integer userid);

    public Platenumber getPlateNumberByMaxId(@Param("userid") Integer userid);

    public List<Platenumber> getNameAndUrl(@Param("userid") Integer userid);


    public String getNameByUrlAndUserId(@Param("url") String url, @Param("userid") Integer userid);




    public Integer getOrderByUseriId(@Param("url") String url, @Param("userid") Integer userid);

    public Integer getMaxOrderByUseriId(@Param("userid") Integer userid);

    public Integer getMinOrderByUseriId(@Param("userid") Integer userid);

    public String
    getUrlByOrderindexByUserId(@Param("orderindex") Integer orderindexm,
                               @Param("userid") Integer userid
    );



}
