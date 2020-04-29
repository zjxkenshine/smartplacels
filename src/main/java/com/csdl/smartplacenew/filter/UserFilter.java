package com.csdl.smartplacenew.filter;




import com.csdl.smartplacenew.security.UserSecurity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author: Pan Li Jie
 * @Email: lijie666666p@163.com
 * @Date: 2018/12/5
 */

//监听用户登入后可以访问的接口
public class UserFilter implements Filter {

    private static ArrayList arrayList;

    static {

        arrayList = new ArrayList();

        arrayList.add("/API/LS/User/Login");
        arrayList.add("/API/LS/User/login");
        arrayList.add("/API/LS/User/changepwd");

        arrayList.add("/API/LS/User/RegByEmail");
        arrayList.add("/API/LS/User/RegByMobile");
        arrayList.add("/API/LS/User/sendEmailNotLogin");
        arrayList.add("/API/LS/User/sendMsgNotLogin");
        arrayList.add("/API/LS/error/notAdminError");
        arrayList.add("/API/LS/error/notLoginError");
        arrayList.add("/API/LS/Model/CallBack");
        arrayList.add("/API/LS/User/FindPwd");
        arrayList.add("/API/LS/User/IsUserNameExists");
        arrayList.add("/API/LS/User/IsMobileExists");
        arrayList.add("/API/LS/User/getVerification");
        arrayList.add("/API/LS/SiteManager/UploadImg");
        arrayList.add("/API/LS/SiteManager/LoadConfig");
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

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

        servletRequest.getRequestDispatcher("/error/notLoginError").forward(servletRequest, servletResponse);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
