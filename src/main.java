import java.util.LinkedList;
import java.util.Queue;

public class main {
    public static void main(String[] args) {
        Queue<Job> readyQueue = new LinkedList<Job>();

        Queue<Job> jobQueue = new LinkedList<>();

        String schedulingAlgorithm = "", filePath = "";

        MemoryManager memoryManager = new MemoryManager(jobQueue, readyQueue);

        Scheduler scheduler = new Scheduler(readyQueue, schedulingAlgorithm, memoryManager);

        JobLoader jobLoader = new JobLoader(jobQueue, filePath);


        Thread jobLoaderThread = new Thread(jobLoader);

        Thread memoryManagerThread = new Thread(memoryManager);

        Thread schedulerThread = new Thread(scheduler);

        schedulerThread.start();

        jobLoaderThread.start();

        memoryManagerThread.start();

        try {
            schedulerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in Scheduler Thread");
        }

        try {
            jobLoaderThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in JobLoader Thread");
        }

        try {
            memoryManagerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Error in MemoryManager Thread");
        }


    }
}
