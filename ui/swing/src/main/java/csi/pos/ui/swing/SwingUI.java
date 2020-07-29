package csi.pos.ui.swing;

import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.listeners.key.ControlKey;
import ru.crystals.pos.hw.events.listeners.key.TypedKey;
import ru.crystals.pos.ui.Locale;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;

import javax.swing.SwingUtilities;

@Component
public class SwingUI implements UI {

    private MainForm mainForm;

    public SwingUI(MainForm mainForm) {
        this.mainForm = mainForm;
    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public void setLayer(UILayer uiLayer) {
        mainForm.setLayer(uiLayer);
    }

    @Override
    public <U extends UIFormModel> U showForm(U uiFormModel) {
        return null;
    }

    @Override
    public <U extends UIFormModel> U showForm(UILayer uiLayer, U uiFormModel) {
        SwingUtilities.invokeLater(() -> {
            mainForm.setLayer(uiLayer);
            mainForm.showForm(uiFormModel);
        });
        return null;
    }

    @Override
    public void dispatchControlKey(ControlKey controlKey) {

    }

    @Override
    public void dispatchTypedKey(TypedKey controlKey) {

    }
}
