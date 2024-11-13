public class SystemCalls
{
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

}
