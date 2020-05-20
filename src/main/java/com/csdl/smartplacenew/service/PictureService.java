package com.csdl.smartplacenew.service;

import com.csdl.smartplacenew.mapper.PictureMapper;
import com.csdl.smartplacenew.pojo.Picture2;
import com.csdl.smartplacenew.util.NioFileUtil;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片处理业务类
 * @author kenshine
 * @create 2020-05-18 9:04
 **/
@Service
public class PictureService {

    @Resource
    PictureMapper pictureMapper;

    //添加图片
    public ResultVO add(MultipartFile[] files, Picture2 picture) {
        int currentUserId=16;

        String type="default";
        String imgVirtualPath=null;
        List<String> listurl=new ArrayList<>();

        //判断类型
        if(picture.getPlatenumberId()!=0){
            type="platenumber";
        }else if(picture.getRoadnumberId()!=0){
            type="roadnumber";
        }else if(picture.getVillagenumberId()!=0) {
            type = "villagenumber";
        }else if(picture.getScenicspotId()!=0) {
            type = "scenicspotnumber";
        }else if(picture.getRoadId()!=0) {
            type = "road";
        }else if(picture.getMaintrecordsId()!=0) {
            type = "maintrecords";
        }

        if(files!=null&&files.length>0){
            for(int i=0;i<files.length;i++){

                MultipartFile file=files[i];
                //图片虚拟路径
                imgVirtualPath= NioFileUtil.upload(file,type);
                listurl.add(imgVirtualPath);
                //上传图片相关信息
                pictureMapper.addPicture(imgVirtualPath,picture.getPlatenumberId(),picture.getRoadnumberId(),picture.getVillagenumberId(),picture.getScenicspotId(),picture.getRoadId(),1,currentUserId,picture.getMaintrecordsId());
            }
        }
        return ResultVOUtil.success("上传成功");
    }


    //添加一个图片
    public Map addOne(MultipartFile file, Picture2 picture) {
        String type="default";
        String imgVirtualPath=null;

        //判断类型
        if(picture.getPlatenumberId()!=0){
            type="platenumber";
        }else if(picture.getRoadnumberId()!=0){
            type="roadnumber";
        }else if(picture.getVillagenumberId()!=0) {
            type = "villagenumber";
        }else if(picture.getScenicspotId()!=0) {
            type = "scenicspotnumber";
        }else if(picture.getRoadId()!=0) {
            type = "road";
        }else if(picture.getMaintrecordsId()!=0) {
            type = "maintrecords";
        }

        //图片虚拟路径
        imgVirtualPath= NioFileUtil.upload(file,type);
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("code","200");
        resultMap.put("message","成功");
        resultMap.put("url",imgVirtualPath);

        return resultMap;
    }


    //获取所有图片
    public ResultVO getPictures(Picture2 picture){
        //获取所有的图片
        List<String> listUrl=pictureMapper.getPictures(picture);
        return ResultVOUtil.success(listUrl);
    }

    public ResultVO addAll(Picture2 picture) {
        //删除原有信息
        pictureMapper.deletePictureRoadnumberId(picture.getRoadnumberId(),picture.getUserId());
        for(String imgVirtualPath:picture.getUrls()){
            //上传图片相关信息
            pictureMapper.addPicture(imgVirtualPath,picture.getPlatenumberId(),picture.getRoadnumberId(),picture.getVillagenumberId(),picture.getScenicspotId(),picture.getRoadId(),1,picture.getUserId(),picture.getMaintrecordsId());
        }
        return ResultVOUtil.success();
    }
}
