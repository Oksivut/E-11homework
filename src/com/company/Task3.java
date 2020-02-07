package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class Task3 {
    public void thread3() throws ExecutionException, InterruptedException {
        List<Queue<String>> listOfQueues = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            listOfQueues.add(createQueue(i + 1));
        }

        long before = System.nanoTime();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<String>> future = executorService.submit(new Service(listOfQueues));


        System.out.println(future.get().size());
        long after = System.nanoTime();
        System.out.println( (after - before) + " nanoseconds");


        executorService.shutdown();

    }

    private Queue createQueue(int numberOfQueue) {
        Queue<String> queue = new LinkedList();
        for (int i = 0; i < 100_000; i++) {
            queue.add("человек $username: " + (i + 1) + "очередь:" + numberOfQueue);
        }
        return queue;
    }

    public class Service implements Callable<List<String>> {

        private List<Queue<String>> listOfQueues;

        public Service(List<Queue<String>> listOfQueues) {
            this.listOfQueues = listOfQueues;
        }

        @Override
        public List<String> call(){
            List<String> list = new ArrayList<>();

            for (int i = 0; i < listOfQueues.size(); i++) {
                while (!listOfQueues.get(i).isEmpty()) {
                    System.out.println(listOfQueues.get(i).element() + "обслужен потоком: " + Thread.currentThread().getId());
                    list.add(listOfQueues.get(i).poll());
                }
            }
            return list;
        }
    }
}

