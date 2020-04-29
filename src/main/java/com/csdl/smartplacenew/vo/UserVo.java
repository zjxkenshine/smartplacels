package com.csdl.smartplacenew.vo;

import lombok.Data;

@Data
public class UserVo {

    private Integer id;

    private String code;//编号

    private String userName;//用户名

    private String loginName;//登录名

    private String joinTime;

    private  Integer level;

    private String status;//是否停用状态

    private String county;//县市
	
	

}


