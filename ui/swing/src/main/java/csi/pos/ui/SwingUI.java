package csi.pos.ui;

import csi.pos.ui.swing.MainForm;
import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.UILayers;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.UIValueFormModel;

import java.util.Collection;
import java.util.Optional;

/**
 * Реализация UI на Swnig
 */
@Component
public class SwingUI implements UI, UILayers {

    private MainForm mainForm;

    public SwingUI(MainForm mainForm) {
        this.mainForm = mainForm;
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
    public void setLayerModels(UILayer uiLayer, Collection<UIFormModel> models) {
        mainForm.setLayerModels(uiLayer, models);
    }

    @Override
    public <V> Optional<V> getFormValue(UIValueFormModel<V> model) {
        return mainForm.getFormValue(model);
    }
}
