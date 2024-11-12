import java.util.LinkedList;
import java.util.Queue ;

public class Scheduler
{
    Queue<Job> readyQueue = new LinkedList<Job>() ;
    Queue<Job> jobQueue = new LinkedList<Job>() ;
    MemoryManager Mx = new MemoryManager() ;
    int timeQuantum = 8 ;

    public void loadJobsToReadyQueue() // this method shall load Jobs to ready Queue
    {                                  // iff there is enough memory and there is a job to do !
        if( jobQueue.isEmpty() )
        {
            System.out.println("The Queue is Empty !");
            return;
        }

        if ( Mx.checkMemory(jobQueue.peek().getPcb().getMemoryRequired()) )
        {
            readyQueue.add(jobQueue.poll()) ;
        }

        else
            System.out.println("There is no enough Memory Available for this Job !");

    }
    public void runFCFS()
    {}
    public void runRoundRobin()
    {}
    public void runSJF()
    {}
    public void calculateStats()
    {}


}
