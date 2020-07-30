package csi.pos.ui.swing;

import csi.pos.ui.swing.forms.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.forms.UIFormModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FormsCatalog {

    private final Map<Class<? extends UIFormModel>, Form<UIFormModel>> forms = new HashMap<>();

    public FormsCatalog(@Autowired List<Form> forms) {
        forms.forEach(this::put);
    }

    public Form<UIFormModel> get(UIFormModel model) {
        return forms.get(model.getClass());
    }

    private void put(Form<UIFormModel> form) {
        forms.put(form.getModelClass(), form);
    }

}
