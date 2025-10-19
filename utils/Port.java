package utils;

import java.awt.Point;
import java.awt.Rectangle;

public class Port {
    private int x;
    private int y;
    private int side = 16;

    public Port() {}

    public Port(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean contains(Point point) {
        return new Rectangle(x - side / 2, y - side / 2, side, side).contains(point);
    }
}
