package csi.pos.ui.swing.popup;

import csi.pos.ui.swing.LayerPanel;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import java.awt.BorderLayout;
import java.awt.Color;

public class PopupLayerPanel extends LayerPanel {

    public PopupLayerPanel() {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setName("popup");
        jPanel.setOpaque(true);
        jPanel.setBackground(new Color(250, 250, 250, 200));
        CompoundBorder compoundBorder = new CompoundBorder(BorderFactory.createEmptyBorder(90, 90, 90, 90),
            BorderFactory.createLineBorder(Color.black));
        jPanel.setBorder(compoundBorder);
    }

}
