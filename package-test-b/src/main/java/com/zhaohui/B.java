package com.zhaohui;

import com.google.common.base.Strings;

public class B {
    public static void test(){
        try{
            System.out.println("开始执行B的代码");
            System.out.println(Strings.lenientFormat("",new Object()));
            System.out.println("B的代码,执行完了,没报错");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        B.test();
    }
}
