package views;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ToolPanel extends JPanel {
    public enum EditorMode {
        SELECT,
        ASSOCIATION,
        GENERALIZATION,
        COMPOSITION,
        RECTANGLE,
        OVAL
    }

    private EditorMode currentMode = EditorMode.SELECT;
    private JButton[] buttons;
    private String[] tips = {"select", "association", "generalization", "composition", "rect", "oval"};
    private String[] paths = {"images/select.png", "images/association.png", "images/generalization.png", "images/composition.png", "images/rect.png", "images/oval.png"};
    private EditorMode[] modes = {EditorMode.SELECT, EditorMode.ASSOCIATION, EditorMode.GENERALIZATION, EditorMode.COMPOSITION, EditorMode.RECTANGLE, EditorMode.OVAL};

    public ToolPanel() {}

    public void init() {
        this.setLayout(new GridLayout(6, 1, 5, 5));
        this.setPreferredSize(new Dimension(80, 0));

        buttons = createToolButtons(tips, paths, modes);
        for (JButton button : buttons) {
            this.add(button);
        }
    }

    private JButton createToolButton(String path) {
        ImageIcon icon = new ImageIcon(path);
        JButton button = new JButton(icon);
        
        return button;
    }

    private JButton[] createToolButtons(String[] tips, String[] paths, EditorMode[] modes) {
        JButton[] buttons = new JButton[paths.length];
        JButton button;

        for (int i = 0; i < paths.length; i++) {
            final int index = i;

            button = createToolButton(paths[i]);
            button.setToolTipText(tips[i]);
            button.addActionListener(e -> {
                setCurrentMode(modes[index]);
                updateToolButtonStyle(buttons[index]);
            });
            buttons[i] = button;
        }

        return buttons;
    }

    private void setCurrentMode(EditorMode mode) {
        this.currentMode = mode;
    }

    public EditorMode getCurrentMode() {
        return currentMode;
    }

    private void updateToolButtonStyle(JButton selectedButton) {
        for (JButton botton : buttons) {
            botton.setBackground(null);
            botton.setForeground(Color.BLACK);
        }
        selectedButton.setBackground(Color.BLACK);
        selectedButton.setForeground(Color.WHITE);
    }
}
