import java.util.Queue;

public class MemoryManager implements Runnable {
    public Queue<Job> jobQueue;
    public Queue<Job> readyQueue;
    private final int totalMemory = 1024;
    private int usedMemory;
    private int arrivalTime;
    public SystemCalls systemCalls;

    public MemoryManager(Queue<Job> jobQueue, Queue<Job> readyQueue) {
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.arrivalTime = 0;
        this.usedMemory = 0;
        this.systemCalls = new SystemCalls(this);
    }

    // Check if there is enough memory available
    public boolean checkMemory(int memoryRequired) {
        return memoryRequired + usedMemory <= totalMemory;
    }

    // Allocate memory for a job
    public void allocateMemory(int memoryRequired) {
        usedMemory += memoryRequired;
    }

    // Release memory for a completed job
    public void releaseMemory(int memoryRequired) {
        usedMemory -= memoryRequired;
    }

    @Override
    public void run() {
        while (true) {
            if (!jobQueue.isEmpty()) {
                Job job = jobQueue.peek(); // Look at the first job in the jobQueue

                if (checkMemory(job.getPcb().getMemoryRequired())) {
                    systemCalls.allocateMemory(job); // Allocate memory for the job
                    job.getPcb().setArrivalTime(arrivalTime);
                    readyQueue.add(jobQueue.poll()); // Move job to the readyQueue
                    System.out.println("--------");
                    System.out.println(job.getJobDetails()+" added to ready queue.");
                }
                else {
                    System.out.println("Insufficient memory for Job " + job.getPcb().getId() + ". Waiting for memory to become available.");
                    System.out.println("Memory used: " + usedMemory);
                    arrivalTime++; // Since we're not counting the time that the jobs in the job queue are kept, we'll keep track of when they got into the ready queue.
                }
            }

            if(jobQueue.isEmpty() && readyQueue.isEmpty()) {
                System.out.println("----------"); // For debugging
                System.out.println("Memory Manager thread finished execution");
                break;
            }
        }
    }
}
