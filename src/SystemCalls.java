public class SystemCalls
{
    MemoryManager M1 = new MemoryManager(); // so we can reach the memory methods
    public void startProcess(Job job)
    {
        job.updateJobState(State.RUNNING); // START RUNNING THE PROCESS
    }
    public void terminateProcess(Job job)
    {
        job.updateJobState(State.TERMINATED); // TERMINATE THE PROCESS
    }
    public void getProcessInfo(Job job)
    {
        job.getJobDetails() ; // RETURN PROCESS DETAILS
    }
    public void manageMemory(Job job)
    {
    M1.allocateMemory(job.getPcb().getMemoryRequired()); // the allocate() shall deal with all checking mem,ory
    }
}
