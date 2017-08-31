package com.jerry.mouse.core;

import com.jerry.mouse.api.Servlet;
import com.jerry.mouse.api.ServletContext;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Application{


    ServerHandler getServerHandler();

    String getContext();

    String getEncoding();

    List<String> welcomeFiles();

    Servlet getServlet(String path);

    Servlet getErrorServlet();

    Servlet getNotFoundServlet();

    Servlet getDefaultServlet();

    Collection<Servlet> getServlets();

    void addServlet(String path, Servlet servlet);

    Servlet removeServlet(String path);

    ServletContext getServletContext();

    boolean viewDir();

    boolean isLogConn();

    Set<String> cacheExtensions();

    String getSessionCookieName();

    int getSessionMaxAge();

    SessionManager getSessionManager();

    String getBasePath();

    URL getResource(String path);

    InputStream getResourceAsStream(String path);

    ClassLoader getClassLoader();


}
