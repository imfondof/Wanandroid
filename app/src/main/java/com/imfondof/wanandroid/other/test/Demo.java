package com.imfondof.wanandroid.other.test;

public class Demo {
    public static void main(String[] args) {
//        String objectA = "a";
//        String objectB = "b";
//        ThreadTest threadTest1 = new ThreadTest();
//        ThreadTest threadTest2 = new ThreadTest();
//        try {
//            threadTest1.a();
//            threadTest2.a();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        ThreadTest threadTest1 = new ThreadTest();
        ThreadTest threadTest2 = new ThreadTest();
        new ThreadA(threadTest1).start();
        new ThreadB(threadTest2).start();
    }
}

