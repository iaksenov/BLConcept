package csi.pos.ui.swing.popup;

import csi.pos.ui.swing.LayerPanel;
import csi.pos.ui.swing.forms.Form;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;

public class ScreenSaverLayerPanel extends LayerPanel {

    public ScreenSaverLayerPanel() {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setOpaque(true);
        Color back = new Color(16, 71, 60, 255);
        jPanel.setBackground(back);
        JLabel jLabel = new JLabel("This is screen saver");
        jLabel.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel.setBackground(back);
        jLabel.setForeground(Color.WHITE);
        jPanel.add(jLabel, BorderLayout.CENTER);
    }

    @Override
    public void showForm(Form form) {
        System.err.println("Screen saver does not allow to show form");
    }
}
