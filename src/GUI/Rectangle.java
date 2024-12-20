package GUI;

import java.awt.*;

public class Rectangle {
    public int x, y, width, height;
    Color color;

    public Rectangle(int x, int y, int width, String color) {
        this.x = x * 5 + 5;
        this.y = y * 60 + 5;
        this.width = width * 5;
        this.height = 50;
        this.color = Color.decode(color);
    }
}
