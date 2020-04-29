package com.csdl.smartplacenew.controllerinfomation;


import com.csdl.smartplacenew.information.*;
import com.csdl.smartplacenew.mapper.*;
import com.csdl.smartplacenew.util.ResultVOUtil;
import com.csdl.smartplacenew.vo.ListVO;
import com.csdl.smartplacenew.vo.ResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Api(description = "Pc端控制器")
@RestController
@RequestMapping("/Pc")
public class Pccontroller {


    @Resource
    AvillagerhymeMapper avillagerhymeMapper;//行政村名

    @Resource
    ChafoodMapper chafoodMapper;

    @Resource
    FamoussceneryMapper famoussceneryMapper;

    @Resource
    FocustomsMapper focustomsMapper;  //文旅文化

    @Resource
    NolegacultureMapper nolegacultureMapper; //红色地名

    @Resource
    OcplacenamesMapper ocplacenamesMapper;  //廊桥地名

    @Resource
    PlacenacultuMapper placenacultuMapper; //地名文化

    @Resource
    StreetplanaMapper streetplanaMapper;//街巷地名

    @Resource
    UserMapper userMapper;

    @Resource
    JinliterbrigaMapper jinliterbrigaMapper;

    @Resource
    ImageplacenamesMapper imageplacenamesMapper;//影像地名

    @Resource
    CultuinsurunitMapper cultuinsurunitMapper;

    @Resource
    LeisuretripMapper leisuretripMapper;




    @ApiOperation(value = "获取总的信息", notes = "")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "userid", value = "用户id", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "seniorkey", value = "高级关键词", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "lowerkey", value = "低级关键词", required = false, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "string", paramType = "query"),

            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "大小", required = true, dataType = "string", paramType = "query"),

    })
    @GetMapping("/getList1")
    private ResultVO getList1(HttpServletRequest request) throws IOException {

//        User user= UserSecurity.getCurrentUser(request,userMapper);
//        Integer currentUserId=user.getId();

        Integer currendId=0;

        if(request.getParameter("userid")!=null&&!request.getParameter("userid").equals("")){

            currendId=Integer.valueOf(request.getParameter("userid"));
        }

        Integer seniorkey=Integer.valueOf(request.getParameter("seniorkey"));

        String name=request.getParameter("name");


        if(seniorkey==1){


            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);


            List<Ocplacenames> ocplacenamesList=ocplacenamesMapper.selectOcplacenames(null,currendId);

            PageInfo<Ocplacenames> pageInfo = new PageInfo<Ocplacenames>(ocplacenamesList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(ocplacenamesList);

            return ResultVOUtil.success(listVO);

        }


        if(seniorkey==2){

            if(currendId==15&&name!=null){


                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);


                List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName("%"+name+"%",currendId);

                PageInfo<Avillagerhyme> pageInfo = new PageInfo<Avillagerhyme>(avillagerhymeList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(avillagerhymeList);

                return ResultVOUtil.success(listVO);

            }

            else{

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);


                List<Avillagerhyme> avillagerhymeList=avillagerhymeMapper.selectAvillagerhymeByName(null,currendId);

                PageInfo<Avillagerhyme> pageInfo = new PageInfo<Avillagerhyme>(avillagerhymeList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(avillagerhymeList);

                return ResultVOUtil.success(listVO);

            }


        }


        if(seniorkey==3){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);

            List<Chafood>  chafoodList=chafoodMapper.selectChafoodByName(null,currendId);


            PageInfo<Chafood> pageInfo = new PageInfo<Chafood>(chafoodList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(chafoodList);

            return ResultVOUtil.success(listVO);
        }

        if(seniorkey==4){
            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));
            if(currendId==15||currendId==16){

                if(lowerkey==1){

                    Integer page=Integer.valueOf(request.getParameter("page"));

                    Integer size=Integer.valueOf(request.getParameter("size"));

                    PageHelper.startPage(page,size);

                    List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey1",currendId);


                    PageInfo<Focustoms> pageInfo = new PageInfo<Focustoms>(focustomsList);

                    ListVO listVO=new ListVO();
                    listVO.setCount(pageInfo.getTotal());
                    listVO.setList(focustomsList);

                    return ResultVOUtil.success(listVO);
                }

                if(lowerkey==2){


                    Integer page=Integer.valueOf(request.getParameter("page"));

                    Integer size=Integer.valueOf(request.getParameter("size"));

                    PageHelper.startPage(page,size);

                    List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey2",currendId);


                    PageInfo<Focustoms> pageInfo = new PageInfo<Focustoms>(focustomsList);

                    ListVO listVO=new ListVO();
                    listVO.setCount(pageInfo.getTotal());
                    listVO.setList(focustomsList);

                    return ResultVOUtil.success(listVO);

                }

                if(lowerkey==3){

                    Integer page=Integer.valueOf(request.getParameter("page"));

                    Integer size=Integer.valueOf(request.getParameter("size"));

                    PageHelper.startPage(page,size);


                    List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey3",currendId);


                    PageInfo<Focustoms> pageInfo = new PageInfo<Focustoms>(focustomsList);

                    ListVO listVO=new ListVO();
                    listVO.setCount(pageInfo.getTotal());
                    listVO.setList(focustomsList);

                    return ResultVOUtil.success(listVO);

                }

                if(lowerkey==4){

                    Integer page=Integer.valueOf(request.getParameter("page"));

                    Integer size=Integer.valueOf(request.getParameter("size"));

                    PageHelper.startPage(page,size);


                    List<Focustoms> focustomsList=focustomsMapper.selectFocustoms2(null,"tabkey4",currendId);

                    PageInfo<Focustoms> pageInfo = new PageInfo<Focustoms>(focustomsList);

                    ListVO listVO=new ListVO();
                    listVO.setCount(pageInfo.getTotal());
                    listVO.setList(focustomsList);

                    return ResultVOUtil.success(listVO);

                }

            }else{

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);


                List<Focustoms> focustomsList=focustomsMapper.selectFocustoms(null,currendId);


                PageInfo<Focustoms> pageInfo = new PageInfo<Focustoms>(focustomsList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(focustomsList);

                return ResultVOUtil.success(listVO);

            }



        }


        if(seniorkey==5){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);

            List<Famousscenery> famoussceneryList=famoussceneryMapper.selectFamousscenery(null,currendId);

            PageInfo<Famousscenery> pageInfo = new PageInfo<Famousscenery>(famoussceneryList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(famoussceneryList);

            return ResultVOUtil.success(listVO);

        }


        if(seniorkey==6){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);


            List<Nolegaculture> nolegacultureList=nolegacultureMapper.selectNolegaculture(null,currendId);

            PageInfo<Nolegaculture> pageInfo = new PageInfo<Nolegaculture>(nolegacultureList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(nolegacultureList);

            return ResultVOUtil.success(listVO);

        }

        if(seniorkey==7){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);


                List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey1",currendId);

                PageInfo<Placenacultu> pageInfo = new PageInfo<Placenacultu>(placenacultuList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(placenacultuList);

                return ResultVOUtil.success(listVO);

            }

            if(lowerkey==2) {

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey2",currendId);

                PageInfo<Placenacultu> pageInfo = new PageInfo<Placenacultu>(placenacultuList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(placenacultuList);

                return ResultVOUtil.success(listVO);

            }

            if(lowerkey==3) {


                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey3",currendId);

                PageInfo<Placenacultu> pageInfo = new PageInfo<Placenacultu>(placenacultuList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(placenacultuList);

                return ResultVOUtil.success(listVO);

            }

            if(lowerkey==4) {


                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey4",currendId);

                PageInfo<Placenacultu> pageInfo = new PageInfo<Placenacultu>(placenacultuList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(placenacultuList);

                return ResultVOUtil.success(listVO);


            }

            if(lowerkey==5) {


                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Placenacultu> placenacultuList = placenacultuMapper.selectPlacenacultu(null,"tabkey5",currendId);

                PageInfo<Placenacultu> pageInfo = new PageInfo<Placenacultu>(placenacultuList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(placenacultuList);

                return ResultVOUtil.success(listVO);

            }



        }


        if(seniorkey==8){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);

            List<Streetplana> streetplanaList=streetplanaMapper.selectStreetplana(null,currendId);

            PageInfo<Streetplana> pageInfo = new PageInfo<Streetplana>(streetplanaList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(streetplanaList);

            return ResultVOUtil.success(listVO);


        }



        if(seniorkey==9){

            Integer lowerkey=Integer.valueOf(request.getParameter("lowerkey"));

            if(lowerkey==1) {

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey1" ,currendId);

                PageInfo<Jinliterbriga> pageInfo = new PageInfo<Jinliterbriga>(jinliterbrigaList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(jinliterbrigaList);

                return ResultVOUtil.success(listVO);

            }

            if(lowerkey==2) {

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);


                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey2" ,currendId);

                PageInfo<Jinliterbriga> pageInfo = new PageInfo<Jinliterbriga>(jinliterbrigaList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(jinliterbrigaList);

                return ResultVOUtil.success(listVO);
            }

            if(lowerkey==3) {


                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey3" ,currendId);

                PageInfo<Jinliterbriga> pageInfo = new PageInfo<Jinliterbriga>(jinliterbrigaList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(jinliterbrigaList);

                return ResultVOUtil.success(listVO);
            }

            if(lowerkey==4) {


                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey4" ,currendId);

                PageInfo<Jinliterbriga> pageInfo = new PageInfo<Jinliterbriga>(jinliterbrigaList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(jinliterbrigaList);

                return ResultVOUtil.success(listVO);

            }

            if(lowerkey==5) {

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);


                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey5" ,currendId);

                PageInfo<Jinliterbriga> pageInfo = new PageInfo<Jinliterbriga>(jinliterbrigaList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(jinliterbrigaList);

                return ResultVOUtil.success(listVO);

            }

            if(lowerkey==6) {

                Integer page=Integer.valueOf(request.getParameter("page"));

                Integer size=Integer.valueOf(request.getParameter("size"));

                PageHelper.startPage(page,size);

                List<Jinliterbriga> jinliterbrigaList = jinliterbrigaMapper.getJinliterbrigaByNameAndUserid(null,"tabkey6" ,currendId);

                PageInfo<Jinliterbriga> pageInfo = new PageInfo<Jinliterbriga>(jinliterbrigaList);

                ListVO listVO=new ListVO();
                listVO.setCount(pageInfo.getTotal());
                listVO.setList(jinliterbrigaList);

                return ResultVOUtil.success(listVO);

            }


        }


        //影像地名
        if(seniorkey==10){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);


            List<Imageplacenames> imageplacenamesList=imageplacenamesMapper.getImageplacenamesByNameAndUserid(null,currendId);

            PageInfo<Imageplacenames> pageInfo = new PageInfo<Imageplacenames>(imageplacenamesList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(imageplacenamesList);

            return ResultVOUtil.success(listVO);

        }


        //文保单位
        if(seniorkey==11){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);

            List<Cultuinsurunit> cultuinsurunitList=cultuinsurunitMapper.getCultuinsurunitByNameAndUserid(null,currendId);

            PageInfo<Cultuinsurunit> pageInfo = new PageInfo<Cultuinsurunit>(cultuinsurunitList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(cultuinsurunitList);

            return ResultVOUtil.success(listVO);

        }


        //休闲之旅
        if(seniorkey==12){

            Integer page=Integer.valueOf(request.getParameter("page"));

            Integer size=Integer.valueOf(request.getParameter("size"));

            PageHelper.startPage(page,size);

            List<Leisuretrip> leisuretripList=leisuretripMapper.getLeisuretripByNameAndUserid(null,currendId);

            PageInfo<Leisuretrip> pageInfo = new PageInfo<Leisuretrip>(leisuretripList);

            ListVO listVO=new ListVO();
            listVO.setCount(pageInfo.getTotal());
            listVO.setList(leisuretripList);

            return ResultVOUtil.success(listVO);

        }






        return ResultVOUtil.success();


    }


}
