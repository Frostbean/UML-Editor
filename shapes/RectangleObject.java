package shapes;
import java.awt.Graphics;
import java.awt.Point;

import utils.Port;

public class RectangleObject extends BaseObject {
    private int width;
    private int height;

    public RectangleObject(int x, int y) {
        super(x, y);
        portCount = 8;
        width = 80;
        height = 60;
        initializaPorts();
        setPorts();
    }

    public RectangleObject(int x, int y, int width, int height) {
        super(x, y);
        portCount = 8;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect(x, y, width, height);

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
        ports[0] = new Port(x, y);
        ports[1] = new Port(x + width / 2, y);
        ports[2] = new Port(x + width, y);
        ports[3] = new Port(x, y + height / 2);
        ports[4] = new Port(x + width, y + height / 2);
        ports[5] = new Port(x, y + height);
        ports[6] = new Port(x + width / 2, y + height);
        ports[7] = new Port(x + width, y + height);
    }

    @Override
    public boolean contains(Point p) {
        return (p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height);
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
        ports[0].setPosition(x, y);
        ports[1].setPosition(x + width / 2, y);
        ports[2].setPosition(x + width, y);
        ports[3].setPosition(x, y + height / 2);
        ports[4].setPosition(x + width, y + height / 2);
        ports[5].setPosition(x, y + height);
        ports[6].setPosition(x + width / 2, y + height);
        ports[7].setPosition(x + width, y + height);
    }
}
