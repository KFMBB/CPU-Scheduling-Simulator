import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;
public class main {
    public static void main(String[] args) {

        try {
            PrintStream logFile = new PrintStream(new FileOutputStream("src/output.log"));
            System.setOut(logFile);
            System.setErr(logFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error redirecting output to file: " + e.getMessage());
        }

        Scanner input = new Scanner(System.in);

        Queue<Job> readyQueue = new LinkedList<Job>();

        Queue<Job> jobQueue = new LinkedList<>();

        String filePath = "src/TestCase.txt";

        System.out.println("Choose a scheduling algorithm to run:");
        System.out.println("1. First-Come-First-Serve (FCFS)");
        System.out.println("2. Round-Robin (RR)");
        System.out.println("3. Shortest Job First (SJF)");

        String schedulingAlgorithm = switch (input.nextInt()) {
            case 1 -> "FCFS";
            case 2 -> "RoundRobin";
            case 3 -> "SJF";
            default -> {
                System.out.println("Invalid selection. Defaulting to FCFS.");
                yield "FCFS";
            }
        };

        ReportGenerator reportGenerator = new ReportGenerator();

        MemoryManager memoryManager = new MemoryManager(jobQueue, readyQueue);

        Scheduler scheduler = new Scheduler(readyQueue, schedulingAlgorithm, memoryManager);

        JobLoader jobLoader = new JobLoader(jobQueue, filePath);


        Thread jobLoaderThread = new Thread(jobLoader);

        Thread memoryManagerThread = new Thread(memoryManager);

        Thread schedulerThread = new Thread(scheduler);


        jobLoaderThread.start();

        try {
            jobLoaderThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in JobLoader Thread");
        }

        memoryManagerThread.start();

        schedulerThread.start();
        try {
            schedulerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in Scheduler Thread");
        }

//        scheduler.calculateStats(schedulingAlgorithm);

        reportGenerator.generatePerformanceReport(scheduler.getCompletedJobs(schedulingAlgorithm), schedulingAlgorithm);
        reportGenerator.generateExecutionLogReport(scheduler.getExecutionLog().getLogEntries(), schedulingAlgorithm);



    }
}
