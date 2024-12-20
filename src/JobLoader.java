import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Queue;

public class JobLoader implements Runnable
{
    private Queue<Job> jobQueue;
    private String filePath;

    public JobLoader(Queue<Job> jobQueue, String filePath)
    {
        this.jobQueue = jobQueue;
        this.filePath = filePath;
    }

    @Override
    public void run()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split("[:;]");
                int jobId = Integer.parseInt(parts[0]);
                int burstTime = Integer.parseInt(parts[1]);
                int memoryRequired = Integer.parseInt(parts[2]);

                // Create a new Job with parsed data
                Job job = new Job(jobId, burstTime, memoryRequired);

                jobQueue.add(job);
                System.out.println("----------"); // For debugging
                System.out.println(job.getJobDetails() + " added to job queue" );
            }
        } catch (IOException e)
        {
            System.err.println("Error reading the job file: " + e.getMessage());
        }
    }
}
