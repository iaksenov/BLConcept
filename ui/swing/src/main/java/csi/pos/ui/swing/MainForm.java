package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import csi.pos.ui.swing.sale.SalePanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.ui.UIKeyListener;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.events.POSStatusEvent;
import ru.crystals.pos.ui.forms.UIFormModel;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Основная UI форма и получатель клавиатурных событий.
 */
@Component
public class MainForm extends JFrame implements UIKeyListener {

    private JPanel fullFramePanel;
    private CardLayout cardLayout;
    private UILayer currentLayer;

    private StatusPanel statusPanel;

    private final EnumMap<UILayer, LayerPanel> layers = new EnumMap<>(UILayer.class);

    private final FormsCatalog formsCatalog;

    private final Map<UILayer, LayerPanel> layerPanel;

    public MainForm(@Autowired FormsCatalog formsCatalog) {
        this.formsCatalog = formsCatalog;
        layerPanel = new HashMap<>();
        layerPanel.put(UILayer.SALE, new SalePanel());
    }

    @PostConstruct
    private void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setSize(640, 480);
        setResizable(true);
        cardLayout = new CardLayout();
        fullFramePanel = new JPanel(cardLayout);
        statusPanel = new StatusPanel();
        add(fullFramePanel, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setVisible(false);
        initLayers(fullFramePanel);
        setVisible(true);
        setLayer(UILayer.START);
    }

    private void initLayers(Container container) {
        for (UILayer layer : UILayer.values()) {
            LayerPanel layerPanel = this.layerPanel.getOrDefault(layer, new LayerPanel());
            JPanel panel1 = layerPanel.getPanel();
            container.add(panel1, layer.toString());
            layers.put(layer, layerPanel);
        }
    }

    public void setLayer(UILayer layer) {
        if (this.currentLayer != layer) {
            this.currentLayer = layer;
            cardLayout.show(fullFramePanel, layer.toString());
            statusPanel.setVisible(layer != UILayer.START);
        }
    }

    public UILayer getCurrentLayer() {
        return this.currentLayer;
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

    public void setLayerModels(UILayer uiLayer, UIFormModel... models) {
        if (models == null) {
            return;
        }
        LayerPanel layerPanel = layers.get(uiLayer);
        if (layerPanel != null) {
            for (UIFormModel model : models) {
                layerPanel.setModel(model);
            }
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

    @Async
    @EventListener
    private void onPOSStatusEvent(POSStatusEvent event) {
        statusPanel.setData(event);
    }

}
