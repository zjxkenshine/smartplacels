package com.csdl.smartplacenew.pojo;


import lombok.Data;

@Data
public class Audio {

    private Integer id;

    private String url;

    private Integer  platenumberid;  //门牌id

    private Integer roadnumberid;  //路牌 id

    private Integer villagenumberid; //村牌id

    private Integer scenicspotid;//景区id

    private Integer roadid;//道路id

    private Integer status;

    private Integer userid;

}
