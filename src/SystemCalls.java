public class SystemCalls {
    public void startProcess(Job job)
    {
        job.updateJobState(State.RUNNING);
    }
    public void terminateProcess(Job job)
    {
        job.updateJobState(State.TERMINATED);
    }
    public void getProcessInfo(Job job)
    {
        job.getJobDetails() ;
    }
    public void manageMemory(Job job)
    {
        //Later
    }
}
