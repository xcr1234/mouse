package com.jerry.mouse.example;

import com.jerry.mouse.api.Request;
import com.jerry.mouse.api.Response;
import com.jerry.mouse.api.Servlet;
import com.jerry.mouse.api.WebServlet;

@WebServlet("/login.do")
public class LoginAction implements Servlet {
    @Override
    public void service(Request request, Response response) throws Exception {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        if("admin".equals(user) && "admin".equals(pass)){
            request.putAttr("message","登录成功！");
        }else{
            request.putAttr("message","登录失败！");
        }
        request.putAttr("template","index.ftl");
        request.getRequestDispatcher("/freemarker").forward(request,response);
    }
}
