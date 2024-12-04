package SchedulerSimulators;

import java.util.Comparator;
import java.util.List;

public class PriorityScheduler implements SchedulerSimulator {
    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nNon-preemptive Priority Scheduling with Context Switching");
        if (processes.isEmpty()) return;
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = processes.getFirst().arrivalTime;
        boolean isFirst = true;

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
            if (!isFirst) {
                System.out.println("Time " + currentTime + ": Context switching to Process " + selected.name);
                currentTime += contextSwitchTime;
            } else {
                isFirst = false;
            }

            // Execute the selected process
            System.out.println("Time " + currentTime + ": Process " + selected.name + " with priority " + selected.priority + " starts executing");
            currentTime += selected.burstTime;
            System.out.println("Time " + currentTime + ": Process " + selected.name + " has completed execution");
            selected.completed = true;

            selected.turnaroundTime = currentTime - selected.arrivalTime;
            selected.waitingTime = selected.turnaroundTime - selected.burstTime;
        }
    }
}
