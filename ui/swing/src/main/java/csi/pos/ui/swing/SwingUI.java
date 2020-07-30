package csi.pos.ui.swing;

import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.Locale;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;

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
    public void showForm(UIFormModel uiFormModel) {
        mainForm.showForm(uiFormModel);
    }

    @Override
    public void showForm(UILayer uiLayer, UIFormModel uiFormModel) {
        mainForm.setLayer(uiLayer);
        mainForm.showForm(uiFormModel);
    }

}
