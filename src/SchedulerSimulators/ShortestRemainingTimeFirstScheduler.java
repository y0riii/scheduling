package SchedulerSimulators;

import GUI.RectanglesPainter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ShortestRemainingTimeFirstScheduler extends SchedulerSimulator {

    public ShortestRemainingTimeFirstScheduler(RectanglesPainter rectanglesPainter) {
        super(rectanglesPainter);
    }

    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nShortest Remaining Time First (SRTF) Scheduling with Context Switching");
        if (processes.isEmpty()) return;
        int currentTime = 0;
        int ranFor = 0;
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
                if (processes.stream().allMatch(p -> p.completed)) {
                    rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(runningProcess), ranFor, runningProcess.color);
                    break;
                }
                currentTime++;
                continue;
            }

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
            selected.remainingTime--;
            currentTime++;
            ranFor++;

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
