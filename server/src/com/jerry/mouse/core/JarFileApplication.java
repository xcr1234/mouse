package com.jerry.mouse.core;

import com.jerry.mouse.util.Properties;

import java.io.File;

public class JarFileApplication extends BaseApplication{

    private File jarFile;
    private String context;

    public JarFileApplication(Properties properties, File jarFile) throws LifecyleException {
        super(properties);
        this.jarFile = jarFile;
        String ctx = properties.get("app.context");
        if(ctx == null || ctx.isEmpty()){
            String name = jarFile.getName();
            int i = jarFile.getName().lastIndexOf(".jar");
            this.context = "/" + name.substring(0,i);
        }else if(ctx.startsWith("/")){
            this.context = ctx;
        }else{
            this.context = "/" + ctx;
        }
    }

    @Override
    public ServerHandler getServerHandler() {
        return null;
    }

    @Override
    public String getContext() {
        return context;
    }

    @Override
    public String getBasePath() {
        return jarFile.getPath();
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }
}
