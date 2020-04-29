package com.csdl.smartplacenew.vo;


import lombok.Data;

import java.util.List;

@Data
public class RoadNumberVo {

    private  int id;
    private String roadNum ; //路牌编号
    private String name ;

    private String lonandlat;  //经纬度

    private  String lon;//经度

    private  String lat;//纬度

    private String type;

    private String url;

    private  String  dialectVoice;//方言

    private  String  englishVoice;  //英语

    private String  mandarinVoice;  //国语

    private List<String> pictureUrl;

    private Integer status;



    private String textOfMater;//材质

    private String size;//尺寸

    private String yearOfEsent;//设立年份

    private String mainteUnit;//维护单位

    private String numofMaint;//维护次数

    private String maintPeriod;//维护期限

    private  String maintCost;//维护费用

    private String maintAccounts;//维护台账

    private String mandarinWord;//

    private String englishWord;//


    private String sharelinks;//分享链接
}
