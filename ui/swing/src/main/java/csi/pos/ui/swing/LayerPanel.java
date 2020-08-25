package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;
import ru.crystals.pos.ui.forms.UIFormModel;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * Панель по умолчанию для UI слоя.
 */
public class LayerPanel implements TypedKeyListener, ControlKeyListener {

    protected JPanel jPanel;

    protected Form<?> currentForm;

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

    /**
     * Установка модели для слоя.
     * @param uiFormModel модель
     * @return true - установлено, false - нет (слой эту модель не поддерживает)
     */
    public boolean setModel(UIFormModel uiFormModel) {
        return false;
    }
}
