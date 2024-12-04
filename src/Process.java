class Process {
    String name;
    String color;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int priority;
    int quantum;
    int waitingTime;
    int turnaroundTime;
    boolean completed;
    public double fcaiFactor;

    Process(String name, String color, int arrivalTime, int burstTime, int priority, int quantum) {
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
}