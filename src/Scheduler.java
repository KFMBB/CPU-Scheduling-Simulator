import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class Scheduler implements Runnable {
    private final Queue<Job> readyQueue;
    private final int timeQuantum = 8;
    private final String schedulingAlgorithm; // Define which scheduling algorithm to use
    private final MemoryManager memoryManager;
    private Queue<Job> FCFS;
    private Queue<Job> roundRobin;
    private Queue<Job> SJF;
    private static int log = 0;     // Log variable to keep track of waiting time (WT) and turnaround time (TA)

    // Constructor to set time quantum and scheduling algorithm
    public Scheduler(Queue<Job> readyQueue, String schedulingAlgorithm, MemoryManager memoryManager) {
        this.readyQueue = readyQueue;
        this.schedulingAlgorithm = schedulingAlgorithm;
        this.memoryManager = memoryManager;
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

        FCFS = new LinkedList<Job>();
        while (!memoryManager.readyQueue.isEmpty() || !memoryManager.jobQueue.isEmpty()) {
            Job job = memoryManager.readyQueue.poll(); // Get the job at the head of the queue to process
            if (job != null) {
                int counter = 0; // Counter to bound the burst time
                int burstTime = job.getPcb().getBurstTime();
                memoryManager.systemCalls.startProcess(job);
                System.out.println("-----------------------------------------------------------------------------"); // For debugging
                System.out.println(job.getJobDetails() + " started execution.");
                System.out.println();
                // Simulate job processing
                while (counter != burstTime) {
                    counter++;
                    log++;
                }
                counter = 0;
                job.getPcb().setTurnaroundTime(log); // Set turnaround time
                job.getPcb().setWaitingTime(job.getPcb().getTurnaroundTime() - burstTime);  // Set the waiting time
                memoryManager.systemCalls.terminateProcess(job);
                System.out.println("-----------------------------------------------------------------------------"); // For debugging
                System.out.println("Job " + job.getPcb().getId() + " completed with Turnaround Time: " + job.getPcb().getTurnaroundTime() + ", Waiting Time: " + job.getPcb().getWaitingTime());
                System.out.println();
                FCFS.add(job);
            }
            else {
                System.out.println("-----------------------------------------------------------------------------"); // For debugging
                System.out.println("Waiting for Memory manager to add jobs to readyQueue.");
                System.out.println();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("-----------------------------------------------------------------------------"); // For debugging
        System.out.println(schedulingAlgorithm + " thread of execution is done.");
    }

    // Placholder for RR Algo
    public void runRoundRobin() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        roundRobin = new LinkedList<Job>();
        while (!memoryManager.readyQueue.isEmpty() || !memoryManager.jobQueue.isEmpty()) {
            Job job = readyQueue.poll();
            if (job != null) {
                int remainingTime = job.getPcb().getRemainingTime(); //RemainingTime inital is burst time
                memoryManager.systemCalls.startProcess(job);
                System.out.println("-----------------------------------------------------------------------------"); // For debugging
                System.out.println(job.getJobDetails() + " started execution.");
                System.out.println();
                int i = timeQuantum;
                // Simulate job processing for TimeQuantum which is 8 ms
                while (i > 0 && remainingTime != 0) {
                    remainingTime--;
                    log++;
                    i--;
                }
                // check if job is finshed if so terminate
                if (remainingTime == 0) {
                    job.getPcb().setTurnaroundTime(log - job.getPcb().getArrivalTime());
                    job.getPcb().setWaitingTime(job.getPcb().getTurnaroundTime() - job.getPcb().getBurstTime());
                    memoryManager.systemCalls.releaseMemory(job);
                    memoryManager.systemCalls.terminateProcess(job);
                    roundRobin.add(job);
                } else {  //update remaingTime if not finshed and add to ready queue again until its finshed
                    job.getPcb().setRemainingTime(remainingTime);
                    readyQueue.add(job);
                }
            }
            else {
                System.out.println("-----------------------------------------------------------------------------"); // For debugging
                System.out.println("Waiting for Memory manager to add jobs to readyQueue.");
                System.out.println();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("-----------------------------------------------------------------------------"); // For debugging
        System.out.println(schedulingAlgorithm + " thread of execution is done.");
        System.out.println();
    }

    // Placeholder for SJF ALGO
    public void runSJF() {

        PriorityQueue<Job> PreadyQueue = new PriorityQueue<Job>();
        SJF = new LinkedList<Job>();
        while (!memoryManager.readyQueue.isEmpty() || !memoryManager.jobQueue.isEmpty()) {//empty the readyQueue to the Priorty Queue
            System.out.println("-----------------------------------------------------------------------------"); // For debugging
            System.out.println("Waiting for Memory manager to add jobs to readyQueue.");
            try{
                Thread.sleep(300);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            while(!memoryManager.readyQueue.isEmpty()) {
                Job job = readyQueue.poll();
                if (job != null) {
                    PreadyQueue.add(job);
                }
            }
            while (!PreadyQueue.isEmpty()) { //copied the FCFS and i'm not sure

                Job Sjob = PreadyQueue.poll();
                int counter = 0;
                int burstTime = Sjob.getPcb().getBurstTime();
                memoryManager.systemCalls.startProcess(Sjob);
                System.out.println("-----------------------------------------------------------------------------"); // For debugging
                System.out.println(Sjob.getJobDetails() + " started execution.");
                System.out.println();
                //Simulate Sjob processing

                while (counter != burstTime) {
                    counter++;
                    log++;
                }
                counter = 0;
                Sjob.getPcb().setTurnaroundTime(log - Sjob.getPcb().getArrivalTime()); // Set turnaround time
                Sjob.getPcb().setWaitingTime(Sjob.getPcb().getTurnaroundTime() - burstTime);  // Set the waiting time
                memoryManager.systemCalls.releaseMemory(Sjob);
                memoryManager.systemCalls.terminateProcess(Sjob);
                System.out.println("Job " + Sjob.getPcb().getId() + " completed with Turnaround Time: " +
                        Sjob.getPcb().getTurnaroundTime() + ", Waiting Time: " + Sjob.getPcb().getWaitingTime());
                SJF.add(Sjob);
                while (!readyQueue.isEmpty()) { //check if the readyQueue got any new jobs after completing a Sjob and then insert it in the priorty Queue
                    Job job1 = readyQueue.poll();
                    PreadyQueue.add(job1);
                }
            }
        }
        System.out.println("-----------------------------------------------------------------------------"); // For debugging
        System.out.println(schedulingAlgorithm + " thread of execution is done.");
    }

    public void calculateStats(String schedulingAlgorithm) {
        int size;
        int averageTurnaround;
        int averageWaiting ;
        switch (schedulingAlgorithm) {
            case "FCFS":
                System.out.println("---------------------------------------------------");
                System.out.println("Scheduling Algorithm: FCFS stats");
                size = FCFS.size();
                averageTurnaround = 0;
                averageWaiting = 0;
                while (!FCFS.isEmpty()) {   //Calculate total turnaround time and waiting time
                    Job job = FCFS.poll();
                    averageTurnaround += job.getPcb().getTurnaroundTime();
                    averageWaiting += job.getPcb().getWaitingTime();
                }
                averageTurnaround = averageTurnaround / size;// Calculate averageTurnAroundtime
                System.out.println("\nThe Average Turn Around Time : " + averageTurnaround);

                averageWaiting = averageWaiting / size;// Calculate Waitingtime
                System.out.println("\nThe Average Waiting time : " + averageWaiting);

                System.out.println("\n---------------------------------------------------");
                break;

            case "RoundRobin":
                System.out.println("---------------------------------------------------");
                System.out.println("Scheduling Algorithm: RoundRobin stats");
                size = roundRobin.size();//reset size, turnaround and waiting time
                averageTurnaround = 0;
                averageWaiting = 0;

                while (!roundRobin.isEmpty()) {  //Calculate total turnaround time and waiting time
                    Job job = roundRobin.poll();
                    averageTurnaround += job.getPcb().getTurnaroundTime();
                    averageWaiting += job.getPcb().getWaitingTime();
                }
                averageTurnaround = averageTurnaround / size;// Calculate averageTurnAroundtime
                System.out.println("\nThe Average Turn Around Time : " + averageTurnaround);

                averageWaiting = averageWaiting / size;// Calculate averageWaitingtime
                System.out.println("\nThe Average Waiting time : " + averageWaiting);
                System.out.println("---------------------------------------------------");
                break;
            case "SJF":
                System.out.println("---------------------------------------------------");
                System.out.println("Scheduling Algorithm: SJF stats:");
                size = SJF.size(); //reset size, turnaround and waiting time
                averageTurnaround = 0;
                averageWaiting = 0;

                while (!SJF.isEmpty()) {  //Calculate total turnaround time and waiting time
                    Job job = SJF.poll();
                    averageTurnaround += job.getPcb().getTurnaroundTime();
                    averageWaiting += job.getPcb().getWaitingTime();
                }
                averageTurnaround = averageTurnaround / size; // Calculate averageTurnAroundtime
                System.out.println("\nThe Average Turn Around Time : " + averageTurnaround);

                averageWaiting = averageWaiting / size; //Calculate averageWaitingtime
                System.out.println("\nThe Average Waiting time : " + averageWaiting);
                System.out.println("---------------------------------------------------");
                break;
            default:
                System.out.println("Unknown scheduling algorithm.");

        }
    }

    public Queue<Job> getCompletedJobs(String schedulingAlgorithm) {
        switch (schedulingAlgorithm) {
            case "FCFS":
                return FCFS;
            case "RoundRobin":
                return roundRobin;
            case "SJF":
                return SJF;
            default:
                System.out.println("Unknown scheduling algorithm.");
                return null;
        }
    }
}