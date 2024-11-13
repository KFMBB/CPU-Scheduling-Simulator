import java.util.Queue;

public class JobLoader implements Runnable {
    private Queue<Job> jobQueue;
    private String filePath;

    public JobLoader(Queue<Job> jobQueue, String filePath) {
        this.jobQueue = jobQueue;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        // Implement file reading and job creation logic here
        // Add jobs to jobQueue
    }
}
