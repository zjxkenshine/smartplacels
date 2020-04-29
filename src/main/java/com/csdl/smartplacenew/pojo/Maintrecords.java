package com.csdl.smartplacenew.pojo;


import lombok.Data;

@Data
public class Maintrecords {


    private Integer id;

    private String mainttype;//维护类型

    private String number;//编号

    private String maintcontent;//维护内容

    private String maintcompany;//维护公司

    private String  contacts;//联系人

    private String contactsnum;//联系电话

    private String cost;//费用

    private String mainttime;//维修时间

    private Integer userid;



}
