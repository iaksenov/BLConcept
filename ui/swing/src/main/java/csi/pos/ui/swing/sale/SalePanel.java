package csi.pos.ui.swing.sale;

import csi.pos.ui.swing.LayerPanel;
import csi.pos.ui.swing.forms.Form;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.sale.PlitkiFormModel;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFrameModel;
import ru.crystals.pos.ui.forms.sale.purchase.UIPosition;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

    private DefaultListModel<UIPosition> positionsListModel;
    private PurchaseFrameModel purchaseFrameModel;
    private JList<UIPosition> positionsList;

    public SalePanel() {
        mainPanel = createPanel(Color.black, 0, 0);
        rightOnMainPanel = createPanel(Color.blue, 200, 0);
        createPurchaseComponents(rightOnMainPanel);
        centerOnMainPanel = createPanel(Color.gray, 200, 300);
        upLeftPanel = createPanel(Color.lightGray, 200, 100);
        createPlitkiComponents(upLeftPanel);
        bottomFormsPanel = createPanel(Color.green, 200, 100);
        mainPanel.add(rightOnMainPanel, BorderLayout.EAST);
        mainPanel.add(centerOnMainPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(upLeftPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(bottomFormsPanel, BorderLayout.SOUTH);
    }

    private void createPurchaseComponents(JPanel parent) {
        positionsListModel = new DefaultListModel<>();
        positionsList = new JList<>(positionsListModel);
        positionsList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(positionsList);
        parent.add(positionsList, BorderLayout.CENTER);
    }

    private void createPlitkiComponents(JPanel pnl) {
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
            this.plitkiModel.setListener(this::onPlitkiChanged);
            return true;
        } else if (uiFormModel instanceof PurchaseFrameModel) {
            this.purchaseFrameModel = (PurchaseFrameModel)uiFormModel;
            this.purchaseFrameModel.setListener(this::onPurchaseChanged);
        }
        return super.setModel(uiFormModel);
    }

    private void onPurchaseChanged(PurchaseFrameModel purchaseFrameModel) {
        positionsListModel.removeAllElements();
        for (UIPosition position : purchaseFrameModel.getPurchase().getPositions()) {
            positionsListModel.addElement(position);
        }
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
