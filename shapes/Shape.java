package shapes;

import java.awt.Graphics;
import java.awt.Point;
import utils.Port;

public abstract class Shape {
    protected boolean selected;
    protected int portCount;
    protected Port[] ports;
    
    public abstract void draw(Graphics g);
    public abstract boolean contains(Point p);
    public abstract RectangleObject getBounds();
    public abstract void move(int offsetX, int offsetY);

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public Port[] getPorts() {
        return ports;
    }

    public boolean contains(RectangleObject rect) {
        RectangleObject bounds = getBounds();
        Point pointA = new Point(rect.getX(), rect.getY());
        Point pointB = new Point(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
        return (bounds.contains(pointA) && bounds.contains(pointB));
    }
}
