import java.io.*;
import java.util.*;

class Thread {
    int pid, arrivalTime, burstTime, priority;
    public Thread(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
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

    public static void runThreads(List<Thread> threads) {
        for (Thread t : threads) {
            System.out.println(t.burstTime);
        }
    }
    public static void main (String[] args) {
        try {
            List<Thread> threads = readThreads("./processes.txt");
            runThreads(threads);
        } catch (IOException e) {
            System.out.println("Error reading thread files: " + e.getMessage());
        }
    }
}
