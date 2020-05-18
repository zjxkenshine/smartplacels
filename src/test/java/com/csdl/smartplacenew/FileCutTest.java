package com.csdl.smartplacenew;

import com.csdl.smartplacenew.mapper.FileCutMapper;
import com.csdl.smartplacenew.service.FileService;
import com.csdl.smartplacenew.vo.ResultVO;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author kenshine
 * @create 2020-04-30 10:26
 **/
public class FileCutTest {

    public static FileService fileService;

    @BeforeClass
    public static void init(){
        fileService=new FileService();
    }


    @Test
    public void test01(){
        fileService.getMd5("123456");
    }




}
