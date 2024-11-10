public enum State {
    NEW,         // Job is created but not yet scheduled
    READY,       // Job is ready and waiting in the queue
    RUNNING,     // Job is currently being processed
    WAITING,     // Job is waiting (if applicable, e.g., in multi-level scheduling)
    TERMINATED;  // Job has finished execution
}
