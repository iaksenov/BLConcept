package csi.pos.ui.swing.sale;

import csi.pos.ui.swing.LayerPanel;
import csi.pos.ui.swing.forms.Form;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.sale.PlitkiFormModel;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SalePanel extends LayerPanel {

    public static final int ROWS = 5;
    public static final int COLS = 4;
    private JPanel mainPanel;
    private JPanel centerOnMainPanel;

    private JPanel rightOnMainPanel; // позиции чека
    private JPanel bottomFormsPanel; // для отображения форм BL
    private JPanel upLeftPanel; // для плиток
    private PlitkiFormModel plitkiModel; //модель для плиток

    private final List<Button> buttons = new ArrayList<>();
    private Consumer<String> plitkaConsumer;

    public SalePanel() {
        mainPanel = createPanel(Color.black, 0, 0);
        rightOnMainPanel = createPanel(Color.blue, 200, 0);
        centerOnMainPanel = createPanel(Color.gray, 200, 300);
        upLeftPanel = createPanel(Color.lightGray, 200, 100);
        plitki(upLeftPanel);
        bottomFormsPanel = createPanel(Color.green, 200, 100);
        mainPanel.add(rightOnMainPanel, BorderLayout.EAST);
        mainPanel.add(centerOnMainPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(upLeftPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(bottomFormsPanel, BorderLayout.SOUTH);
    }

    private void plitki(JPanel pnl) {
        pnl.setLayout(new GridLayout(ROWS, COLS, 10, 10));
        int i = 0;
        for (int r = 0; r < ROWS ; r++) {
            for (int c = 0; c < COLS; c++) {
                final int num = i;
                Button btn = new Button(""+ ++i);
                btn.setFocusable(false);
                btn.addActionListener(e -> plitkaClick(num));
                pnl.add(btn);
                buttons.add(btn);
            }
        }
    }

    private void plitkaClick(int index) {
        if (plitkaConsumer != null) {
            plitkaConsumer.accept(buttons.get(index).getLabel());
        }
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

    @Override
    public boolean setModel(UIFormModel uiFormModel) {
        if (uiFormModel instanceof PlitkiFormModel) {
            this.plitkiModel = (PlitkiFormModel)uiFormModel;
            plitkiModel.setListener(this::onPlitkiChanged);
            return true;
        }
        return super.setModel(uiFormModel);
    }

    public void onPlitkiChanged(PlitkiFormModel model) {
        for (int i= 0; i < buttons.size(); i++) {
            try {
                String text = model.getPlitki().get(i);
                buttons.get(i).setLabel(text);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        this.plitkaConsumer = model.getCallback();
    }

}
