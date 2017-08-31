package com.jerry.mouse.example;

import com.jerry.mouse.api.Request;
import com.jerry.mouse.api.Response;
import com.jerry.mouse.api.Servlet;

public class IndexAction implements Servlet {
    @Override
    public void service(Request request, Response response) throws Exception {
        request.putAttr("template","index.ftl");
        request.getRequestDispatcher("/freemarker").forward(request,response);
    }
}
