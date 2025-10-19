import javax.swing.SwingUtilities;

import views.MainFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame tool = new MainFrame();
            tool.init();
            tool.setVisible(true);
        });
    }
}
