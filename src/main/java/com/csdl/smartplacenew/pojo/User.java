package com.csdl.smartplacenew.pojo;


import lombok.Data;

@Data
public class User  {

      private Integer id;

      private String code;//编号

      private String username;//用户名

      private String loginname;//登录名

      private String password;

      private String jointime;

      private  Integer level;

      private Integer status;

      private Integer delstatus;//删除状态


      private String county;//县市

}
