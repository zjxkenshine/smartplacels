package com.csdl.smartplacenew.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@Configuration
@EnableSwagger2
public class Swagger2 {
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Resource
    Environment environment;

    @Bean
    public Docket docket1(){
        //生产环境不使用
        String profiles[]=environment.getActiveProfiles();
        Boolean flag=profiles[0].equals("dev");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理接口")
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.csdl.smartplacenew.controller"))
                .paths(PathSelectors.any())
                .build();
    }
//
//    @Bean
//    public Docket docket2() {
//        //生产环境不使用
//        String profiles[]=environment.getActiveProfiles();
//        Boolean flag=profiles[0].equals("dev");
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("信息接口")
//                .apiInfo(apiInfo())
//                .enable(flag)
//                .select()
//                //为当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.csdl.smartplacenew.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }

    //
//    @Bean
//    public Docket docket3() {
//        //生产环境不使用
//        String profiles[]=environment.getActiveProfiles();
//        Boolean flag=profiles[0].equals("dev");
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("后端html接口")
//                .apiInfo(apiInfo())
//                .enable(flag)
//                .select()
//                //为当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.csdl.smartplacenew.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("智慧地名应用平台")
                //创建人
                .contact(new Contact("kenshine", "", "1754294529@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("java后端")
                .build();
    }

}
