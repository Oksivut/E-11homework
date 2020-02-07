package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;


public class Task2 {
    public static Queue<String> queue = new LinkedList<>();

    public void thread2() {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(queue.size());
    }

    public static class Producer implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                queue.add("Put" + (i + 1));
            }
        }
    }


        public static class Consumer implements Runnable {

            @Override
            public void run() {
                while (!queue.isEmpty()) {
                    System.out.println(queue.poll());

                }
            }
        }
    }

