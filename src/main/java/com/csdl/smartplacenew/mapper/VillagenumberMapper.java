package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VillagenumberMapper {

    public void addVillagenumber(@Param("name") String name,
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




    public void addVillagenumberMandarin(@Param("name") String name,
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
                                         @Param("englishword") String englishword,
                                         @Param("mandarin") String mandarin

    );


    public Villagenumber getVillagenumberById(@Param("id") Integer id, @Param("userid") Integer userid);

    void updateVillagenumberStatus(@Param("id") Integer id, @Param("userid") Integer userid);

    public void updateVillagenumberById(@Param("name") String name,
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

    public void updateVillagenumberByIdAndMandarin(@Param("name") String name,
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
                                                   @Param("mandarin") String mandarin,
                                                   @Param("id") Integer id
            , @Param("userid") Integer userid);



    List<Villagenumber> getVillagenumberByKey(@Param("userid") Integer userid, @Param("key") String key);


    Integer getVillagenumberByKeyCount(@Param("userid") Integer userid, @Param("key") String key);

    public void addMaintOfVillage(@Param("mainttype") String mainttype,
                                  @Param("number") String number,
                                  @Param("maintcontent") String maintcontent,
                                  @Param("maintcompany") String maintcompany,
                                  @Param("contacts") String contacts,
                                  @Param("contactsnum") String contactsnum,
                                  @Param("cost") String cost,
                                  @Param("mainttime") String mainttime
            , @Param("userid") Integer userid

    );

    Integer getVillageIdByName(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);

    Integer getVillageIdByName2(@Param("name") String name, @Param("userid") Integer userid);


    List<String> getLonAndLat(@Param("userid") Integer userid);

    Villagenumber getVillageByLonAndLat(@Param("lonandlat") String lonandlat, @Param("userid") Integer userid);

    Villagenumber getVillageNumByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public void addCountOfVilageNum(@Param("name") String name, @Param("count2") Integer count2,
                                    @Param("monthlyvisits") Integer monthlyvisits, @Param("type") String type, @Param("status") Integer status,
                                    @Param("yearlyvisits") Integer yearlyvisits, @Param("userid") Integer userid);



    List<Villagenumber> getVilageNumByName(@Param("name") String name, @Param("userid") Integer userid);


    public void updateMandarinword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);

    public void updateEnglishword(@Param("word") String word, @Param("id") Integer id, @Param("userid") Integer userid);


    public String getUrlById(@Param("id") Integer id, @Param("userid") Integer userid);

    public Integer getIdByUrl(@Param("url") String url, @Param("userid") Integer userid);

    public Integer  getMaxId(@Param("userid") Integer userid);

    public Integer  getMinId(@Param("userid") Integer userid);

    public void addLonAndLatByUrl(@Param("LonAndLat") String LonAndLat, @Param("url") String url, @Param("userid") Integer userid);

    public List<VillagenumberTwo>  getNameAndLatLon(@Param("userid") Integer userid);

    public Integer getMaxIdByAll(@Param("userid") Integer userid);

    public Villagenumber getVillagenumberByMaxId(@Param("userid") Integer userid);


    public  List<Villagenumber> getNameAndUrl(@Param("userid") Integer userid);

    public  List<Villagenumber> getVillagenumberByNameList(@Param("name") String name, @Param("userid") Integer userid);








    public Integer getOrderVillageNumberByUseriId(@Param("url") String url, @Param("userid") Integer userid);

    public Integer getMaxOrderVillageNumberByUseriId(@Param("userid") Integer userid);

    public Integer getMinOrderVillageNumberByUseriId(@Param("userid") Integer userid);

    public String
    getUrlByOrderindexVillageNumberByUserId(@Param("orderindex") Integer orderindexm,
                                            @Param("userid") Integer userid
    );


}
