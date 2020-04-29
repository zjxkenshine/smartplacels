package com.csdl.smartplacenew.vo;


import lombok.Data;

import java.util.List;

@Data
public class MaintrecordsVo {

    private Integer id;

    private String maintType;//维护类型

    private String number;//编号

    private String maintContent;//维护内容

    private String maintCompany;//维护公司

    private String  contacts;//联系人

    private String contactsNum;//联系电话

    private String cost;//费用

    private String maintTime;//维修时间

    private List<String> pictureUrl;//维护图片


}
