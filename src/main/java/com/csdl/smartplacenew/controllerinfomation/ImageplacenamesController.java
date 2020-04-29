package com.csdl.smartplacenew.controllerinfomation;

import com.csdl.smartplacenew.config.ConfigBean;
import com.csdl.smartplacenew.information.Imageplacenames;
import com.csdl.smartplacenew.mapper.ImageplacenamesMapper;
import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.pojo.User;
import com.csdl.smartplacenew.security.UserSecurity;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ImageplacenamesVO;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Api(description = "影像地名")
@RestController
@RequestMapping("/Imageplacenames")
public class ImageplacenamesController {


    @Resource
    ImageplacenamesMapper imageplacenamesMapper;

    @Resource
    UserMapper userMapper;


    @ApiOperation(value = "添加影像地名", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "name", value = "标题", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "link", value = "链接", required = false, dataType = "string", paramType = "query"),

    })
    @PostMapping("/add")
    private ResultVO add(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();

        String name=request.getParameter("name");

        String link=request.getParameter("link");

        if(file!=null) {
            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String times = System.currentTimeMillis() + "";
            String filename = times + "." + type;
            String fileRealPath = ConfigBean.sourceRealPath + "information" + File.separator + "Imageplacenames" + File.separator;
            File thefile = new File(fileRealPath);
            if (!thefile.exists()) {
                thefile.mkdirs();
            }
            Path path = Paths.get(fileRealPath + filename);
            Files.write(path, file.getBytes());
            String imgVirtualPath = ConfigBean.sourceVirtualPath + "information/" + "Imageplacenames/" + filename;
            imageplacenamesMapper.addImageplacenames(name,imgVirtualPath,link, 1,currentUserId);
        }
        else{
            imageplacenamesMapper.addImageplacenames(name,null,link, 1,currentUserId);

        }

        Imageplacenames imageplacenames=imageplacenamesMapper.getImageplacenamesByMaxId(currentUserId);


        return ResultVOUtil.success(imageplacenames);

    }




    @ApiOperation(value = "查询影像地名", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = false, dataType = "string", paramType = "query"),


    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer page=Integer.valueOf(request.getParameter("page"));

        Integer size=Integer.valueOf(request.getParameter("size"));

        String name=request.getParameter("name");



        if(name!=null&&!name.equals("")){

            Integer count=imageplacenamesMapper.getImageplacenamesCount("%"+name+"%",currentUserId);

            PageHelper.startPage(page,size);

            List<Imageplacenames> imageplacenamesList=imageplacenamesMapper.selectImageplacenames2("%"+name+"%",currentUserId);

            List<ImageplacenamesVO> imageplacenamesVOList=new ArrayList<>();

            for(int i=0;i<imageplacenamesList.size();i++){

                Imageplacenames imageplacenames=imageplacenamesList.get(i);

                ImageplacenamesVO ImageplacenamesVO=new ImageplacenamesVO();

                ImageplacenamesVO.setId(imageplacenames.getId());
                ImageplacenamesVO.setName(imageplacenames.getName());
                ImageplacenamesVO.setPictureUrl(imageplacenames.getPictureurl());
                ImageplacenamesVO.setLink(imageplacenames.getLink());
                ImageplacenamesVO.setUserId(imageplacenames.getUserid());

                imageplacenamesVOList.add(ImageplacenamesVO);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(imageplacenamesVOList);

            return ResultVOUtil.success(listVO);


        }
        else{

            Integer count=imageplacenamesMapper.getImageplacenamesCount(null,currentUserId);

            PageHelper.startPage(page,size);

            List<Imageplacenames> imageplacenamesList=imageplacenamesMapper.selectImageplacenames2(null,currentUserId);

            List<ImageplacenamesVO> imageplacenamesVOList=new ArrayList<>();

            for(int i=0;i<imageplacenamesList.size();i++){

                Imageplacenames imageplacenames=imageplacenamesList.get(i);

                ImageplacenamesVO ImageplacenamesVO=new ImageplacenamesVO();

                ImageplacenamesVO.setId(imageplacenames.getId());
                ImageplacenamesVO.setName(imageplacenames.getName());
                ImageplacenamesVO.setPictureUrl(imageplacenames.getPictureurl());
                ImageplacenamesVO.setLink(imageplacenames.getLink());
                ImageplacenamesVO.setUserId(imageplacenames.getUserid());

                imageplacenamesVOList.add(ImageplacenamesVO);

            }

            ListVO listVO=new ListVO();
            listVO.setCount(count);
            listVO.setList(imageplacenamesVOList);

            return ResultVOUtil.success(listVO);


        }


    }






    @ApiOperation(value = "删除影像地名内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "影像地名id", required = true, dataType = "integer", paramType = "query"),

    })
    @PostMapping("/del")
    private ResultVO del( HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        imageplacenamesMapper.delImageplacenames(id,currentUserId);

        return ResultVOUtil.success("删除成功");


    }




    @ApiOperation(value = "修改影像地名内容", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "影像地名id", required = true, dataType = "integer", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "标题", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "link", value = "链接", required = false, dataType = "string", paramType = "query"),
    })
    @PostMapping("/update")
    private ResultVO update(@ApiParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        User user= UserSecurity.getCurrentUser(request,userMapper);
        Integer currentUserId=user.getId();


        Integer id=Integer.valueOf(request.getParameter("id"));

        Imageplacenames imageplacenames=imageplacenamesMapper.getImageplacenamesById2(id,currentUserId);


        String name=request.getParameter("name");

        String link=request.getParameter("link");


        if(file!=null) {

            if(imageplacenames!=null){

                if(imageplacenames.getPictureurl()!=null&&!imageplacenames.getPictureurl().equals("")) {
                    String[] strings = imageplacenames.getPictureurl().split("\\/");


                    File file1 = new File(ConfigBean.sourceRealPath + "information" + File.separator + "Imageplacenames" + File.separator + strings[5]);

                    file1.delete();
                }

            String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            String times=System.currentTimeMillis()+"";
            String filename=times+"."+type;
            String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Imageplacenames"+File.separator;
            File  theFile=new File(fileRealPath);
            if(!theFile.exists()){
                theFile.mkdirs();
            }
            Path paths=Paths.get(fileRealPath+filename);
            Files.write(paths,file.getBytes());

            String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Imageplacenames/"+filename;


                imageplacenamesMapper.updateImageplacenames(name,imgVirtualPath,link,id,currentUserId);

          Imageplacenames imageplacenames1=imageplacenamesMapper.getImageplacenamesById2(id,currentUserId);

            return  ResultVOUtil.success(imageplacenames1);

            }
            else{

                String type = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
                String times=System.currentTimeMillis()+"";
                String filename=times+"."+type;
                String fileRealPath= ConfigBean.sourceRealPath+"information"+ File.separator+"Imageplacenames"+File.separator;
                File  theFile=new File(fileRealPath);
                if(!theFile.exists()){
                    theFile.mkdirs();
                }
                Path paths=Paths.get(fileRealPath+filename);
                Files.write(paths,file.getBytes());

                String imgVirtualPath=ConfigBean.sourceVirtualPath+"information/"+"Imageplacenames/"+filename;



                imageplacenamesMapper.updateImageplacenames(name,imgVirtualPath,link,id,currentUserId);

                Imageplacenames imageplacenames1=imageplacenamesMapper.getImageplacenamesById2(id,currentUserId);

                return  ResultVOUtil.success(imageplacenames1);

            }
        }
        else{

            imageplacenamesMapper.updateImageplacenames(name,imageplacenames.getPictureurl(),link,id,currentUserId);

            Imageplacenames imageplacenames1=imageplacenamesMapper.getImageplacenamesById2(id,currentUserId);

            return  ResultVOUtil.success(imageplacenames1);

        }



    }


}
