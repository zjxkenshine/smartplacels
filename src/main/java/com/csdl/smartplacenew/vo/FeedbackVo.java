package com.csdl.smartplacenew.vo;

import lombok.Data;

@Data
public class FeedbackVo {

    private Integer id;

    private  String feedbackPath;  //反馈道路

    private String feedbackType;  //反馈类型

    private String details;  //详细内容

    private String handle;//是否处理

    private String submiTime;//提交时间


}
