package com.csdl.smartplacenew.mapper;

import com.csdl.smartplacenew.pojo.FileTemp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kenshine
 * @create 2020-04-30 10:19
 **/
@Mapper
public interface FileCutMapper {
    //-------------filemd5 文件切片上传表----------------
    //查询该文件是否已经存在,是否已经上传
    int checkUpload(@Param("fileMd5") String md5);

    //添加合并上传后的信息
    void addMd5(@Param("fileMd5") String md5,@Param("filepath") String filepath);


    //---------------filemd5temp 中间表---------------------
    //查询md5-no列表
    List<String> getMd5NoList(@Param("fileMd5") String md5);

    //保存中间信息
    void saveCutTemp(@Param("fileMd5") String md5,@Param("md5no") String md5no,@Param("dirname") String dirname);

    //查询分片数量
    int getTempNum(String md5);

    //查询分片列表
    List<FileTemp> getTemp(String md5);

    //删除分片表(中间表相关数据)
    void deleteMd5Temp(String md5);
}
