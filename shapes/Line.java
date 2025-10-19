package shapes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import utils.Port;

public abstract class Line extends Shape {
    public Line(Port from, Port to) {
        portCount = 2;

        ports = new Port[2];
        ports[0] = from;
        ports[1] = to;
    }

    @Override
    public void draw(Graphics g) {
        int x1 = ports[0].getX();
        int y1 = ports[0].getY();
        int x2 = ports[1].getX();
        int y2 = ports[1].getY();
        
        g.drawLine(x1, y1, x2, y2);
        drawArrow((Graphics2D)g, x1, y1, x2, y2);

        if (selected) {
            for (Port port : ports) {
                g.fillRect(port.getX() - 4, port.getY() - 4, 8, 8);
            }
        }
    }

    protected abstract void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2);

    @Override
    public boolean contains(Point p) {
        int tolerance = 5;

        double distance = pointToSegmentDistance(p.x, p.y, ports[0].getX(), ports[0].getY(), ports[1].getX(), ports[1].getY());
        return distance <= tolerance;
    }

    public double pointToSegmentDistance(double px, double py,
                                        double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        if (dx == 0 && dy == 0) {
            dx = px - x1;
            dy = py - y1;
            return Math.sqrt(dx * dx + dy * dy);
        }

        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));

        double projX = x1 + t * dx;
        double projY = y1 + t * dy;

        dx = px - projX;
        dy = py - projY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public RectangleObject getBounds() {
        int x = Math.min(ports[0].getX(), ports[1].getX());
        int y = Math.min(ports[0].getY(), ports[1].getY());
        int width = Math.abs(ports[0].getX() - ports[1].getX());
        int height = Math.abs(ports[0].getY() - ports[1].getY());
        
        return new RectangleObject(x, y, width, height);
    }

    @Override
    public void move(int offsetX, int offsetY) {}
}
