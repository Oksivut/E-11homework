package com.company;

import java.util.concurrent.TimeUnit;

public class Task1 {
    public void thread1 (){
        new Thread(new ThreadTask1 ()).start();
    }

    public static class ThreadTask1 implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("Прошло 5 секунд");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
