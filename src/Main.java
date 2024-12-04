import SchedulerSimulators.Process;
import SchedulerSimulators.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        // Adding example processes
        processes.add(new Process("P1", "Red", 0, 17, 4, 4)); // name, color, arrivalTime, burstTime, priority, quantum
        processes.add(new Process("P2", "Blue", 3, 6, 9, 3));
        processes.add(new Process("P3", "Green", 4, 10, 3, 5));
        processes.add(new Process("P4", "Yellow", 29, 4, 10, 2));

        // Testing Non-Preemptive Priority Scheduling with Context Switching
        SchedulerSimulator[] schedulers = {new PriorityScheduler(), new ShortestJobFirstScheduler(), new ShortestRemainingTimeFirstScheduler()};
        for (SchedulerSimulator scheduler : schedulers) {
            List<Process> processesCopy = deepCopy(processes);
            scheduler.schedule(processesCopy, 0);
            scheduler.printResults(processesCopy);
        }

        // Testing FCAI Scheduling
        // List<Process> fcaiProcesses = deepCopy(processes);
        // SchedulerSimulator.fcaiScheduling(fcaiProcesses, 0);
    }

    // Helper method to create a deep copy of the process list
    private static List<Process> deepCopy(List<Process> original) {
        List<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(p));
        }
        return copy;
    }
}