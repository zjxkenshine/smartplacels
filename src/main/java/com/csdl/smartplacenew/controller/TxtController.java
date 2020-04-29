package com.csdl.smartplacenew.controller;


import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Cleanup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Api(description = "写入txt文本接口")
@RestController
@RequestMapping("/Txt")
public class TxtController {




    @ApiOperation(value = "读将文本西写入文本", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "txt", value = "文本", required = true, dataType = "string", paramType = "query"),

    })
    @PostMapping("/write")
    private void write(HttpServletRequest request) throws IOException {

        String path = "./input2.txt";
        String word = request.getParameter("txt");
        @Cleanup
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path,true)));
        out.write(word+"\r\n");



    }





    @ApiOperation(value = "读取文件", notes = "")
    @ApiImplicitParams({

    })
    @PostMapping("/red")
    private ResultVO red(HttpServletRequest request) throws IOException {

        String pathname = "./input2.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        //Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
        String line=null;

        List<String> stringList=new ArrayList();

        FileReader reader = new FileReader(pathname);

        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言

            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
                stringList.add(line);
                System.out.println(line);
            }


        return ResultVOUtil.success(stringList);

    }

    }
