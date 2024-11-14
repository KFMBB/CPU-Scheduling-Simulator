import java.util.LinkedList;
import java.util.Queue;

public class Scheduler implements Runnable {
    private Queue<Job> readyQueue;
    private final int timeQuantum = 8;
    private String schedulingAlgorithm; // Define which scheduling algorithm to use
    private SystemCalls systemCall;
    // Constructor to set time quantum and scheduling algorithm
    public Scheduler(Queue<Job> readyQueue,String schedulingAlgorithm) {
        this.readyQueue = readyQueue;
        this.schedulingAlgorithm = schedulingAlgorithm;
        this.systemCall  = new SystemCalls();
    }

    // The main run method to start the scheduler thread
    @Override
    public void run() {
        switch (schedulingAlgorithm) {
            case "FCFS":
                runFCFS();
                break;
            case "RoundRobin":
                runRoundRobin();
                break;
            case "SJF":
                runSJF();
                break;
            default:
                System.out.println("Unknown scheduling algorithm.");
        }
    }

    // FCFS scheduling implementation
    public void runFCFS() {

        while (!readyQueue.isEmpty()) {
            int counter = 0; // Counter to bound the burst time
            int log = 0;     // Log variable to keep track of waiting time (WT) and turnaround time (TA)
            Job job = readyQueue.poll(); // Get the job at the head of the queue to process
            int burstTime = job.getPcb().getBurstTime();
            systemCall.startProcess(job);
            // Simulate job processing
            while (counter != burstTime) {
                counter++;
                log++;
            }
            counter = 0;
            job.getPcb().setTurnaroundTime(log - job.getPcb().getArrivalTime()); // Set turnaround time
            job.getPcb().setWaitingTime(job.getPcb().getTurnaroundTime() - burstTime);  // Set the waiting time
            systemCall.releaseMemory(job);
            systemCall.terminateProcess(job);
            System.out.println("Job " + job.getPcb().getId() + " completed with Turnaround Time: " +
                    job.getPcb().getTurnaroundTime() + ", Waiting Time: " + job.getPcb().getWaitingTime());
        }
    }

    // Placholder for RR Algo
    public void runRoundRobin() {

    }

    // Placeholder for SJF ALGO
    public void runSJF() {

    }

    public void calculateStats() {

    }
}
