package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RectanglesPainter extends JPanel {

    private final ArrayList<Rectangle> rectangles = new ArrayList<>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw multiple rectangles next to each other with a gap between them
        for (Rectangle rectangle : rectangles) {
            g.setColor(rectangle.color);
            g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    public void addRectangle(int x, int y, int width, String color) {
        Rectangle rectangle = new Rectangle(x, y, width, color);
        rectangles.add(rectangle);
    }

    public void sizeHandler(int numberOfProcesses) {
        Rectangle rectangle = rectangles.getLast();
        int x = rectangle.x + rectangle.width + 5;
        int y = numberOfProcesses * 60;
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.black);
    }
}
