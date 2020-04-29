package com.csdl.smartplacenew.filter;

import com.csdl.smartplacenew.mapper.UserMapper;
import com.csdl.smartplacenew.security.UserSecurity;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: Pan Li Jie
 * @Email: lijie666666p@163.com
 * @Date: 2018/11/28
 */

@Component
public class AdminFilter implements Filter {

    private static ArrayList arrayList;

    static {

        arrayList = new ArrayList();

        arrayList.add("/API/LS/Admin/GetTerrainListByIds");

        arrayList.add("/API/LS/Admin/add");
        arrayList.add("/API/LS/Admin/del");
        arrayList.add("/API/LS/Admin/sortStatus");
        arrayList.add("/API/LS/Admin/update");
        arrayList.add("/API/LS/Admin/getList1");
        arrayList.add("/API/LS/Admin/login");
        arrayList.add("/API/LS/Admin/edit");
        arrayList.add("/API/LS/Admin/getList");
    }

//    @Autowired
//    private   userService;


    @Resource
    private UserMapper userMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        userMapper = ac.getBean(UserMapper.class);

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response=(HttpServletResponse)servletResponse;

        String url = request.getRequestURI();

        for (int i = 0; i < arrayList.size(); i++) {

            if (UserSecurity.checkUserLogin(request)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            if (url.equals(arrayList.get(i))) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

        }

        if (!UserSecurity.checkCurrentUserIsAdmin((HttpServletRequest) servletRequest, userMapper)) {

            System.out.println("非管理员");
            servletRequest.getRequestDispatcher("/error/notAdminError").forward(servletRequest, servletResponse);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
