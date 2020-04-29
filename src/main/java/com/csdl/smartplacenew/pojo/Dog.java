package com.csdl.smartplacenew.pojo;


import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.val;

@Data
@Builder
@Log
public class Dog {
    private String name;
    private String type;
    private String age;
    private String color;
    private val value;

    public void log(){
        log.info("哈哈哈");
    }
}
