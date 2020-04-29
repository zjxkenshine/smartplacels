package com.csdl.smartplacenew.pojo;

import lombok.Data;

@Data
public class Picture2 {

    private Integer id;

    private String url;

    private Integer  platenumberId;  //门牌id

    private Integer roadnumberId;  //路牌 id

    private Integer villagenumberId; //村牌id

    private Integer scenicspotId;//景区id

    private Integer roadId;//道路id

    private Integer status;

    private Integer userId;

    private Integer maintrecordsId;//维护id



}
