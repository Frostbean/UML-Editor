package shapes;

import java.awt.Graphics;
import java.awt.Point;

public class SelectedArea extends BaseObject {
    int offsetX;
    int offsetY;

    public SelectedArea(int x, int y) {
        super(x, y);
        offsetX = 0;
        offsetY = 0;
    }

    public void setOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    public void draw(Graphics g) {
        int tempX;
        int tempY;

        if (offsetX < 0) {
            tempX = x + offsetX;
        }
        else {
            tempX = x;
        }
        if (offsetY < 0) {
            tempY = y + offsetY;
        }
        else {
            tempY = y;
        }

        g.drawRect(tempX, tempY, Math.abs(offsetX), Math.abs(offsetY));
    }

    @Override
    public void initializaPorts() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setPorts() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean contains(Point p) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setPosition(int x, int y) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public RectangleObject getBounds() {
        int tempX;
        int tempY;

        if (offsetX < 0) {
            tempX = x + offsetX;
        }
        else {
            tempX = x;
        }
        if (offsetY < 0) {
            tempY = y + offsetY;
        }
        else {
            tempY = y;
        }

        return new RectangleObject(tempX, tempY, Math.abs(offsetX), Math.abs(offsetY));
    }

    public void translate(int dx, int dy) {
        offsetX += dx;
        offsetY += dy;
    }

    @Override
    protected void setPortsPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPortsPosition'");
    }    
}
