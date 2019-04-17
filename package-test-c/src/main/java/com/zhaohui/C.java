package com.zhaohui;

public class C {
    public static void test(){
        try{
            A.test();
            B.test();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        C.test();
    }
}
