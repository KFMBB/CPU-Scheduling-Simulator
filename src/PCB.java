public class PCB {
    private int id;
    private int burstTime;
    private int memoryRequired;
    private State state;            // Enum for job state
    private int arrivalTime;
    private int waitingTime;
    private int turnaroundTime;
    private int remainingTime;

    // Constructor to initialize essential PCB fields
    public PCB(int id, int burstTime, int memoryRequired) {
        this.id = id;
        this.burstTime = burstTime;
        this.memoryRequired = memoryRequired;
        this.state = State.NEW;      // Initialize to NEW state
        this.remainingTime = burstTime;
    }

    // Getters and setters for each attribute
    public int getId() {
        return id;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getMemoryRequired() {
        return memoryRequired;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    // Method to update the waiting time
    public void updateWaitingTime(int time) {
        this.waitingTime += time;
    }

    // Method to update the turnaround time
    public void updateTurnaroundTime(int time) {
        this.turnaroundTime = time;
    }
}
