package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.EnumMap;

@Component
public class MainForm extends JFrame {

    private JPanel layersPanel;
    private CardLayout cardLayout;
    private UILayer uiLayer;

    private final EnumMap<UILayer, LayerPanel> layers = new EnumMap<>(UILayer.class);

    private final FormsCatalog formsCatalog;

    public MainForm(@Autowired FormsCatalog formsCatalog) {
        this.formsCatalog = formsCatalog;
        SwingUtilities.invokeLater(this::init);
    }

    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setSize(640, 480);
        setResizable(false);
        cardLayout = new CardLayout();
        layersPanel = new JPanel(cardLayout);
        add(layersPanel, BorderLayout.CENTER);
        initLayers(layersPanel);
        setVisible(true);
        setLayer(UILayer.START);
    }

    private void initLayers(Container container) {
        int i = 100;
        for (UILayer layer : UILayer.values()) {
            LayerPanel layerPanel = new LayerPanel();
            JPanel panel1 = layerPanel.getPanel();
            panel1.setBackground(new Color(i, 0, 0));
            i+=20;
            container.add(panel1, layer.toString());
            layers.put(layer, layerPanel);
        }
    }

    public void setLayer(UILayer uiLayer) {
        if (this.uiLayer != uiLayer) {
            this.uiLayer = uiLayer;
            cardLayout.show(layersPanel, uiLayer.toString());
        }
    }

    public void showForm(UIFormModel uiFormModel) {
        Form<UIFormModel> form = formsCatalog.get(uiFormModel);
        if (form != null) {
            layers.get(uiLayer).showForm(form.createPanel());
            form.onModelChanged(uiFormModel);
            uiFormModel.setListener(form);
        } else {
            System.out.println("Form for model not implemented: " + uiFormModel.getClass());
        }
    }

}
