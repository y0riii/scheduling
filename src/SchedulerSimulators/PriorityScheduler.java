package SchedulerSimulators;

import GUI.RectanglesPainter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityScheduler extends SchedulerSimulator {

    public PriorityScheduler(RectanglesPainter rectanglesPainter) {
        super(rectanglesPainter);
    }

    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nNon-preemptive Priority Scheduling with Context Switching");

        Process selected = null;

        List<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));

        PriorityQueue<Process> readyProcesses = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));

        int currentTime = 0;
        int index = 0;
        boolean isFirst = true;

        while (true) {
            while (index < sortedProcesses.size() && sortedProcesses.get(index).arrivalTime <= currentTime) {
                readyProcesses.add(sortedProcesses.get(index));
                index++;
            }

            // If no process is available, increment the current time
            if (readyProcesses.isEmpty()) {
                if (index == sortedProcesses.size()) break;
                currentTime += sortedProcesses.get(index).arrivalTime - currentTime;
                continue;
            }

            // Simulate context switching delay
            if (!isFirst) {
                System.out.printf("Time %d: Context switching from Process %s to Process %s\n", currentTime, selected.name, readyProcesses.peek().name);
                currentTime += contextSwitchTime;
            } else {
                isFirst = false;
            }
            selected = readyProcesses.poll();

            rectanglesPainter.addRectangle(currentTime, processes.indexOf(selected), selected.burstTime, selected.color);
            // Execute the selected process
            System.out.printf("Time %d: Process %s with priority %d is executing\n", currentTime, selected.name, selected.priority);
            currentTime += selected.burstTime;
            System.out.printf("Time %d: Process %s has completed execution\n", currentTime, selected.name);

            selected.completed = true;
            selected.turnaroundTime = currentTime - selected.arrivalTime;
            selected.waitingTime = selected.turnaroundTime - selected.burstTime;
        }
    }
}
