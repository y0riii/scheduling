package SchedulerSimulators;

import GUI.RectanglesPainter;

import java.util.List;

public class ShortestJobFirstScheduler extends SchedulerSimulator {

    public ShortestJobFirstScheduler(RectanglesPainter rectanglesPainter) {
        super(rectanglesPainter);
    }

    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nNon-preemptive Shortest Job First (SJF) Scheduling with Context Switching");
        if (processes.isEmpty()) return;
        int currentTime = 0;
        int index = 0;
        boolean isFirst = true;

        while (true) {
            Process selected = null;

            // Select the shortest job available
            for (int i = 0; i < processes.size(); i++) {
                if (!processes.get(i).completed && processes.get(i).arrivalTime <= currentTime &&
                        (selected == null || processes.get(i).burstTime < selected.burstTime)) {
                    selected = processes.get(i);
                    index = i;
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

            rectanglesPainter.addRectangle(currentTime, index, selected.burstTime, selected.color);
            // Execute the selected process
            System.out.println("Time " + currentTime + ": Process " + selected.name + " with burst time " + selected.burstTime + " starts executing");
            currentTime += selected.burstTime;
            System.out.println("Time " + currentTime + ": Process " + selected.name + " has completed execution");
            selected.completed = true;

            selected.turnaroundTime = currentTime - selected.arrivalTime;
            selected.waitingTime = selected.turnaroundTime - selected.burstTime;
        }
    }
}
