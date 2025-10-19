package shapes;

import java.awt.Graphics;
import java.awt.Point;

import utils.Port;

public class OvalObject extends BaseObject {
    private int width;
    private int height;

    public OvalObject(int x, int y) {
        super(x, y);
        portCount = 4;
        width = 100;
        height = 60;
        initializaPorts();
        setPorts();
    }

    public OvalObject(int x, int y, int width, int height) {
        super(x, y);
        portCount = 4;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        g.fillOval(x, y, width, height);

        if (selected) {
            for (Port port : ports) {
                g.fillRect(port.getX() - 4, port.getY() - 4, 8, 8);
            }
        }

        if (labelVisible) {
            label.draw(g, x, y, width, height);
        }
    }

    @Override
    public void initializaPorts() {
        ports = new Port[portCount];
    }

    @Override
    public void setPorts() {
        ports[0] = new Port(x + width / 2, y);
        ports[1] = new Port(x, y + height / 2);
        ports[2] = new Port(x + width / 2, y + height);
        ports[3] = new Port(x + width, y + height / 2);
    }

    @Override
    public boolean contains(Point p) {
        double a = width / 2.0;
        double b = height / 2.0;
        double centerX = x + a;
        double centerY = y + b;

        double dx = p.x - centerX;
        double dy = p.y - centerY;

        return (dx * dx) / (a * a) + (dy * dy) / (b * b) <= 1.0;
    }

    @Override
    public RectangleObject getBounds() {
        return new RectangleObject(x, y, width, height);
    }

    @Override
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

        setPortsPosition();
    }

    @Override
    protected void setPortsPosition() {
        ports[0].setPosition(x + width / 2, y);
        ports[1].setPosition(x, y + height / 2);
        ports[2].setPosition(x + width / 2, y + height);
        ports[3].setPosition(x + width, y + height / 2);
    }
}
