package com.zhaohui;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

public class C {

    public static void test2(){
        try{
            URLClassLoader loader = AClassLoader.getInstance();
            Class<?> aClass = loader.loadClass("com.zhaohui.A");
            Method method = aClass.getMethod("test");
            method.invoke(null);

            loader = BClassLoader.getInstance();
            Class<?>  bClass = loader.loadClass("com.zhaohui.B");
            method = bClass.getMethod("test");
            method.invoke(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void test1(){
        try{
            A.test();
            B.test();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try{
            C.test2();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
