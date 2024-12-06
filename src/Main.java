import GUI.MainFrame;
import GUI.RectanglesPainter;
import SchedulerSimulators.Process;
import SchedulerSimulators.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final RectanglesPainter rectanglesPainter = new RectanglesPainter();

    private static final String[] statsData = new String[3];

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
        String[] results = scheduler.printResults(processesCopy);
        statsData[1] = results[0];
        statsData[2] = results[1];

        MainFrame frame = new MainFrame(rectanglesPainter, processes, statsData);
        frame.showGUI();
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

        statsData[0] = "Scheduler name: ";

        switch (choice) {
            case 1:
                statsData[0] += "Priority Scheduler";
                return new PriorityScheduler(rectanglesPainter);
            case 2:
                statsData[0] += "Shortest Job First Scheduler";
                return new ShortestJobFirstScheduler(rectanglesPainter);
            case 3:
                statsData[0] += "Shortest Remaining Time First Scheduler";
                return new ShortestRemainingTimeFirstScheduler(rectanglesPainter);
        }
        statsData[0] += "FCAI Scheduler";
        return new FCAI_Scheduler(rectanglesPainter);
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