package views;

import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import shapes.SelectedArea;
import shapes.Shape;

public class Canvas extends JPanel {
    private ArrayList<Shape> shapes;

    public Canvas() {
        shapes = new ArrayList<>();
        setBackground(Color.WHITE);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.GRAY);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
    }

    public void addShape(Shape shape) {
        if (shape != null) {
            this.shapes.add(shape);
            this.repaint();
        }
    }

    public void selectShapes(Point p) {
        for (Shape shape : shapes) {
            if (!(shape instanceof SelectedArea)) {
                if (shape.contains(p)) {
                    shape.setSelected(true);
                } else {
                    shape.setSelected(false);
                }
            }
        }
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    public boolean isPointInsideAnyShape(Point p) {
        boolean result = false;
        for (Shape shape : shapes) {
            if (!(shape instanceof SelectedArea)) {
                result = result || shape.contains(p);
                
                if (result) {
                    break;
                }
            }
        }
        return result;
    }

    public Shape getShape(Point p) {
        Shape foundShape = null;
        for (Shape shape : shapes) {
            if (!(shape instanceof SelectedArea)) {
                if (shape.contains(p)) {
                    foundShape = shape;
                };
                
                if (foundShape != null) {
                    break;
                }
            }
        }
        return foundShape;
    }

    public ArrayList<Shape> getSelectedShapes() {
        ArrayList<Shape> selected = new ArrayList<>();
        for (Shape shape : shapes) {
            if (shape.isSelected()) {
                selected.add(shape);
            }
        }
        return selected;
    }
}
