package com.imfondof.wanandroid.other.test;

public class ThreadB extends Thread {

    ThreadTest threadTest;

    public ThreadB(ThreadTest threadTest) {
        this.threadTest = threadTest;
    }

    @Override
    public void run() {
        try {
            threadTest.d();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
