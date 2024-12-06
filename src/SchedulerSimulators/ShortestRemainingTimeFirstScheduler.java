package SchedulerSimulators;

import GUI.RectanglesPainter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class ShortestRemainingTimeFirstScheduler extends SchedulerSimulator {

    public ShortestRemainingTimeFirstScheduler(RectanglesPainter rectanglesPainter) {
        super(rectanglesPainter);
    }

    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nShortest Remaining Time First (SRTF) Scheduling with Context Switching");

        List<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));

        PriorityQueue<Process> readyProcesses = new PriorityQueue<>(Comparator.comparingInt(p -> p.remainingTime));

        int currentTime = 0;
        int ranFor = 0;
        int index = 0;
        Process runningProcess = null;

        while (true) {
            if (currentTime == 19) {
                System.out.println("hello");
            }
            // Add newly arrived processes to the ready queue
            while (index < sortedProcesses.size() && sortedProcesses.get(index).arrivalTime <= currentTime) {
                readyProcesses.add(sortedProcesses.get(index));
                index++;
            }

            // Handle idle time if no process is ready
            if (readyProcesses.isEmpty()) {
                if (index == sortedProcesses.size()) {
                    rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(runningProcess), ranFor, runningProcess.color);
                    break;
                }
                currentTime += sortedProcesses.get(index).arrivalTime - currentTime;
                continue;
            }

            Process selected = readyProcesses.peek();

            if (runningProcess == null) {
                runningProcess = selected;
            }

            // Context switching handling
            if (runningProcess != selected) {
                rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(runningProcess), ranFor, runningProcess.color);
                System.out.printf("Time %d: Context switching from Process %s to Process %s\n",
                        currentTime, runningProcess.name, selected.name);
                currentTime += contextSwitchTime;
                runningProcess = selected;
                ranFor = 0;
            }

            // Execute the selected process for one time unit
            if (ranFor == 0) {
                System.out.printf("Time %d: Process %s with remaining time %d is executing\n",
                        currentTime, selected.name, selected.remainingTime);
            }
            int extraRunningTime = selected.remainingTime;
            if (index < sortedProcesses.size() && sortedProcesses.get(index).arrivalTime - currentTime < extraRunningTime) {
                extraRunningTime = sortedProcesses.get(index).arrivalTime - currentTime;
            }
            selected.remainingTime -= extraRunningTime;
            currentTime += extraRunningTime;
            ranFor += extraRunningTime;

            // Mark process as completed if its remaining time is zero
            if (selected.remainingTime == 0) {
                System.out.printf("Time %d: Process %s has completed execution\n", currentTime, selected.name);
                selected.completed = true;
                selected.turnaroundTime = currentTime - selected.arrivalTime;
                selected.waitingTime = selected.turnaroundTime - selected.burstTime;
                readyProcesses.poll();
            }
        }
    }
}
