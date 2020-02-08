package com.company;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class Task6 {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(6);

        public void thread6() throws InterruptedException {
            while (true){
                new Thread(new PokerGame(cyclicBarrier)).start();
                TimeUnit.SECONDS.sleep(1);
            }

        }

        static class PokerGame implements Runnable {

            private CyclicBarrier cyclicBarrier;

            public PokerGame(CyclicBarrier cyclicBarrier) {
                this.cyclicBarrier = cyclicBarrier;
            }

            @Override
            public void run() {
                try {

                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("To start PokerGame");
            }
        }
    }

