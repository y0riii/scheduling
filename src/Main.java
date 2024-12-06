import GUI.LabelsMaker;
import GUI.MainFrame;
import GUI.RectanglesPainter;
import SchedulerSimulators.Process;
import SchedulerSimulators.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final RectanglesPainter rectanglesPainter = new RectanglesPainter();

    public static void main(String[] args) {

        List<Process> processes = new ArrayList<>();

        // Adding example processes
        processes.add(new Process("P1", "#ff0000", 0, 17, 4, 4)); // name, color, arrivalTime, burstTime, priority, quantum
        processes.add(new Process("P2", "#0000ff", 3, 6, 9, 3));
        processes.add(new Process("P3", "#00ff00", 4, 10, 3, 5));
        processes.add(new Process("P4", "#ffff00", 29, 4, 8, 2));

        // Testing Non-Preemptive Priority Scheduling with Context Switching
        SchedulerSimulator scheduler = selectScheduler();
        List<Process> processesCopy = deepCopy(processes);
        scheduler.schedule(processesCopy, 0);
        scheduler.printResults(processesCopy);

        MainFrame frame = new MainFrame();
        rectanglesPainter.sizeHandler(processes.size());
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(new LabelsMaker(processes));
        mainPanel.add(rectanglesPainter);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    // Helper method to create a deep copy of the process list
    private static List<Process> deepCopy(List<Process> original) {
        List<Process> copy = new ArrayList<>();
        for (Process p : original) {
            copy.add(new Process(p));
        }
        return copy;
    }

    private static SchedulerSimulator selectScheduler() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select scheduler: ");
        System.out.println("1. Priority scheduler");
        System.out.println("2. Shortest job first scheduler");
        System.out.println("3. Shortest remaining time first scheduler");
        System.out.println("4. FCAI scheduler");
        int choice = scanner.nextInt();

        while (choice < 1 || choice > 4) {
            System.out.println("Invalid choice, please try again.");
            choice = scanner.nextInt();
        }

        scanner.close();

        return switch (choice) {
            case 1 -> new PriorityScheduler(rectanglesPainter);
            case 2 -> new ShortestJobFirstScheduler(rectanglesPainter);
            case 3 -> new ShortestRemainingTimeFirstScheduler(rectanglesPainter);
            default -> new FCAI_Scheduler(rectanglesPainter);
        };
    }
}