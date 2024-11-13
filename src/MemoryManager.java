import java.util.Queue;

public class MemoryManager implements Runnable {
    private Queue<Job> jobQueue;
    private Queue<Job> readyQueue;
    private final int totalMemory;
    private int usedMemory = 0;
    private static int arrivalTime;

    public MemoryManager(Queue<Job> jobQueue, Queue<Job> readyQueue, int totalMemory) {
        this.jobQueue = jobQueue;
        this.readyQueue = readyQueue;
        this.totalMemory = totalMemory;
        this.arrivalTime = 0;
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
                int memoryRequired = job.getPcb().getMemoryRequired();

                if (checkMemory(memoryRequired)) {
                    allocateMemory(memoryRequired); // Allocate memory for the job
                    job.getPcb().setArrivalTime(arrivalTime);
                    readyQueue.add(jobQueue.poll()); // Move job to the readyQueue
                    System.out.println(job.getJobDetails()+ " moved to readyQueue with ");
                } else {
                    System.out.println("Insufficient memory for Job " + job.getPcb().getId() + ". Waiting for memory to become available.");
                    arrivalTime++;
                }
            }
        }
    }
}
