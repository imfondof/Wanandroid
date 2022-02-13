package com.imfondof.wanandroid.other.test;

public class ThreadA extends Thread {
    ThreadTest threadTest;

    public ThreadA(ThreadTest threadTest) {
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
