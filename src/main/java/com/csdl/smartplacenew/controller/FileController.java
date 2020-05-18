package com.csdl.smartplacenew.controller;

import com.csdl.smartplacenew.service.FileService;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author kenshine
 * @create 2020-04-30 9:46
 * 文件上传接口
 **/
@Api(description = "文件切片/断点上传接口")
@RestController
public class FileController {

    @Resource
    FileService fileService;

    @ApiOperation(value = "上传前查询", notes = "")
    @ApiImplicitParam(name = "fileMd5",value = "文件的Md5值",required = true,dataType = "string",paramType = "query")
    @GetMapping("/getMd5")
    public ResultVO getMd5(String fileMd5){
        return fileService.getMd5(fileMd5);
    }


    @ApiOperation(value = "分片上传", notes = "")
    @ApiImplicitParams ({
        @ApiImplicitParam(name = "file",value = "分片文件",required = true,dataType = "file",paramType = "query"),
        @ApiImplicitParam(name = "md5no",value = "文件的Md5值-序号",required = true,dataType = "string",paramType = "query")
    })
    @PostMapping("/upload")
    public ResultVO upload(MultipartFile file, String md5no) throws IOException {
         return fileService.upload(file,md5no);
    }


    @ApiOperation(value = "分片合并", notes = "")
    @ApiImplicitParams ({
        @ApiImplicitParam(name = "md5",value = "分片文件",required = true,dataType = "string",paramType = "query"),
        @ApiImplicitParam(name = "type",value = "文件类型",required = true,dataType = "string",paramType = "query"),
        @ApiImplicitParam(name = "chunks",value = "切片数量",required = true,dataType = "int",paramType = "query")
    })
    @GetMapping("/merge")
    public ResultVO merge(HttpServletRequest request){
        System.out.println(request.getParameterNames());
        String md5=request.getParameter("md5");
        String type=request.getParameter("type");
        int chunks=Integer.valueOf(request.getParameter("chunks"));
        return fileService.merge(md5,type,chunks);
    }


}
