package csi.pos.ui.swing.sale;

import csi.pos.ui.swing.LayerPanel;
import csi.pos.ui.swing.forms.Form;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class SalePanel extends LayerPanel {

    private JPanel mainPanel;
    private JPanel centerOnMainPanel;

    private JPanel rightOnMainPanel; // позиции чека
    private JPanel bottomFormsPanel; // для отображения форм BL
    private JPanel upLeftPanel; // для плиток

    public SalePanel() {
        mainPanel = createPanel(Color.black, 0, 0);
        rightOnMainPanel = createPanel(Color.blue, 200, 0);
        centerOnMainPanel = createPanel(Color.gray, 200, 300);
        upLeftPanel = createPanel(Color.yellow, 200, 100);
        bottomFormsPanel = createPanel(Color.green, 200, 100);
        mainPanel.add(rightOnMainPanel, BorderLayout.EAST);
        mainPanel.add(centerOnMainPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(upLeftPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(bottomFormsPanel, BorderLayout.SOUTH);
    }

    private JPanel createPanel(Color color, int minx, int miny) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setOpaque(true);
        panel.setBackground(color);
        panel.setPreferredSize(new Dimension(minx, miny));
        return panel;
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public void showForm(Form form) {
        bottomFormsPanel.removeAll();
        this.currentForm = form;
        bottomFormsPanel.add(form.create(), BorderLayout.CENTER);
        bottomFormsPanel.invalidate();
    }

}
