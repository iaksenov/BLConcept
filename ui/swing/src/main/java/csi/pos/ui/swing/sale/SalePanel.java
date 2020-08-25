package csi.pos.ui.swing.sale;

import csi.pos.ui.swing.LayerPanel;
import csi.pos.ui.swing.forms.Form;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.sale.PlitkiFormModel;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFormCallback;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFrameModel;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseStages;
import ru.crystals.pos.ui.forms.sale.purchase.UIPayment;
import ru.crystals.pos.ui.forms.sale.purchase.UIPosition;
import ru.crystals.pos.ui.forms.sale.purchase.UIPurchase;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SalePanel extends LayerPanel {

    public static final int ROWS = 5;
    public static final int COLS = 4;
    private final JPanel mainPanel;

    private final JPanel bottomFormsPanel; // для отображения форм BL

    private final List<Button> buttons = new ArrayList<>();
    private Consumer<String> plitkaConsumer;

    private DefaultListModel<String> positionsListModel;
    private JList<String> positionsList; // компонент список позиций
    private PurchaseFrameModel purchaseFrameModel; // модель списка опзиций
    private Consumer<PurchaseFormCallback> purchaseFrameModelCallback;
    private JLabel stageLabel;
    private Button calcButton;

    public SalePanel() {
        mainPanel = createPanel(Color.black, 0, 0);
        // позиции чека
        JPanel rightOnMainPanel = createPanel(Color.white, 200, 0);
        createPurchaseComponents(rightOnMainPanel);
        JPanel centerOnMainPanel = createPanel(Color.gray, 200, 300);
        // для плиток
        JPanel upLeftPanel = createPanel(Color.lightGray, 200, 100);
        createPlitkiComponents(upLeftPanel);
        bottomFormsPanel = createPanel(Color.green, 200, 100);
        mainPanel.add(rightOnMainPanel, BorderLayout.EAST);
        mainPanel.add(centerOnMainPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(upLeftPanel, BorderLayout.CENTER);
        centerOnMainPanel.add(bottomFormsPanel, BorderLayout.SOUTH);
    }

    private void createPurchaseComponents(JPanel parent) {
        stageLabel = new JLabel();
        stageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        parent.add(stageLabel, BorderLayout.NORTH);
        positionsListModel = new DefaultListModel<>();
        positionsList = new JList<>(positionsListModel);
        positionsList.setLayoutOrientation(JList.VERTICAL);
        positionsList.setFocusable(false);
        JScrollPane listScroller = new JScrollPane(positionsList);
        parent.add(positionsList, BorderLayout.CENTER);
        calcButton = new Button("Расчет");
        calcButton.setFont(new Font("Arial", Font.PLAIN, 20));
        calcButton.setFocusable(false);
        calcButton.setMinimumSize(new Dimension(0, 50));
        calcButton.addActionListener(this::onCalculateBtn);
        parent.add(calcButton, BorderLayout.SOUTH);
    }

    private void onCalculateBtn(ActionEvent actionEvent) {
        if (purchaseFrameModelCallback != null) {
            PurchaseFormCallback callback = PurchaseFormCallback.subtotal();
            purchaseFrameModelCallback.accept(callback);
        }
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
            //модель для плиток
            PlitkiFormModel plitkiModel = (PlitkiFormModel) uiFormModel;
            plitkiModel.setListener(this::onPlitkiChanged);
            return true;
        } else if (uiFormModel instanceof PurchaseFrameModel) {
            this.purchaseFrameModel = (PurchaseFrameModel)uiFormModel;
            this.purchaseFrameModel.setListener(this::onPurchaseChanged);
            return true;
        }
        return super.setModel(uiFormModel);
    }

    private void onPurchaseChanged(PurchaseFrameModel purchaseModel) {
        SwingUtilities.invokeLater(() -> {
            positionsListModel.removeAllElements();
            UIPurchase purchase = purchaseModel.getPurchase();
            for (UIPosition position : purchase.getPositions()) {
                positionsListModel.addElement(position.getName() + "[" + position.getCount() + "]");
            }
            if (purchase.getDiscountAmount() != null) {
                positionsListModel.addElement("----");
                positionsListModel.addElement("Скидка " + purchase.getDiscountAmount());
            }
            if (!purchase.getPayments().isEmpty()) {
                positionsListModel.addElement("----");
            }
            for (UIPayment payment : purchase.getPayments()) {
                positionsListModel.addElement(payment.getTypeName() + "[" + payment.getAmount() + "]");
            }
            stageLabel.setText(purchaseModel.getPurchaseStage() == null ? "" : purchaseModel.getPurchaseStage().toString());
            calcButton.setEnabled(PurchaseStages.ADD == purchaseModel.getPurchaseStage());
            purchaseFrameModelCallback = purchaseModel.getCallback();
        });
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
