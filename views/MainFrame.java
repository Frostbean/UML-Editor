package views;
import javax.swing.*;

import shapes.RectangleObject;
import shapes.SelectedArea;
import shapes.OvalObject;
import shapes.BaseObject;
import shapes.CompositeObject;
import shapes.Line;
import shapes.AssociationLine;
import shapes.GeneralizationLine;
import shapes.CompositionLine;
import shapes.Shape;
import utils.Port;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

public class MainFrame extends JFrame {
    
    private Canvas canvas;
    private ToolPanel toolPanel = new ToolPanel();;
    private Port startPort = null;
    private Port endPort = null;
    Point selectionStart;
    private int offsetX;
    private int offsetY;
    private SelectedArea selectedArea;
    private Shape selectedShape;

    public MainFrame() {}

    public void init() {
        setup();
        createMenuBar();
        JPanel canvasPanel = createCanvasPanel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolPanel, BorderLayout.WEST);
        getContentPane().add(canvasPanel, BorderLayout.CENTER);
    }

    private void setup() {
        setTitle("UML Editor");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        toolPanel.init();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("File"));
        JMenu editMenu = new JMenu("Edit");
        
        JMenuItem labelItem = new JMenuItem("Label");
        JMenuItem groupItem = new JMenuItem("Group");
        JMenuItem ungroupItem = new JMenuItem("Ungroup");
        labelItem.addActionListener(e -> {
            createLabelDialog();
        });
        groupItem.addActionListener(e -> {
            groupSelectedShapes();
        });
        ungroupItem.addActionListener(e -> {
            ungroupSelectedShape();
        });
        editMenu.add(labelItem);
        editMenu.add(groupItem);
        editMenu.add(ungroupItem);
        
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        return menuBar;
    }

    private JPanel createCanvasPanel() {
        canvas = new Canvas();

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleCanvasMousePress(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleCanvasMouseReleased(e.getPoint());
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleCanvasMouseDragged(e.getPoint());
            }
        });        

        return canvas;
    }

    private void handleCanvasMousePress(Point p) {
        BaseObject newShape = null;

        switch (toolPanel.getCurrentMode()) {
            case SELECT:
                canvas.selectShapes(p);
                selectedShape = canvas.getShape(p);

                selectionStart = p;
                offsetX = 0;
                offsetY = 0;

                if (selectedShape == null) {
                    selectedArea = new SelectedArea(p.x, p.y);
                    canvas.addShape(selectedArea);
                }
                break;
            case ASSOCIATION:
            case GENERALIZATION:
            case COMPOSITION:
                startPort = findNearestPort(p);
                break;
            case RECTANGLE:
                newShape = new RectangleObject((int)p.getX(), (int)p.getY());
                break;
            case OVAL:
                newShape = new OvalObject((int)p.getX(), (int)p.getY());
                break;
            default:
                System.out.println("Unhandled mode: " + toolPanel.getCurrentMode());
                break;
        }

        if (newShape != null) {
            canvas.addShape(newShape);
        }
        canvas.repaint();
    }

    private void handleCanvasMouseDragged(Point p) {
        if (selectionStart != null) {
            offsetX = p.x - selectionStart.x;
            offsetY = p.y - selectionStart.y;
        }

        if (selectedArea != null) {
            selectedArea.translate(offsetX, offsetY);
        }

        if (selectedShape != null) {
            selectedShape.move(offsetX, offsetY);
        }

        selectionStart = p;
        canvas.repaint();
    }

    private void handleCanvasMouseReleased(Point p) {
        endPort = findNearestPort(p);
        if (startPort != null && endPort != null && startPort != endPort) {
            switch (toolPanel.getCurrentMode()) {
                case ASSOCIATION:
                    canvas.addShape(new AssociationLine(startPort, endPort));
                    break;
                case GENERALIZATION:
                    canvas.addShape(new GeneralizationLine(startPort, endPort));
                    break;
                case COMPOSITION:
                    canvas.addShape(new CompositionLine(startPort, endPort));
                    break;
                default:
                    break;
            }
        }
        startPort = null;

        if (selectedArea != null) {
    
            for (Shape s : canvas.getShapes()) {
                if (!(s instanceof SelectedArea) && selectedArea.contains(s.getBounds())) {
                    s.setSelected(true);
                }
            }
    
            canvas.removeShape(selectedArea);
            selectedArea = null;
        }

        canvas.repaint();
    }

    private Port findNearestPort(Point p) {
        for (Shape shape : canvas.getShapes()) {
            if (shape.getPorts() != null) {
                for (Port port : shape.getPorts()) {
                    if (port.contains(p)) return port;
                }
            }
        }
        return null;
    }

    public void groupSelectedShapes() {
        List<Shape> selectedShapes = canvas.getSelectedShapes();
        if (selectedShapes.size() < 2) return;

        CompositeObject group = new CompositeObject();
        for (Shape s : selectedShapes) {
            group.add(s);
            canvas.removeShape(s);
        }
        group.setSelected(true);
        canvas.addShape(group);
        canvas.repaint();
    }

    public void ungroupSelectedShape() {
        List<Shape> selected = canvas.getSelectedShapes();
        if (selected.size() == 1 && selected.get(0) instanceof CompositeObject) {
            CompositeObject group = (CompositeObject) selected.get(0);
            canvas.removeShape(group);
            for (Shape s : group.getShapes()) {
                s.setSelected(true);
                canvas.addShape(s);
            }
            canvas.repaint();
        }
    }

    public void createLabelDialog() {
        List<Shape> selected = canvas.getSelectedShapes();
        if (selected.size() == 1 && (selected.get(0) instanceof RectangleObject || selected.get(0) instanceof OvalObject)) {
            LabelDialog labelDialog = new LabelDialog(this, (BaseObject)selected.get(0));
            labelDialog.setVisible(true);
            canvas.repaint();
        }
    }
}
