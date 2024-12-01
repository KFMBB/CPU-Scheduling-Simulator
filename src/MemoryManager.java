import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryManager implements Runnable {
    public Queue<Job> jobQueue;
    public Queue<Job> readyQueue;
    private final int totalMemory = 1024;
    private final AtomicInteger usedMemory;
    private int arrivalTime;
    public SystemCalls systemCalls;

    public MemoryManager(Queue<Job> jobQueue, Queue<Job> readyQueue) {
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.arrivalTime = 0;
        this.usedMemory = new AtomicInteger(0);
        this.systemCalls = new SystemCalls(this);
    }

    // Check if there is enough memory available
    public boolean checkMemory(int memoryRequired) {
        return memoryRequired + usedMemory.get() <= totalMemory;
    }

    public void allocateMemory(int memoryRequired) {
        usedMemory.addAndGet(memoryRequired);
    }

    public void releaseMemory(int memoryRequired) {
        usedMemory.addAndGet(-memoryRequired);
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
                    System.out.println("-----------------------------------------------------------------------------");
                    System.out.println(job.getJobDetails()+" added to ready queue.");
                    System.out.println();
                }
                else {
                    System.out.println("-----------------------------------------------------------------------------");
                    System.out.println("Insufficient memory for Job " + job.getJobDetails());
                    System.out.println("Waiting for memory to become available.");
                    System.out.println("Memory used: " + usedMemory);
                    System.out.println("-----------------------------------------------------------------------------");
                    arrivalTime++; // Since we're not counting the time that the jobs in the job queue are kept, we'll keep track of when they got into the ready queue.
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if(jobQueue.isEmpty() && readyQueue.isEmpty()) {
                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("Memory Manager thread finished execution");
                break;
            }
        }
    }
}
