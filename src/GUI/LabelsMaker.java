package GUI;

import SchedulerSimulators.Process;

import javax.swing.*;
import java.util.List;


public class LabelsMaker extends JPanel {
    public LabelsMaker(List<Process> processes){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (int i = 0; i < processes.size() - 1; i++) {
            this.add(new JLabel(processes.get(i).getName()));
            this.add(Box.createVerticalStrut(45));
        }
        this.add(new JLabel(processes.getLast().getName()));
    }
}
