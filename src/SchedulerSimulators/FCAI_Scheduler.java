package SchedulerSimulators;

import GUI.RectanglesPainter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCAI_Scheduler extends SchedulerSimulator {

    private double v1;
    private double v2;
    public FCAI_Scheduler(RectanglesPainter rectanglesPainter) {
        super(rectanglesPainter);
    }

    @Override
    public void schedule(List<Process> processes, int contextSwitchTime) {
        System.out.println("\nFCAI Scheduling with Context Switching");
        if (processes.isEmpty()) return;

        List<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(p -> p.arrivalTime));

        Process selected = sortedProcesses.getLast();
        v1 = (double) selected.arrivalTime / 10.0;

        selected = processes.stream()
                .max(Comparator.comparingInt(p -> p.burstTime)).get();
        v2 = (double) selected.burstTime / 10.0;
        int currentTime = 0;
        int ranFor = 0;
        int index = 0;

        List<Process> readyProcesses = new ArrayList<>();

        while (true) {
            if (currentTime == 8){
                System.out.println("hello");
            }
            List<Process> extra = new ArrayList<>();
            while (index < sortedProcesses.size()) {
                Process process = sortedProcesses.get(index);
                if (process.arrivalTime > currentTime) break;
                updateFcaiFactor(process);
                extra.add(process);
                index++;
            }
            extra.sort(Comparator.comparingInt((Process p) -> p.arrivalTime).thenComparingDouble(p -> p.fcaiFactor));
            readyProcesses.addAll(extra);

            if (readyProcesses.isEmpty() && index == processes.size()) {
                rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(selected), ranFor, selected.color);
                break;
            } else if (readyProcesses.isEmpty()) {
                currentTime++;
                continue;
            }

            if (ranFor != 0) {
                Process first = readyProcesses.getFirst();
                selected = readyProcesses.stream()
                        .min(Comparator.comparingInt(p -> p.fcaiFactor))
                        .orElse(null);
                if (selected != first) {
                    rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(first), ranFor, first.color);
                    updateFcaiFactor(first);
                    first.quantum += first.quantum - ranFor;
                    System.out.printf("Time %d: Context switching from Process %s to Process %s\n",
                            currentTime, first.name, selected.name);
                    readyProcesses.removeFirst();
                    readyProcesses.remove(selected);
                    readyProcesses.add(first);
                    readyProcesses.addFirst(selected);
                    ranFor = 0;
                }
            }

            if (ranFor == 0) {
                if (selected.completed) {
                    System.out.printf("Time %d: Context switching from Process %s to Process %s\n",
                            currentTime, selected.name, readyProcesses.getFirst().name);
                }
                selected = readyProcesses.getFirst();
                System.out.printf("Time %d: Process %s with remaining time %d is executing\n",
                        currentTime, selected.name, selected.remainingTime);
                ranFor = (int) Math.ceil(selected.quantum * 0.4);
                if (ranFor > selected.remainingTime) {
                    ranFor = selected.remainingTime;
                }
                selected.remainingTime -= ranFor;
                currentTime += ranFor;
            } else if (ranFor < selected.quantum) {
                ranFor++;
                currentTime++;
                selected.remainingTime--;
            } else {
                updateFcaiFactor(selected);
                selected.quantum += 2;
                readyProcesses.add(readyProcesses.getFirst());
                readyProcesses.removeFirst();
                rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(selected), ranFor, selected.color);
                System.out.printf("Time %d: Context switching from Process %s to Process %s\n",
                        currentTime, selected.name, readyProcesses.getFirst().name);
                ranFor = 0;
            }

            if (selected.remainingTime == 0) {
                rectanglesPainter.addRectangle(currentTime - ranFor, processes.indexOf(selected), ranFor, selected.color);
                System.out.printf("Time %d: Process %s has completed execution\n", currentTime, selected.name);
                selected.completed = true;
                selected.turnaroundTime = currentTime - selected.arrivalTime;
                selected.waitingTime = selected.turnaroundTime - selected.burstTime;
                readyProcesses.removeFirst();
                ranFor = 0;
            }
        }
    }

    private void updateFcaiFactor(Process process){
        process.fcaiFactor = (10 - process.priority) + (int) Math.ceil(process.arrivalTime / v1) + (int) Math.ceil(process.remainingTime / v2);
    }
}
