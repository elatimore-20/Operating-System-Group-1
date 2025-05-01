import java.io.*;
import java.util.*;

class Process {
    int pid, arrivalTime, burstTime, priority, waitingTime, turnaroundTime;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class main {
    public static List<Process> readProcesses(String filename) throws IOException {
        List<Process> processes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        br.readLine(); // Skip header
        while ((line = br.readLine()) != null) {
            String[] parts = line.trim().split("\\s+");
            processes.add(new Process(
                    Integer.parseInt(parts[0]),
                    Integer.parseInt(parts[1]),
                    Integer.parseInt(parts[2]),
                    Integer.parseInt(parts[3])
            ));
        }
        br.close();
        return processes;
    }

    public static void fcfsScheduling(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int time = 0;
        for (Process p : processes) {
            if (time < p.arrivalTime) {
                time = p.arrivalTime;
            }
            p.waitingTime = time - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            time += p.burstTime;
        }
        displayResults("FCFS", processes);
    }

    public static void priorityScheduling(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.priority));
        int time = 0;
        for (Process p : processes) {
            if (time < p.arrivalTime) {
                time = p.arrivalTime;
            }
            p.waitingTime = time - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            time += p.burstTime;
        }
        displayResults("Priority Scheduling", processes);
    }

    public static void displayResults(String algorithm, List<Process> processes) {
        System.out.println("\n" + algorithm + " Gantt Chart:");
        int time = 0;
        for (Process p : processes) {
            System.out.print("| P" + p.pid + " ");
            time += p.burstTime;
        }
        System.out.println("|");
        time = 0;
        for (Process p : processes) {
            System.out.print(time + "    ");
            time += p.burstTime;
        }
        System.out.println(time);

        System.out.println("\nPID | WT | TAT");
        int totalWT = 0, totalTAT = 0;
        for (Process p : processes) {
            System.out.println(p.pid + "   | " + p.waitingTime + "  | " + p.turnaroundTime);
            totalWT += p.waitingTime;
            totalTAT += p.turnaroundTime;
        }
        System.out.println("\nAverage WT: " + (double) totalWT / processes.size());
        System.out.println("Average TAT: " + (double) totalTAT / processes.size());
    }

    public static void main(String[] args) {
        try {
            List<Process> processes = readProcesses("src/processes.txt");
            fcfsScheduling(new ArrayList<>(processes));
            priorityScheduling(new ArrayList<>(processes));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
