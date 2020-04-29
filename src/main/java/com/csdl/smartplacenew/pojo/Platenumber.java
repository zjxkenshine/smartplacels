package com.csdl.smartplacenew.pojo;


import lombok.Data;


@Data
public class Platenumber {

    private  int id;

    private String name ;

    private String lonandlat;//经纬度

    private String type;

    private String url;

    private String voiceurl;//语音文件

    private Integer status;



    private String textofmater;//材质

    private String size;//尺寸

    private String yearofesent;//设立年份

    private String mainteunit;//维护单位

    private String numofmaint;//维护次数

    private String maintperiod;//维护期限

    private String maintcost;//维护费用

    private String maintaccounts;//维护台账


    private String mandarinword;//

    private String englishword;//

    private Integer userid;

    private String sharelinks;//分享链接

    private Integer orderindex;//顺序

}
