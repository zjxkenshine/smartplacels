package com.csdl.smartplacenew.pojo;

import lombok.Data;

@Data
public class Picture2 {

    private String[] urls;

    private int id;

    private String url;

    private int  platenumberId;  //门牌id

    private int roadnumberId;  //路牌 id

    private int villagenumberId; //村牌id

    private int scenicspotId;//景区id

    private int roadId;//道路id

    private int status;

    private int userId;

    private int maintrecordsId;//维护id

}
