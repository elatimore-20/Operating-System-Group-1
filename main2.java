import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
// The Thread class based on the Process class from previous project
class Thread {
    int pid, arrivalTime, burstTime, priority;
    public Thread(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

// Reading the text file and collect informations into a list of threads
public class main2 {
    public static List<Thread> readThreads(String filename) throws IOException {
        List<Thread> threads = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        br.readLine(); // Skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            threads.add(new Thread(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3])
            ));
        }
        br.close();
        return threads;
    }

    // Simulate when the thread was running
    public static void runThreads(List<Thread> threads) throws IOException {
        for (Thread t : threads) {
            System.out.println("Process" + t.pid + " has started.");
            try {
                TimeUnit.SECONDS.sleep(t.burstTime);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Process" + t.pid + "has finished.");
        }
    }

    // Main function
    public static void main (String[] args) {
        try {
            List<Thread> threads = readThreads("./processes.txt");
            runThreads(threads);
        } catch (IOException e) {
            System.out.println("Error reading thread files: " + e.getMessage());
        }
    }
}
