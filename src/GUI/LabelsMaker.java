package GUI;

import SchedulerSimulators.Process;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;


public class LabelsMaker extends JPanel {
    public LabelsMaker(List<Process> processes) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(new EmptyBorder(0, 5, 0, 5));
        this.setBackground(null);
        this.add(Box.createVerticalStrut(20));
        for (int i = 0; i < processes.size(); i++) {
            JLabel label = new JLabel(processes.get(i).getName());
            label.setForeground(Color.white);
            label.setAlignmentX(CENTER_ALIGNMENT); // Align the label itself to the center
            this.add(label);
            if (i != processes.size() - 1) {
                this.add(Box.createVerticalStrut(45));
            }
        }
        this.setAlignmentY(Component.TOP_ALIGNMENT);
    }
}
