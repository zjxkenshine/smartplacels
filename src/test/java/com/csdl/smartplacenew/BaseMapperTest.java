package com.csdl.smartplacenew;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.Reader;

/**
 * @author kenshine
 * @create 2020-05-18 9:47
 **/
public class BaseMapperTest extends BaseTest{
    //SqlSession工厂类
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init(){
        try{
            // 加载核心配置文件
            Reader reader= Resources.getResourceAsReader("mybatis-config.xml");
            //新建工厂对象，相当于JDBCconnect
            sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
            //关闭输入流
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
}
