package com.csdl.smartplacenew.pojo;


import lombok.Data;

@Data
public class Feedback {

    private Integer id;

    private  String feedbackpath;  //反馈道路

    private String feedbacktype;  //反馈类型

    private String details;  //详细内容

    private String handle;//是否处理

    private String submitime;//提交时间

    private String roadsisitua;//路牌情况

    private Integer userid;



}
