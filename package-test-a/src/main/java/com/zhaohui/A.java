package com.zhaohui;

import com.google.common.base.Objects;

public class A {

    public static void test() {
        try {
            System.out.println("开始执行A的代码");
            System.out.println(Objects.toStringHelper(new Object()).add("test", "test").toString());
            System.out.println("A的代码,执行完了,没报错");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        A.test();
    }
}
