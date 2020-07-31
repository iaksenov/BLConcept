package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.ui.UIKeyListener;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.EnumMap;

/**
 * Основная UI форма и получатель клавиатурных событий.
 */
@Component
public class MainForm extends JFrame implements UIKeyListener {

    private JPanel layersPanel;
    private CardLayout cardLayout;
    private UILayer currentLayer;

    private final EnumMap<UILayer, LayerPanel> layers = new EnumMap<>(UILayer.class);

    private final FormsCatalog formsCatalog;

    public MainForm(@Autowired FormsCatalog formsCatalog) {
        this.formsCatalog = formsCatalog;
    }

    @PostConstruct
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setSize(640, 480);
        setResizable(true);
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
        if (this.currentLayer != uiLayer) {
            this.currentLayer = uiLayer;
            cardLayout.show(layersPanel, uiLayer.toString());
        }
    }

    public void showForm(UIFormModel uiFormModel) {
        Form<UIFormModel> form = formsCatalog.get(uiFormModel);
        if (form != null) {
            layers.get(currentLayer).showForm(form);
            form.onModelChanged(uiFormModel);
            uiFormModel.setListener(form);
        } else {
            System.out.println("Form for model not implemented: " + uiFormModel.getClass());
        }
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        layers.get(currentLayer).onControlKey(controlKey);
    }

    @Override
    public void onTypedKey(TypedKey key) {
        layers.get(currentLayer).onTypedKey(key);
    }
}
