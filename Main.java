import java.util.*;

public class Main {

    public static void main(String[] args) {
    	List<Process> processes = new ArrayList<>();

        // Adding example processes
        processes.add(new Process("P1", "Red", 0, 17, 4, 4)); // name, color, arrivalTime, burstTime, priority, quantum
        processes.add(new Process("P2", "Blue", 3, 6, 9, 3));
        processes.add(new Process("P3", "Green", 4, 10, 3, 5));
        processes.add(new Process("P4", "Yellow", 29, 4, 10, 2));

        // Testing Non-Preemptive Priority Scheduling with Context Switching
        List<Process> priorityProcesses = deepCopy(processes);
        SchedulerSimulator.priorityScheduling(priorityProcesses, 0);

        // Testing Non-Preemptive Shortest Job First (SJF) Scheduling with Context Switching
        List<Process> sjfProcesses = deepCopy(processes);
        SchedulerSimulator.shortestJobFirst(sjfProcesses, 0);

        // Testing Shortest Remaining Time First (SRTF) Scheduling with Context Switching
        List<Process> srtfProcesses = deepCopy(processes);
        SchedulerSimulator.shortestRemainingTimeFirst(srtfProcesses, 0);

        // Testing FCAI Scheduling
//        List<Process> fcaiProcesses = deepCopy(processes);
//        SchedulerSimulator.fcaiScheduling(fcaiProcesses, 0);
    }

    // Helper method to create a deep copy of the process list
    private static List<Process> deepCopy(List<Process> original) {
        List<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(p.name, p.color, p.arrivalTime, p.burstTime, p.priority, p.quantum));
        }
        return copy;
    }
}
