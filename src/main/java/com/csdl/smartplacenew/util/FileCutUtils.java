package com.csdl.smartplacenew.util;

import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.pojo.FileTemp;
import lombok.Cleanup;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * @author kenshine
 * @create 2020-04-30 10:04
 * 文件切片工具类
 **/
public class FileCutUtils {
    /**
     * 文件上传的保存路径
     */
    public static String UPLOAD_TEMP_PATH= ConfigBean.tempRealPath;
    public static String file_REAL_PATH= ConfigBean.fileRealPath;
    public static String file_VIRTUAL_PATH= ConfigBean.fileVirtualPath;


    /**
     * @param fileTemps    合并文件列表
     * @param type      文件类型
     * @return fileName 合成后的文件名
     */
    public static String merge(List<FileTemp> fileTemps,String type) throws IOException {
        String mergeFileName=fileTemps.get(0).getMd5()+"."+type;
        String RealPath=file_REAL_PATH+mergeFileName;
        //如不存在则创建目录及文件
        FileUtil.touch(RealPath);
        File file=new File(RealPath);

        //合并方式一
        @Cleanup
        FileInputStream in=null;
        @Cleanup
        FileOutputStream out=null;
        out=new FileOutputStream(file);
        byte[] buffer=new byte[1024];
        int num;
        for(FileTemp ft:fileTemps){
            in=new FileInputStream(new File(ft.getDirname()+File.separator+ft.getMd5no()));
            while((num=in.read(buffer))!=-1)
                out.write(buffer,0,num);
        }
        out.close();
        in.close();

        //返回虚拟路径
        return file_VIRTUAL_PATH+mergeFileName;
    }


    /** 分片中间文件上传(nio2方式)
     * @param fis 文件输入流
     * @param md5no 文件md5-序号
     * @return 分片文件所在文件夹(绝对路径)
     */
    public static String save(FileInputStream fis, String md5no) {
        String dirname=md5no.substring(0,md5no.indexOf("-")); //md5值,总文件名，文件夹名称
        FileOutputStream fos = null;
        //创建通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        //要返回的路径
        String selfPath = null;
        //分片的存储位置
        String publicPath = null;
        try {
            selfPath =  dirname + File.separator + md5no;
            publicPath = UPLOAD_TEMP_PATH+selfPath;
            //如不存在则创建目录及文件
            FileUtil.touch(publicPath);
            fos = new FileOutputStream(publicPath);

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();
            //通道间传输
            inChannel.transferTo(0, inChannel.size(), outChannel);

            inChannel.close();
            outChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return UPLOAD_TEMP_PATH+dirname;
    }

    /**
     * 删除文件/文件夹
     * @param file 要删除的文件/文件夹
     * @return
     */
    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }

    //删除文件/文件夹
    public static boolean delFile(String path) {
        return delFile(new File(path));
    }

}
