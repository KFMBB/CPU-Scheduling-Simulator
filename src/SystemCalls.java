public class SystemCalls
{
    MemoryManager memoryManager;

    public SystemCalls(MemoryManager memoryManager) {
        this.memoryManager = memoryManager;
    }
    // Default constrictor
    public SystemCalls() {
    }

    public void startProcess(Job job)
    {
        job.updateJobState(State.RUNNING); // START RUNNING THE PROCESS
    }
    public void terminateProcess(Job job)
    {
        job.updateJobState(State.TERMINATED); // TERMINATE THE PROCESS
        releaseMemory(job);
        System.out.println(job.getJobDetails()+" terminated");
    }
    public void getProcessInfo(Job job)
    {
        job.getJobDetails() ; // RETURN PROCESS DETAILS
    }
    public void releaseMemory(Job job){
        memoryManager.releaseMemory(job.getPcb().getMemoryRequired());
    }
    public void allocateMemory(Job job){
        memoryManager.allocateMemory(job.getPcb().getMemoryRequired());
    }
}
