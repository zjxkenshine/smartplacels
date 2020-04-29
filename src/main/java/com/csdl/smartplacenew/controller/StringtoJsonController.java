package com.csdl.smartplacenew.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Api(description = "景区接口")
@RestController
@RequestMapping("/String")
public class StringtoJsonController {



    @ApiOperation(value = "读取文件你让那个", notes = "")
    @ApiImplicitParams({

    })
    @PostMapping("/change")
    private void change(HttpServletRequest request) throws IOException {

        try {
            File file = new File("F:\\360MoveData\\Users\\ypc\\Desktop\\qingyuan.txt");
            if(file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;

                String str=null;

                BufferedWriter bw = new BufferedWriter(new FileWriter("./output.txt"));

                while ((lineTxt = br.readLine()) != null) {

                String[] s=lineTxt.split(";");

                for(int i=0;i<s.length;i++){

                    String[]  strings=s[i].split(",");

               //     System.out.println("{'lng':"+strings[0]+",'lat':"+strings[1]+"}");

                    str=str+"{'lng':"+strings[0]+",'lat':"+strings[1]+"}"+",";

                }

                }
                bw.write(str);
                bw.close();
                br.close();


            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {

            System.out.println("文件读取错误!");

            }


      }


 }
