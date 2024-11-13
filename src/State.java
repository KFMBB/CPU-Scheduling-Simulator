public enum State
{
    NEW,         // Job is created but not yet scheduled
    READY,       // Job is ready and waiting in the queue
    RUNNING,     // Job is currently being processed
    WAITING,     // Job is waiting (For rounf robin)
    TERMINATED;  // Job has finished execution
}
