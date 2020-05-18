package com.csdl.smartplacenew.util;

import com.csdl.smartplacenew.config.ConfigBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**文件上传工具类，NIO通道
 */

public class NioFileUtil {

    /**
     * 文件上传的保存路径
     */
    public static String UPLOAD_PATH= ConfigBean.imageRealPath;
    public static String VIRTUAL_PATH= ConfigBean.imageVirtualPath;

    /**
     * 图片保存的公共路径
     * D:/JAVA_Project/IDEA/warehouse/upload/
     */
    public static String IMAGE_DIRECTORY = "file:" + UPLOAD_PATH;

    /**
     * 读取yml里的属性值
     */
//    static {
//        YamlPropertiesFactoryBean yamlMapFactoryBean = new YamlPropertiesFactoryBean();
//        //可以加载多个yml文件
//        yamlMapFactoryBean.setResources(new ClassPathResource("application.yml"));
//        Properties properties = yamlMapFactoryBean.getObject();
//        //获取yml里的路径参数
//        UPLOAD_PATH = properties.getProperty("filepath");
//    }

    /**
     * 根据文件老名字得到新名字
     *
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {
        String suffix = FileUtil.extName(oldName);
        //根据当前时间戳生成文件名
        return  System.currentTimeMillis()+"."+ suffix;
    }

    /**
     * 保存文件,返回文件所在目录和文件本身
     * type:类型
     * @return 返回文件所在目录和文件本身
     */
    public static String save(FileInputStream fis, String fileName,String type) {
        FileOutputStream fos = null;
        //创建通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        //要返回的路径
        String selfPath = null;
        //图片的存储位置
        String publicPath = null;
        try {
            selfPath = "picture" + File.separator + type + File.separator + fileName;
            publicPath = NioFileUtil.UPLOAD_PATH+selfPath;
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
        return VIRTUAL_PATH+selfPath;
    }



    //图片上传
    public static String upload(MultipartFile file, String type) {
        String newFileName = createNewFileName(file.getOriginalFilename());
        String path = null;
        try {
            path =save((FileInputStream)file.getInputStream(), newFileName,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }




}
