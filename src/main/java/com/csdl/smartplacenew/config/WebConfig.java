package com.csdl.smartplacenew.config;


import com.csdl.smartplacenew.filter.AdminFilter;
import com.csdl.smartplacenew.filter.MyCorsFilter;
import com.csdl.smartplacenew.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Pan Li Jie
 * @Email: lijie666666p@163.com
 * @Date: 2018/11/28
 */

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {


    //允许跨域
    @Bean
    public FilterRegistrationBean getCorsFilter(){
        MyCorsFilter myCorsFilter=new MyCorsFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(myCorsFilter);
        List<String> urlPatterns=new ArrayList<String>();
        urlPatterns.add("/res/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(3);
        return registrationBean;
    }



    ///监听是否为管理员
    @Bean
    public FilterRegistrationBean getAdminFilter(){
        AdminFilter adminFilter=new AdminFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(adminFilter);
        List<String> urlPatterns=new ArrayList<String>();
        urlPatterns.add("/Admin/*");//拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }


   // 监听用户是否登入
    @Bean
    public FilterRegistrationBean getUserFilter(){
        UserFilter userFilter=new UserFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(userFilter);
        List<String> urlPatterns=new ArrayList<>();
        urlPatterns.add("/User/*");
        urlPatterns.add("/Admin/*");
        urlPatterns.add("/Model/*");
        urlPatterns.add("/Plan/*");
        urlPatterns.add("/SiteManager/*");
        urlPatterns.add("/Terrain/*");
        urlPatterns.add("/dahua/*");
        urlPatterns.add("/Data");
//        urlPatterns.add("/Data/*");
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(2);
        return registrationBean;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置swagger相关
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/excel/get/**").addResourceLocations("file:"+ System.getProperty("user.dir")+ File.separator).setCachePeriod(1);
        registry.addResourceHandler(ConfigBean.imageVirtualPath+"**").addResourceLocations("file:"+ ConfigBean.imageRealPath).setCachePeriod(1);
        registry.addResourceHandler(ConfigBean.thumbnailVirtualPath+"**").addResourceLocations("file:"+ ConfigBean.thumbnailRealPath).setCachePeriod(1);
        registry.addResourceHandler(ConfigBean.sourceVirtualPath+"**").addResourceLocations("file:"+ ConfigBean.sourceRealPath).setCachePeriod(1);
        registry.addResourceHandler(ConfigBean.webVirtualPath+"**").addResourceLocations("file:"+ ConfigBean.webRealPath).setCachePeriod(1);
        registry.addResourceHandler(ConfigBean.videoVirtualPath+"**").addResourceLocations("file:"+ ConfigBean.videoRealPath).setCachePeriod(1);
        super.addResourceHandlers(registry);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*"); // 允许任何域名使用
        corsConfiguration.addAllowedHeader("*"); // 允许任何头
        corsConfiguration.addAllowedMethod("*"); // 允许任何方法（post、get等）
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 对接口配置跨域设置

        return new CorsFilter(source);
    }






    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }



    @Bean
    MultipartConfigElement multipartConfigElement() {
        //文件上传配置工厂
        MultipartConfigFactory factory = new MultipartConfigFactory();
        String location = System.getProperty("user.dir") + "/data/tmp";
        File tmpFile = new File(location);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        factory.setLocation(location);
        //创建相关配置
        return factory.createMultipartConfig();
    }


    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
        super.configureMessageConverters(converters);
    }
    @Bean
    public HttpMessageConverter responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }






}
