package SchedulerSimulators;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;



public class ShortestRemainingTimeFirstScheduler implements SchedulerSimulator {
    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nShortest Remaining Time First (SRTF) Scheduling with Context Switching");
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); // Sort by arrival time
        int currentTime = 0;
        Process runningProcess = null;

        while (true) {
            // Add newly arrived processes to the ready queue
            List<Process> readyProcesses = new ArrayList<>();
            for (Process p : processes) {
                if (!p.completed && p.arrivalTime <= currentTime) {
                    readyProcesses.add(p);
                }
            }

            // Select the process with the shortest remaining time
            Process selected = readyProcesses.stream()
                    .min(Comparator.comparingInt(p -> p.remainingTime))
                    .orElse(null);

            // Handle idle time if no process is ready
            if (selected == null) {
                if (processes.stream().allMatch(p -> p.completed)) break; // All processes completed
                currentTime++;
                continue;
            }

            // Context switching handling
            if (runningProcess != selected) {
                if (runningProcess != null) {
                    System.out.printf("Time %d: Context switching from Process %s to Process %s\n",
                            currentTime, runningProcess.name, selected.name);
                } else {
                    System.out.printf("Time %d: Context switching to Process %s\n", currentTime, selected.name);
                }
                currentTime += contextSwitchTime;
                runningProcess = selected;
            }

            // Execute the selected process for one time unit
            System.out.printf("Time %d: Process %s with remaining time %d is executing\n",
                    currentTime, selected.name, selected.remainingTime);
            selected.remainingTime--;
            currentTime++;

            // Mark process as completed if its remaining time is zero
            if (selected.remainingTime == 0) {
                System.out.printf("Time %d: Process %s has completed execution\n", currentTime, selected.name);
                selected.completed = true;
                selected.turnaroundTime = currentTime - selected.arrivalTime;
                selected.waitingTime = selected.turnaroundTime - selected.burstTime;
            }
        }
    }
}
