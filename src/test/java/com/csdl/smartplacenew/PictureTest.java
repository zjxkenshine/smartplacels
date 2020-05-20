package com.csdl.smartplacenew;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * 图片相关测试
 * @author kenshine
 * @create 2020-05-18 9:43
 **/
public class PictureTest extends BaseMapperTest{

    //获取图片测试
    @Test
    public void testGetPicture(){
        //使用sqlSession实例化一个sqlSession对象,相当于使用预处理语句
        SqlSession sqlSession=getSqlSession();
        try{
            List<String> lurl=sqlSession.selectList("com.csdl.smartplacenew.mapper.PictureMapper");
        }finally{
            //关闭sqlSession
            sqlSession.close();
        }
    }

}
