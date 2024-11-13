import java.util.LinkedList;
import java.util.Queue ;

public class Scheduler
{
    public Queue<Job> readyQueue = new LinkedList<Job>() ;
    public Queue<Job> jobQueue = new LinkedList<Job>() ;
    private MemoryManager Mx = new MemoryManager() ;
    private int timeQuantum = 8 ;

    public void loadJobsToReadyQueue() // this method shall load Jobs to ready Queue
    {                                  // iff there is enough memory and there is a job to do !
        if(jobQueue.isEmpty())
        {
            System.out.println("The Queue is Empty !");
            return;
        }
        Job job = jobQueue.poll() ;
        int memoryRequired = job.getPcb().getMemoryRequired();
        if (Mx.checkMemory(memoryRequired))
        {
            Mx.allocateMemory(memoryRequired);
            readyQueue.add(job);
        }

        else
            System.out.println("There is no enough Memory Available for this Job !");
    }

    public void runFCFS()
    {
        if(readyQueue.isEmpty())
            return;

        while (!readyQueue.isEmpty()) {
            int counter = 0; // counter to bound the burst time
            int log = 0; // a log variable to help us keep track of the WT and TA
            Job job = readyQueue.poll() ; // get the job at the head of the queue to process
            job.getPcb().setWaitingTime(log); // set the waiting
            int BT = job.getPcb().getBurstTime();
            job.updateJobState(State.RUNNING);
            while(counter != BT){
                counter++;
                log++;
            }
            counter = 0;
            job.getPcb().setTurnaroundTime(log);
            job.updateJobState(State.TERMINATED);
        }
    }
    public void runRoundRobin()
    {}
    public void runSJF()
    {}
    public void calculateStats()
    {}


}
