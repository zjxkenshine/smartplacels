package com.csdl.smartplacenew.mapper;


import com.csdl.smartplacenew.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    void addUser(@Param("code") String code,
                 @Param("username") String username,
                 @Param("loginname") String loginname,
                 @Param("password") String password,
                 @Param("jointime") String jointime,
                 @Param("level") String level,
                 @Param("status") Integer status,
                 @Param("delstatus") Integer delstatus,
                 @Param("county") String county

    );

    User getUserById(@Param("id") Integer id);

    void updateUserStatus(@Param("id") Integer id);

    void updateUserStatus2(@Param("id") Integer id);

    void updateUserDelStatus(@Param("id") Integer id);

    public void updateUserById(@Param("code") String code,
                               @Param("username") String username,
                               @Param("loginname") String loginname,
                               @Param("password") String password,
                               @Param("level") Integer level,
                               @Param("county") String county,

                               @Param("id") Integer id

    );


    List<User> getUsrByKey(@Param("key") String key);


    List<User> getUsrByKeyOfAdmin(@Param("key") String key);

    List<User> getUsrByKeyOfUser(@Param("key") String key);

    Integer getUsrByKeyCount(@Param("key") String key);

    Integer getUsrByKeyCountOfAdmin(@Param("key") String key);  //管理员总数

    Integer getUsrByKeyCountOfUser(@Param("key") String key);//普通用户总数

    User getUserByLoginName(@Param("loginname") String loginname);

    User getUserByLoginAndPassword(@Param("loginname") String loginname,
                                   @Param("password") String password);




}
