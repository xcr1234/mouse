package com.jerry.mouse.core;

import com.jerry.mouse.api.Servlet;
import com.jerry.mouse.util.Properties;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public abstract class BaseApplication  implements Application{

    private Properties properties;
    private List<String> welComeFiles = new ArrayList<String>();
    private Map<String,Servlet> servletMap = new HashMap<String,Servlet>();
    private final Servlet defaultServlet;
    private final Servlet errorServlet;
    private final Servlet notFoundServlet;
    private final ServletContextImpl servletContext = new ServletContextImpl(this);
    private boolean dirView = false;
    private boolean logConn = false;
    private final SessionManager sessionManager;
    private final Set<String> cacheExtensions = new HashSet<String>();


    public BaseApplication(Properties properties) throws LifecyleException{
        this.properties = properties;
        String wcf = properties.get("app.welComeFiles");
        if(wcf != null && !wcf.isEmpty()){
            Collections.addAll(welComeFiles,wcf.split(","));
        }
        defaultServlet = initServlet("servlet.default");
        errorServlet = initServlet("servlet.error");
        notFoundServlet = initServlet("servlet.notFound");

        dirView = properties.getBool("app.dirView");
        logConn = properties.getBool("app.logConn");

        String ext = properties.get("static.cacheExtensions");
        if(ext != null && !ext.isEmpty()){
            Collections.addAll(cacheExtensions,ext.split(","));
        }

        String sm = properties.get("com.jerry.mouse.core.DefaultSessionManager");
        if(sm != null && !sm.isEmpty()){
            try {
                Class c = getClassLoader().loadClass(sm);
                sessionManager = (SessionManager) c.newInstance();
            } catch (Exception e) {
                throw new LifecyleException("can't create session manager:" + sm,e);
            }
        }else{
            sessionManager = new DefaultSessionManager();
        }
    }



    @Override
    public String getEncoding() {
        String enc = properties.get("app.encoding");
        if(enc == null || enc.isEmpty()){
            return Charset.defaultCharset().displayName();
        }
        return enc;
    }

    @Override
    public List<String> welcomeFiles() {
        return welComeFiles;
    }

    @Override
    public Servlet getServlet(String path) {
        return servletMap.get(path);
    }

    @Override
    public Servlet getErrorServlet() {
        return errorServlet;
    }

    @Override
    public Servlet getDefaultServlet() {
        return defaultServlet;
    }

    @Override
    public Servlet getNotFoundServlet() {
        return notFoundServlet;
    }

    @Override
    public Collection<Servlet> getServlets() {
        return servletMap.values();
    }

    @Override
    public void addServlet(String path, Servlet servlet) {
        servletMap.put(path,servlet);
    }

    @Override
    public Servlet removeServlet(String path) {
        return servletMap.remove(path);
    }

    @Override
    public ServletContextImpl getServletContext() {
        return servletContext;
    }

    @Override
    public boolean viewDir() {
        return dirView;
    }

    @Override
    public boolean isLogConn() {
        return logConn;
    }

    @Override
    public Set<String> cacheExtensions() {
        return cacheExtensions;
    }

    @Override
    public String getSessionCookieName() {
        return properties.get("session.cookieName");
    }

    @Override
    public int getSessionMaxAge() {
        return properties.getInteger("session.maxAge");
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    @Override
    public URL getResource(String path) {
        return getClassLoader().getResource(path);
    }

    @Override
    public InputStream getResourceAsStream(String path) {
        return getClassLoader().getResourceAsStream(path);
    }

    private Servlet initServlet(String prop) throws LifecyleException{
        String name = properties.get(prop);
        if( name == null || name.isEmpty()){
            throw new LifecyleException("can't start Application.null value:" + prop);
        }
        try{
            Class c = getClassLoader().loadClass(name);
            return createServlet(c);
        }catch (Exception e){
            if(e instanceof LifecyleException){
                throw (LifecyleException)e;
            }
            throw new LifecyleException("can't start Application.",e);
        }
    }






    protected Servlet createServlet(Class clazz) throws LifecyleException {
        try{
            Servlet servlet = (Servlet) clazz.newInstance();
            if(servlet instanceof ApplicationAwareServlet){
                ((ApplicationAwareServlet) servlet).setApplication(this);
            }
            return servlet;
        }catch (Exception e){
            throw new LifecyleException("can't create Servlet:" + clazz,e);
        }
    }

}
