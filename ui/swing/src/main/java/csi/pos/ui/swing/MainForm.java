package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import csi.pos.ui.swing.forms.ValueForm;
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
import ru.crystals.pos.ui.forms.UIValueFormModel;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Основная UI форма и получатель клавиатурных событий.
 */
@Component
public class MainForm extends JFrame implements UIKeyListener {

    private JLayeredPane layeredPane;
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
        layeredPane = new JLayeredPane();
        initResizeToChildListeners();
        statusPanel = new StatusPanel();
        add(layeredPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setVisible(false);
        initLayers(layeredPane);
        setLayer(UILayer.START);
        setVisible(true);
    }

    private void initResizeToChildListeners() {
        layeredPane.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                resizeChildren(e.getComponent());
            }

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeChildren(e.getComponent());
            }

            private void resizeChildren(java.awt.Component component) {
                for (java.awt.Component child : layeredPane.getComponents()) {
                    child.setBounds(component.getBounds());
                }
            }
        });
    }

    private void initLayers(JLayeredPane layeredPane) {
        int index = 0;
        for (UILayer layer : UILayer.values()) {
            LayerPanel layerPanel = this.layerPanel.getOrDefault(layer, new LayerPanel());
            layeredPane.add(layerPanel.getPanel(), new Integer(index++));
            layers.put(layer, layerPanel);
        }
    }

    public void setLayer(UILayer layer) {
        if (this.currentLayer != layer) {
            this.currentLayer = layer;
            JPanel pnl = layers.get(layer).getPanel();
            layeredPane.setLayer(pnl, 1000, 0);
            pnl.setVisible(true);
            statusPanel.setVisible(layer != UILayer.START);
        }
    }

    public void showForm(UIFormModel uiFormModel) {
        SwingUtilities.invokeLater(() -> {
            Form<UIFormModel> form = formsCatalog.get(uiFormModel);
            if (form != null) {
                layers.get(currentLayer).showForm(form);
                form.onModelChanged(uiFormModel);
                uiFormModel.setListener(form);
            } else {
                System.err.println("Form for model not implemented: " + uiFormModel.getClass());
            }
        });
    }

    public void setLayerModels(UILayer uiLayer, Collection<UIFormModel> models) {
        if (models == null) {
            return;
        }
        SwingUtilities.invokeLater(() -> {
            LayerPanel layerPanel = layers.get(uiLayer);
            if (layerPanel != null) {
                for (UIFormModel model : models) {
                    if (layerPanel.setModel(model)) {
                        model.modelChanged();
                    }
                }
            }
        });
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

    public <V> Optional<V> getFormValue(UIValueFormModel<V> model) {
        Form<UIFormModel> form = formsCatalog.get(model);
        if (form instanceof ValueForm) {
            return ((ValueForm)form).getCurrentValue();
        } else {
            return Optional.empty();
        }
    }
}
