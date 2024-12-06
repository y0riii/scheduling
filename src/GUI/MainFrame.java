package GUI;

import SchedulerSimulators.Process;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final RectanglesPainter rectanglesPainter;
    List<Process> processes;
    int height = 400;
    private final String[] statsData;

    public MainFrame(RectanglesPainter rectanglesPainter, List<Process> processes, String[] statsData) {
        this.rectanglesPainter = rectanglesPainter;
        this.processes = processes;
        this.statsData = statsData;
        this.setTitle("Scheduler Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(850, 600);
        this.setResizable(false);
    }

    public void showGUI() {
        this.add(getMainPanel());
        this.setVisible(true);
    }

    private JScrollPane getSchedulingGraph() {
        Dimension rectanglesSize = rectanglesPainter.sizeHandler(processes.size());
        JPanel schedulingGraph = new JPanel();
        schedulingGraph.setLayout(new BoxLayout(schedulingGraph, BoxLayout.X_AXIS));

        LabelsMaker labelPanel = new LabelsMaker(processes);

        labelPanel.setMaximumSize(labelPanel.getPreferredSize());
        rectanglesPainter.setMaximumSize(rectanglesSize);
        schedulingGraph.add(labelPanel);
        schedulingGraph.add(rectanglesPainter);
        schedulingGraph.setBackground(new Color(0x2d2d2d));
        JScrollPane scrollSchedulingGraph = new JScrollPane(schedulingGraph);
        if (rectanglesSize.height < 385) {
            height = rectanglesSize.height + 15;
        }
        scrollSchedulingGraph.setPreferredSize(new Dimension(550, height));
        scrollSchedulingGraph.setMaximumSize(new Dimension(550, height));
        scrollSchedulingGraph.setBorder(new EmptyBorder(0, 30, 0, 30));
        scrollSchedulingGraph.setAlignmentY(Component.TOP_ALIGNMENT);
        scrollSchedulingGraph.setBackground(null);
        return scrollSchedulingGraph;
    }

    private JScrollPane getTablePane() {
        String[] columns = {"Process", "Color", "Name", "Priority"};

        Object[][] data = new Object[processes.size()][4];
        for (int i = 0; i < processes.size(); i++) {
            data[i][0] = i;
            data[i][1] = processes.get(i).getColor();
            data[i][2] = processes.get(i).getName();
            data[i][3] = processes.get(i).getPriority();
        }

        JTable table = new JTable(data, columns);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setForeground(Color.white);
        table.getColumnModel().getColumn(1).setCellRenderer(new ColorCellRenderer());
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        table.setRowHeight(60);
        table.setShowGrid(false);
        table.setBackground(new Color(0x2d2d2d));
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(new Color(0x2d2d2d));
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        headerRenderer.setForeground(Color.white);
        table.getTableHeader().setDefaultRenderer(headerRenderer);

        table.getColumnModel().getColumns().asIterator().forEachRemaining(column -> column.setResizable(false));
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setEnabled(false);
        table.setBorder(null);
        JScrollPane tablePane = new JScrollPane(table);
        tablePane.setPreferredSize(new Dimension(250, height));
        tablePane.setMaximumSize(new Dimension(250, height));
        tablePane.setAlignmentY(Component.TOP_ALIGNMENT);
        tablePane.setBackground(null);
        tablePane.setBorder(new EmptyBorder(0, 0, 0, 0));
        return tablePane;
    }

    private JPanel getMainPanel() {
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.X_AXIS));
        dataPanel.add(getSchedulingGraph());
        dataPanel.add(getTablePane());
        dataPanel.setBackground(null);
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        JLabel label = new JLabel("Statistics");
        label.setForeground(Color.white);
        statsPanel.add(label);
        for (String data : statsData) {
            label = new JLabel(data);
            label.setForeground(Color.white);
            statsPanel.add(label);
        }
        dataPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setBorder(new EmptyBorder(10, 30, 0, 0));
        statsPanel.setBackground(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(dataPanel);
        mainPanel.add(statsPanel);
        mainPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
        mainPanel.setBackground(new Color(0x1f2021));
        return mainPanel;
    }
}
