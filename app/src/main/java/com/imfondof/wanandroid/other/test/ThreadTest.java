package com.imfondof.wanandroid.other.test;

public class ThreadTest {
    private Object objectA = "a";
    private Object objectB = "b";

    public void a() throws InterruptedException {
        System.out.println("a外开始方式执行");
        synchronized (this.objectA) {
            System.out.println("a开始方式执行");
            Thread.sleep(3000);
            System.out.println("a结束方式执行");
        }
    }

    public void b() throws InterruptedException {
        synchronized (this.objectA) {
            System.out.println("b开始方式执行");
            Thread.sleep(3000);
            System.out.println("b结束方式执行");
        }
    }

    synchronized public void c() throws InterruptedException {
        System.out.println("c开始方式执行");
        Thread.sleep(3000);
        System.out.println("c结束方式执行");
    }

    public void d() throws InterruptedException {
        System.out.println("d外开始方式执行");
        synchronized (String.class) {
            System.out.println("d开始方式执行");
            Thread.sleep(3000);
            System.out.println("d结束方式执行");
        }
    }
}
