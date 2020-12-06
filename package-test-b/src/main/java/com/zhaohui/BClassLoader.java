package com.zhaohui;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class BClassLoader  extends URLClassLoader {

    public static final String GUAVA_26_0_PATH = "/Users/zhaohui/.m2/repository/com/google/guava/guava/26.0-jre/guava-26.0-jre.jar";
    public static final String CLASS_B_PATH = "/Users/zhaohui/.m2/repository/com/zhaohui/package-test-b/1.0-SNAPSHOT/package-test-b-1.0-SNAPSHOT.jar";

    private volatile static BClassLoader instance = null;

    public static BClassLoader getInstance(){

        if(instance == null){
            synchronized(BClassLoader.class){
                if(instance == null){
                    try{
                        URL [] urls = new URL[]{
                            new File(GUAVA_26_0_PATH).toPath().toUri().toURL()
                            ,new File(CLASS_B_PATH).toPath().toUri().toURL()
                        };
                        instance = new BClassLoader(urls,Thread.currentThread().getContextClassLoader());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }

        return instance;

    }

    private BClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve)
        throws ClassNotFoundException {
        //优先从26.0-jre 中找，找不到在调用super
        Class<?> cls = null;
        try{
            cls = instance.findClass(name);
        }catch (Exception e){
            //e.printStackTrace();
        }
        if(null  != cls){
            return cls;
        }
        return super.loadClass(name,resolve);
    }
}
