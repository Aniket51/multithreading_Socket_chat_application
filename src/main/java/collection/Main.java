package collection;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    // Create an atomic integer variable
    private static int counter =0;// new AtomicInteger(0);

    public static void main(String[] args) {
        // Create and start multiple threads
        Thread thread1 = new Thread(new IncrementTask());
        Thread thread2 = new Thread(new IncrementTask());
        Thread thread3 = new Thread(new IncrementTask());

        thread1.start();
        thread2.start();
        thread3.start();

        // Wait for all threads to finish
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the final value of the counter
        System.out.println("Final counter value: " + counter);
    }

    // Task to increment the counter
    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                counter++; // Atomically increments the counter by 1
            }
        }
    }
}
