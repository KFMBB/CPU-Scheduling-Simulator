import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.Comparator;



public class Scheduler implements Runnable {
    private Queue<Job> readyQueue;
    private final int timeQuantum = 8;
    private String schedulingAlgorithm; // Define which scheduling algorithm to use
    MemoryManager memoryManager;
    private Queue<Job> FCFS;
    private Queue<Job> roundRobin;
    private Queue<Job> SJF;
    // Constructor to set time quantum and scheduling algorithm
    public Scheduler(Queue<Job> readyQueue,String schedulingAlgorithm, MemoryManager memoryManager) {
        this.readyQueue = readyQueue;
        this.schedulingAlgorithm = schedulingAlgorithm;
        this.memoryManager  = memoryManager;
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
            memoryManager.systemCalls.startProcess(job);
            // Simulate job processing
            while (counter != burstTime) {
                counter++;
                log++;
            }
            counter = 0;
            job.getPcb().setTurnaroundTime(log - job.getPcb().getArrivalTime()); // Set turnaround time
            job.getPcb().setWaitingTime(job.getPcb().getTurnaroundTime() - burstTime);  // Set the waiting time
            memoryManager.systemCalls.releaseMemory(job);
            memoryManager.systemCalls.terminateProcess(job);
            System.out.println("Job " + job.getPcb().getId() + " completed with Turnaround Time: " +
                    job.getPcb().getTurnaroundTime() + ", Waiting Time: " + job.getPcb().getWaitingTime());
            FCFS.add(job);
        }
    }

    // Placholder for RR Algo
    public void runRoundRobin() {

            int log = 0;

            while (!readyQueue.isEmpty()){

                Job job=readyQueue.poll();
                int remainingTime = job.getPcb().getRemainingTime(); //RemainingTime inital is burst time
                memoryManager.systemCalls.startProcess(job);
                int i=timeQuantum;
                // Simulate job processing for TimeQuantum which is 8 ms
                while(i>0 && remainingTime!=0) {
                    remainingTime--;
                    log++;
                    i--;
                }
                // check if job is finshed if so terminate
                if(remainingTime==0) {

                    job.getPcb().setTurnaroundTime(log - job.getPcb().getArrivalTime());
                    job.getPcb().setWaitingTime(job.getPcb().getTurnaroundTime() - job.getPcb().getBurstTime());
                    memoryManager.systemCalls.releaseMemory(job);
                    memoryManager.systemCalls.terminateProcess(job);
                    roundRobin.add(job);
                }
                else {  //update remaingTime if not finshed and add to ready queue again until its finshed

                    job.getPcb().setRemainingTime(remainingTime);
                    readyQueue.add(job);

                }

            }



    }
    // Placeholder for SJF ALGO
    public void runSJF() {
        PriorityQueue<Job> PreadyQueue = new PriorityQueue<Job>();

        while (!readyQueue.isEmpty()) {  //empty the readyQueue to the Priorty Queue
            Job job = readyQueue.poll();
            PreadyQueue.add(job);
        }
        while (!PreadyQueue.isEmpty()) { //copied the FCFS and i'm not sure

            Job job = PreadyQueue.poll();
            int counter=0;
            int log=0;
            int burstTime = job.getPcb().getBurstTime();
            memoryManager.systemCalls.startProcess(job);

            //Simulate job processing

            while (counter != burstTime) {
                counter++;
                log++;
            }
            counter = 0;
            job.getPcb().setTurnaroundTime(log - job.getPcb().getArrivalTime()); // Set turnaround time
            job.getPcb().setWaitingTime(job.getPcb().getTurnaroundTime() - burstTime);  // Set the waiting time
            memoryManager.systemCalls.releaseMemory(job);
            memoryManager.systemCalls.terminateProcess(job);
            System.out.println("Job " + job.getPcb().getId() + " completed with Turnaround Time: " +
            job.getPcb().getTurnaroundTime() + ", Waiting Time: " + job.getPcb().getWaitingTime());
            SJF.add(job);
            while (!readyQueue.isEmpty()) { //check if the readyQueue got any new jobs after completing a job and then insert it in the priorty Queue
                Job job1 = readyQueue.poll();
                PreadyQueue.add(job1);
            }

        }



        }

    public void calculateStats() {

        System.out.println("Scheduling Algorithm: FCFS ");
        int size = FCFS.size();//reset size, turnaround and waiting time
        int averageTurnaround = 0;
        int averageWaiting = 0;
        while(!FCFS.isEmpty()) {   //Calculate total turnaround time and waiting time
            Job job = FCFS.poll();
            averageTurnaround += job.getPcb().getTurnaroundTime();
            averageWaiting += job.getPcb().getWaitingTime();
        }
        averageTurnaround = averageTurnaround / size;// Calculate averageTurnAroundtime
        System.out.println("\nThe Average Turn Around Time : " + averageTurnaround);

        averageWaiting = averageWaiting / size;// Calculate Waitingtime
        System.out.println("\nThe Average Waiting time : " + averageWaiting);

        System.out.println("\n---------------------------------------------------");
        System.out.println("\nScheduling Algorithm: RoundRobin ");


        size = roundRobin.size();//reset size, turnaround and waiting time
        averageTurnaround = 0;
        averageWaiting = 0;

        while(!roundRobin.isEmpty()) {  //Calculate total turnaround time and waiting time
            Job job = roundRobin.poll();
            averageTurnaround += job.getPcb().getTurnaroundTime();
            averageWaiting += job.getPcb().getWaitingTime();
        }
        averageTurnaround = averageTurnaround / size;// Calculate averageTurnAroundtime
        System.out.println("\nThe Average Turn Around Time : " + averageTurnaround);

        averageWaiting = averageWaiting / size;// Calculate averageWaitingtime
        System.out.println("\nThe Average Waiting time : " + averageWaiting);

        System.out.println("\n---------------------------------------------------");

        System.out.println("\nScheduling Algorithm: SJF ");

        size = SJF.size();//reset size, turnaround and waiting time
        averageTurnaround = 0;
        averageWaiting = 0;

        while(!SJF.isEmpty()) {  //Calculate total turnaround time and waiting time
            Job job = SJF.poll();
            averageTurnaround += job.getPcb().getTurnaroundTime();
            averageWaiting += job.getPcb().getWaitingTime();
        }
        averageTurnaround = averageTurnaround / size; // Calculate averageTurnAroundtime
        System.out.println("\nThe Average Turn Around Time : " + averageTurnaround);

        averageWaiting = averageWaiting / size; //Calculate averageWaitingtime
        System.out.println("\nThe Average Waiting time : " + averageWaiting);

    }
}