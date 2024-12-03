import java.util.*;

public class SchedulerSimulator {
    // Non-preemptive Priority Scheduling with Context Switching
    public static void priorityScheduling(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nNon-preemptive Priority Scheduling with Context Switching");
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        while (true) {
            Process selected = null;

            // Select the highest priority process available
            for (Process p : processes) {
                if (!p.completed && p.arrivalTime <= currentTime &&
                    (selected == null || p.priority < selected.priority)) {
                    selected = p;
                }
            }

            // If no process is available, increment the current time
            if (selected == null) {
                if (processes.stream().allMatch(p -> p.completed)) break;
                currentTime++;
                continue;
            }

            // Simulate context switching delay
            if (currentTime > 0) {
                System.out.println("Time " + currentTime + ": Context switching to Process " + selected.name);
                currentTime += contextSwitchTime;
            }

            // Execute the selected process
            System.out.println("Time " + currentTime + ": Process " + selected.name + " with priority " + selected.priority + " starts executing");
            currentTime += selected.burstTime;
            System.out.println("Time " + currentTime + ": Process " + selected.name + " has completed execution");
            selected.completed = true;
        }
    }

    // Non-preemptive Shortest Job First (SJF) Scheduling with Context Switching
    public static void shortestJobFirst(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nNon-preemptive Shortest Job First (SJF) Scheduling with Context Switching");
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        while (true) {
            Process selected = null;

            // Select the shortest job available
            for (Process p : processes) {
                if (!p.completed && p.arrivalTime <= currentTime &&
                    (selected == null || p.burstTime < selected.burstTime)) {
                    selected = p;
                }
            }

            // If no process is available, increment the current time
            if (selected == null) {
                if (processes.stream().allMatch(p -> p.completed)) break;
                currentTime++;
                continue;
            }

            // Simulate context switching delay
            if (currentTime > 0) {
                System.out.println("Time " + currentTime + ": Context switching to Process " + selected.name);
                currentTime += contextSwitchTime;
            }

            // Execute the selected process
            System.out.println("Time " + currentTime + ": Process " + selected.name + " with burst time " + selected.burstTime + " starts executing");
            currentTime += selected.burstTime;
            System.out.println("Time " + currentTime + ": Process " + selected.name + " has completed execution");
            selected.completed = true;
        }
    }

    // Shortest Remaining Time First (SRTF) Scheduling with Context Switching
    public static void shortestRemainingTimeFirst(List<Process> processes, int contextSwitchTime) {
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
    

    // Main results printer
    public static void printResults(List<Process> processes) {
        System.out.println("\n--- Scheduling Results ---");
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        for (Process p : processes) {
            System.out.printf("Process %s - Waiting Time: %d, Turnaround Time: %d\n",
                    p.name, p.waitingTime, p.turnaroundTime);
            totalWaitingTime += p.waitingTime;
            totalTurnaroundTime += p.turnaroundTime;
        }

        System.out.printf("Average Waiting Time: %.2f\n", (double) totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTurnaroundTime / processes.size());
    }
}
