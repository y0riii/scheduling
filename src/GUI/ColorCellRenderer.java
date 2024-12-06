package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

// Custom TableCellRenderer for rendering color square
class ColorCellRenderer extends DefaultTableCellRenderer {
    private Color squareColor;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Set the label to have no text and background color based on the hex value
        setText("");
        squareColor = Color.decode((String) value);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int squareSize = 14;
        int x = (getWidth() - squareSize) / 2;
        int y = (getHeight() - squareSize) / 2;

        g.setColor(squareColor);
        g.fillRect(x, y, squareSize, squareSize);
    }
}