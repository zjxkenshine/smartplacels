package com.csdl.smartplacenew.service;

import com.csdl.smartplacenew.constant.CodeMessage;
import com.csdl.smartplacenew.mapper.FileCutMapper;
import com.csdl.smartplacenew.pojo.FileTemp;
import com.csdl.smartplacenew.util.FileCutUtils;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.beans.Transient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kenshine
 * @create 2020-04-30 10:16
 **/
@Service
public class FileService {
    @Resource
    FileCutMapper fileCutMapper;

    @Transactional
    public ResultVO upload(MultipartFile file, String md5no) {
        try{
            String md5=md5no.substring(0,md5no.indexOf("-"));
            //上传切片
            String dirname=FileCutUtils.save((FileInputStream) file.getInputStream(),md5no);
            //保存到上传切片中间表
            fileCutMapper.saveCutTemp(md5,md5no,dirname);
        }catch (IOException e){
            ResultVOUtil.error("500","文件切片保存失败");
        }
        return ResultVOUtil.fileSuccess();
    }

    /**
     * @param fileMd5 文件的md5值
     * @return
     *
     */
    @Transactional
    public ResultVO getMd5(String fileMd5) {
        int i=fileCutMapper.checkUpload(fileMd5);
        ResultVO resultVO;

        if(i>=1){   //文件已经上传
            resultVO= ResultVOUtil.byEnum2(CodeMessage.FileIsUploaded,fileMd5);
        }else{  //文件还没上传
            List<String> md5nolist=fileCutMapper.getMd5NoList(fileMd5);
            if(md5nolist.size()>0){ //有分片
                resultVO=  ResultVOUtil.byEnum2(CodeMessage.FileUploadedPart,md5nolist);
            }else{  //无分片，从未上传
                resultVO=  ResultVOUtil.byEnum2(CodeMessage.FileIsNotUploaded,fileMd5);
            }
        }
        return resultVO;
    }

    @Transactional
    public ResultVO merge(String md5, String type, int chunks) {
        //获取数据库分片数量
        List<FileTemp> tempList=fileCutMapper.getTemp(md5);
        String dirname=tempList.get(0).getDirname();
        File file = new File(dirname);
        if(tempList.size()!=chunks){
            return ResultVOUtil.error("1","文件切片数量不符合,预期"+chunks+"个,实际"+tempList.size());
        }else if(!file.exists()){
            return ResultVOUtil.error("2","文件目录不存在");
        }else{
            try{
                //循环合并文件
                String filepath=FileCutUtils.merge(tempList,type);
                //保存到路径
                fileCutMapper.addMd5(md5,filepath);
                //删除交换表数据
                fileCutMapper.deleteMd5Temp(md5);
                //删除切片文件/文件夹
                FileCutUtils.delFile(tempList.get(0).getDirname());

            }catch (IOException e){
                return ResultVOUtil.error("500","文件合并出错");
            }
            return ResultVOUtil.fileSuccess();
        }


    }
}
