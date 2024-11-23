import java.util.ArrayList;
import java.util.List;

public class ExecutionLog {
    private List<ExecutionLogEntry> logEntries = new ArrayList<>();

    public void log(int jobId, int startTime, int endTime, int remainingBurstTime, State state) {
        logEntries.add(new ExecutionLogEntry(jobId, startTime, endTime, remainingBurstTime, state));
    }

    public List<ExecutionLogEntry> getLogEntries() {
        return logEntries;
    }

    public static class ExecutionLogEntry {
        private final int jobId;
        private final int startTime;
        private final int endTime;
        private final int remainingBurstTime;
        private final State state;

        public ExecutionLogEntry(int jobId, int startTime, int endTime, int remainingBurstTime, State state) {
            this.jobId = jobId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.remainingBurstTime = remainingBurstTime;
            this.state = state;
        }

        public int getJobId() {
            return jobId;
        }

        public int getStartTime() {
            return startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public int getRemainingBurstTime() {
            return remainingBurstTime;
        }

        public State getState() {
            return state;
        }
    }
}
