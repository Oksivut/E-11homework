package com.company;

import java.util.*;
import java.util.concurrent.*;

public class Task5 {
    public static void thread5() throws InterruptedException, ExecutionException {
        List<Queue<String>> listOfQueues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listOfQueues.add(createQueue(i + 1));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Service> listThread = new ArrayList();
        for (int i = 0; i < 10; i++) {
            listThread.add(new Service(listOfQueues));
        }
        long before = System.nanoTime();
        List<Future<List<String>>> listResult = executorService.invokeAll(listThread);

        executorService.shutdown();

        int count = 0;
        for (int i = 0; i < listResult.size(); i++) {
            count += listResult.get(i).get().size();
        }

        System.out.println("Пользователи:" + count);
        long after = System.nanoTime();
        System.out.println("Время обслуживания: " + (after - before) + " nanoseconds");

    }

    private static Queue<String> createQueue(int numberOfQueue) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 100_000; i++) {
            queue.add("человек $username: " + (i + 1) + "очередь: " + numberOfQueue);
        }
        return queue;
    }

    public static class Service implements Callable<List<String>> {

        private List<Queue<String>> listOfQueues;
        private List<String> listResult;


        public Service(List<Queue<String>> listOfQueues) {
            this.listOfQueues = listOfQueues;
            listResult= Collections.synchronizedList (new ArrayList<>());
        }

        @Override
        public List<String> call() throws InterruptedException {
            Random random = new Random();
            int numberOfQueue;


            while (!allQueuesEmpty()) {

                numberOfQueue = random.nextInt(10);

                String poll = listOfQueues.get(numberOfQueue).poll();
                if (poll != null) {
                    listResult.add(poll);
                    System.out.println(poll + " обслужен потоком: " + Thread.currentThread().getId());
                }
                //TimeUnit.SECONDS.sleep(2);
            }
            return listResult;
        }

        private boolean allQueuesEmpty() {
            for (Queue<String> queue : listOfQueues) {
                if (!queue.isEmpty()) {
                    return false;
                }
            }
            return true;

        }
    }
}

