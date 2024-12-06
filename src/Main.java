import GUI.LabelsMaker;
import GUI.MainFrame;
import GUI.RectanglesPainter;
import SchedulerSimulators.Process;
import SchedulerSimulators.*;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final RectanglesPainter rectanglesPainter = new RectanglesPainter();

    public static void main(String[] args) {
        final String inputFilePath = "input.txt";
        final int contextSwitchTime = 2;

        List<Process> processes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Process process = getProcess(line);
                processes.add(process);
            }
        } catch (IOException e) {
            System.err.println("Error reading the input file.");
            e.printStackTrace();
        }
        if (processes.isEmpty()) {
            System.out.println("there's no processes to simulate scheduling\nExiting...");
            return;
        }

        SchedulerSimulator scheduler = selectScheduler();
        List<Process> processesCopy = deepCopy(processes);
        scheduler.schedule(processesCopy, contextSwitchTime);
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

    private static Process getProcess(String line) {
        String[] processData = line.split(" ");
        String name = processData[0];
        String color = processData[1];
        int arrivalTime = Integer.parseInt(processData[2]);
        int burstTime = Integer.parseInt(processData[3]);
        int priority = Integer.parseInt(processData[4]);
        int quantum = Integer.parseInt(processData[5]);

        return new Process(name, color, arrivalTime, burstTime, priority, quantum);
    }
}