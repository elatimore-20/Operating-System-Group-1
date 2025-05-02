import java.io.*;
import java.util.concurrent.*;
import java.lang.Thread;
class Philosopher extends Thread {
    private int id;
    private Semaphore leftFork;
    private Semaphore rightFork;
    private int thinkTime;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork, int thinkTime) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.thinkTime = thinkTime;
    }

    @Override
    public void run() {
        try {
            System.out.println("[Philosopher " + id + "] Thinking for " + thinkTime + " seconds...");
            Thread.sleep(thinkTime * 1000);

            System.out.println("[Philosopher " + id + "] Waiting for forks...");
            
            // To prevent deadlock, pick up lower-numbered fork first
            Semaphore first = (id < (id + 1) % 5) ? leftFork : rightFork;
            Semaphore second = (id < (id + 1) % 5) ? rightFork : leftFork;

            first.acquire();
            System.out.println("[Philosopher " + id + "] Picked up first fork");
            second.acquire();
            System.out.println("[Philosopher " + id + "] Picked up second fork");

            System.out.println("[Philosopher " + id + "] Eating...");
            Thread.sleep((int)(Math.random() * 3000));  // Eating time can be random or based on burst_time.

            first.release();
            second.release();
            System.out.println("[Philosopher " + id + "] Released forks. Done eating.");

        } catch (InterruptedException e) {
            System.out.println("[Philosopher " + id + "] Interrupted.");
        }
    }
}

public class DiningPhilosophers {
    public static void main(String[] args) {
        int numPhilosophers = 5;
        Semaphore[] forks = new Semaphore[numPhilosophers];
        int[] burstTimes = new int[numPhilosophers];

        // Initialize forks
        for (int i = 0; i < numPhilosophers; i++) {
            forks[i] = new Semaphore(1);
        }

        // Read from processes.txt and extract burst times
        try (BufferedReader br = new BufferedReader(new FileReader("./processes.txt"))) {
            String line;
            int philosopherIndex = 0;
            while ((line = br.readLine()) != null) {
                // Skip headers or empty lines
                if (line.trim().startsWith("PID") || line.trim().isEmpty()) continue;
                
                String[] parts = line.trim().split("\\s+");
                int pid = Integer.parseInt(parts[0]);
                int arrivalTime = Integer.parseInt(parts[1]);  // This isn't used in the current simulation
                int burstTime = Integer.parseInt(parts[2]);
                int priority = Integer.parseInt(parts[3]);  // This isn't used in the current simulation

                // Use the burst time for thinking/eating duration
                burstTimes[philosopherIndex] = burstTime;
                philosopherIndex++;
            }
        } catch (IOException e) {
            System.out.println("Error reading processes.txt: " + e.getMessage());
            return;
        }

        // Initialize philosopher threads
        Philosopher[] philosophers = new Philosopher[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            Semaphore left = forks[i];
            Semaphore right = forks[(i + 1) % numPhilosophers];
            philosophers[i] = new Philosopher(i, left, right, burstTimes[i]);
            philosophers[i].start();
        }

        // Wait for all philosophers to finish
        for (int i = 0; i < numPhilosophers; i++) {
            try {
                philosophers[i].join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
            }
        }

        System.out.println("All philosophers have finished.");
    }
}