package Homework.Modul13;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class PrepareProduction implements Runnable {
    BlockingQueue<String> queue;

    public PrepareProduction(BlockingQueue<String> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        try {
            queue.put("Preparatory phase completed");
            queue.put("Production has already started");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class DoProduction implements Runnable {
    private final BlockingQueue<String> queue;

    DoProduction(BlockingQueue<String> q) {
        this.queue = q;
    }

    @Override
    public void run() {
        try {
            String value = queue.take();
            while (!value.equals("Production has already started")) {
                value = queue.take();
                System.out.println(value);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> q = new LinkedBlockingQueue<>();
        Thread t1 = new Thread(new PrepareProduction(q));
        Thread t2 = new Thread(new DoProduction(q));

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}
