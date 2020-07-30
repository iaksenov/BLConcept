package csi.pos.ui.swing;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

public class LayerPanel {

    private JPanel jPanel;

    public LayerPanel() {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setOpaque(true);
        jPanel.setBackground(Color.WHITE);
    }

    public JPanel getPanel() {
        return jPanel;
    }

    public void showForm(JPanel panel) {
        jPanel.removeAll();
        jPanel.add(panel, BorderLayout.CENTER);
        jPanel.invalidate();
    }

}
