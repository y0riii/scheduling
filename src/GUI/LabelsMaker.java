package GUI;

import SchedulerSimulators.Process;

import javax.swing.*;
import java.util.List;


public class LabelsMaker extends JPanel {
    public LabelsMaker(List<Process> processes) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (int i = 0; i < processes.size(); i++) {
            JLabel label = new JLabel(processes.get(i).getName());
            label.setAlignmentX(CENTER_ALIGNMENT); // Align the label itself to the center
            this.add(label);
            if (i < processes.size() - 1) {
                this.add(Box.createVerticalStrut(45)); // Add space between labels
            }
        }
    }
}
