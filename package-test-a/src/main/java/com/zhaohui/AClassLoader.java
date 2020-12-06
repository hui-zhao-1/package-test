package com.zhaohui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AClassLoader extends URLClassLoader{

    public static final String GUAVA_19_0_PATH = "/Users/zhaohui/.m2/repository/com/google/guava/guava/19.0/guava-19.0.jar";
    public static final String CLASS_A_PATH = "/Users/zhaohui/.m2/repository/com/zhaohui/package-test-a/1.0-SNAPSHOT/package-test-a-1.0-SNAPSHOT.jar";

    private volatile static AClassLoader instance = null;

    public static AClassLoader getInstance(){

        if(instance == null){
            synchronized(AClassLoader.class){
                if(instance == null){
                    try{
                        URL [] urls = new URL[]{
                            new File(GUAVA_19_0_PATH).toPath().toUri().toURL()
                            ,new File(CLASS_A_PATH).toPath().toUri().toURL()
                        };
                        instance = new AClassLoader(urls,Thread.currentThread().getContextClassLoader());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return instance;

    }

    private AClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException {
        //优先从19.0 中找，找不到在调用super
        Class<?> cls = null;
        try{
            cls = instance.findClass(name);
        }catch (Exception e){
        }
        if(null  != cls){
            return cls;
        }
        return super.loadClass(name,resolve);
    }
}
