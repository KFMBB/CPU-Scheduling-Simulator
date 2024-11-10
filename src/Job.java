public class Job {
    private PCB pcb ; // Each process (job) initialized will be assigned a PCB.

    public Job(int jobId, int burstTime, int memoryRequired)
    {
        this.pcb = new PCB(jobId , burstTime , memoryRequired)  ;
    }

    public PCB getPcb()
    {
        return pcb;
    }

    public String getJobDetails()
    {
        return "JobId : " + this.pcb.getId() + " Burst Time : " + this.pcb.getBurstTime() + " Memory Required : " + this.pcb.getMemoryRequired();

    }
}