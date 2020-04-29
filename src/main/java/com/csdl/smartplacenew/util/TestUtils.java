package com.csdl.smartplacenew.util;

import java.util.function.Function;

//测试工具类
public class TestUtils {

    //测试某方法时长
    public  static <R,T> void testTime(T t,Function<T,R> function){
        long begin=System.currentTimeMillis();
        R r=function.apply(t);
        long end=System.currentTimeMillis();
        System.out.println("=============================");
        System.out.println("用时="+(begin-end)+"毫秒");
        System.out.println("=============================");
    }
}
