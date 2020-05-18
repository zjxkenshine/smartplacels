package com.csdl.smartplacenew.config;

import lombok.Data;

import java.io.File;

/**
 * @Author: Pan Li Jie
 * @Email: lijie666666p@163.com
 * @Date: 2018/11/29
 */
@Data
public class ConfigBean {

    public  static String imageRealPath= System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"image"+ File.separator;

    public static String imageVirtualPath="/res/image/";

    public  static String tempRealPath= System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"temp"+ File.separator;

    public static String tempVirtualPath="/res/temp/";

    public  static String fileRealPath= System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"file"+ File.separator;

    public static String fileVirtualPath="/res/file/";

    public  static String sourceRealPath= System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"source"+ File.separator;

    public static String sourceVirtualPath="/res/source/";

    public static String thumbnailRealPath= System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"thumbnail"+ File.separator;

    public static String thumbnailVirtualPath="/res/thumbnail/";

    public static String videoRealPath= System.getProperty("user.dir")+ File.separator+"upload"+ File.separator+"video"+ File.separator;

    public static String videoVirtualPath="/res/video/";

    public static String tempPath= System.getProperty("user.dir")+ File.separator+"temp"+ File.separator;

    public  static String webRealPath= System.getProperty("user.dir")+ File.separator+"web"+ File.separator;

    public static String webVirtualPath="/web/";

    public static void config(){

        File file0=new File(imageRealPath);
        if(!file0.exists()){
            if(file0.mkdirs()){
            }
        }

        File file1=new File(sourceRealPath);
        if(!file1.exists()){
            if(file1.mkdirs()){
            }
        }

        File file2=new File(thumbnailRealPath);
        if(!file2.exists()){
            if(file2.mkdirs()){

            }
        }

        File file3=new File(tempPath);
        if(!file3.exists()){
            if(file3.mkdirs()){

            }
        }

        File file4=new File(tempRealPath);
        if(!file4.exists()){
            if(file4.mkdirs()){

            }
        }

    }
}
