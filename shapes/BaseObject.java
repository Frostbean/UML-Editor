package shapes;

import java.awt.Color;
import utils.Label;
import utils.Label.LabelShape;

public abstract class BaseObject extends Shape {
    protected int x;
    protected int y;
    protected Label label;
    protected boolean labelVisible = false;

    public BaseObject(int x, int y) {
        this.x = x;
        this.y = y;

        label = new Label();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void move(int offsetX, int offsetY) {
        x += offsetX;
        y += offsetY;

        setPortsPosition();
    }

    public void setLabel(String text, Color color, LabelShape shape, int fontSize) {
        label.setLabel(text, color, shape, fontSize);
        labelVisible = true;
    }

    public String getLabelText() {
        return label.getLabelText();
    }
    
    public LabelShape getLabelShape() {
        return label.getLabelShape();
    }

    public Color getLabelColor() {
        return label.getLabelColor();
    }

    public int getLabelFontSize() {
        return label.getLabelFontSize();
    }

    public abstract void initializaPorts();
    public abstract void setPorts();
    public abstract void setPosition(int x, int y);
    protected abstract void setPortsPosition();
}
