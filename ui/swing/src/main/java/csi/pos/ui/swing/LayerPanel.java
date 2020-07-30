package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

public class LayerPanel implements TypedKeyListener, ControlKeyListener {

    private JPanel jPanel;

    private Form<?> currentForm;

    public LayerPanel() {
        jPanel = new JPanel(new BorderLayout());
        jPanel.setOpaque(true);
        jPanel.setBackground(Color.GRAY);
    }

    public JPanel getPanel() {
        return jPanel;
    }

    public void showForm(Form form) {
        jPanel.removeAll();
        this.currentForm = form;
        jPanel.add(form.create(), BorderLayout.CENTER);
        jPanel.invalidate();
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        if (currentForm != null && currentForm instanceof ControlKeyListener) {
            ((ControlKeyListener)currentForm).onControlKey(controlKey);
        }
    }

    @Override
    public void onTypedKey(TypedKey key) {
        if (currentForm != null && currentForm instanceof TypedKeyListener) {
            ((TypedKeyListener)currentForm).onTypedKey(key);
        }
    }
}
