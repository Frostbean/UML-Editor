package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class CompositeObject extends Shape {
    private ArrayList<Shape> shapes = new ArrayList<>();

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void remove(Shape shape) {
        shapes.remove(shape);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    @Override
    public void draw(Graphics g) {
        if (isSelected()) {
            RectangleObject bounds = getBounds();
            g.setColor(Color.BLUE);
            ((Graphics2D) g).setStroke(new BasicStroke(1));
            g.drawRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        }
        
        g.setColor(Color.GRAY);
        for (Shape s : shapes) {
            s.draw(g);
        }
    }

    @Override
    public boolean contains(Point p) {
        for (Shape s : shapes) {
            if (s.contains(p)) return true;
        }
        return false;
    }

    @Override
    public void setSelected(boolean selected) {
        for (Shape s : shapes) {
            s.setSelected(selected);
        }
    }

    @Override
    public boolean isSelected() {
        return shapes.stream().allMatch(Shape::isSelected);
    }

    @Override
    public RectangleObject getBounds() {
        if (shapes.isEmpty()) return new RectangleObject(0, 0);
    
        RectangleObject bounds = shapes.get(0).getBounds();
        for (int i = 1; i < shapes.size(); i++) {
            bounds = union(bounds, shapes.get(i).getBounds());
        }
    
        return bounds;
    }

    public static RectangleObject union(RectangleObject r1, RectangleObject r2) {
        int x1 = Math.min(r1.x, r2.x);
        int y1 = Math.min(r1.y, r2.y);
        int x2 = Math.max(r1.x + r1.getWidth(), r2.x + r2.getWidth());
        int y2 = Math.max(r1.y + r1.getHeight(), r2.y + r2.getHeight());
    
        return new RectangleObject(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public void move(int offsetX, int offsetY) {
        for (Shape shape : shapes) {
            if (shape instanceof BaseObject) {
                BaseObject base = (BaseObject)shape;
                base.move(offsetX, offsetY);
            }
            else if (shape instanceof CompositeObject) {
                ((CompositeObject)shape).move(offsetX, offsetY);
            }
        }
    }
}
