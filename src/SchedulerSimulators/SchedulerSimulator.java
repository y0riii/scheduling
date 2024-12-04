package SchedulerSimulators;

import java.util.List;

public interface SchedulerSimulator {
    void schedule(List<Process> processes, int contextSwitchTime);

    // Main results printer
    default void printResults(List<Process> processes) {
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
