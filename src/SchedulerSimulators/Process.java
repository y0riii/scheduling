package SchedulerSimulators;

public class Process {
    String name;
    String color;
    int arrivalTime;
    int burstTime;
    int priority;
    int quantum;
    int remainingTime;
    int waitingTime;
    int turnaroundTime;
    boolean completed;
    int fcaiFactor;

    public Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
        this.quantum = quantum;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.completed = false;
    }

    public Process(Process other) {
        this.name = other.name;
        this.color = other.color;
        this.arrivalTime = other.arrivalTime;
        this.burstTime = other.burstTime;
        this.priority = other.priority;
        this.quantum = other.quantum;
        this.remainingTime = other.remainingTime;
        this.waitingTime = other.waitingTime;
        this.turnaroundTime = other.turnaroundTime;
        this.completed = other.completed;
        this.fcaiFactor = other.fcaiFactor;
    }

    public String getName() {
        return name;
    }
}