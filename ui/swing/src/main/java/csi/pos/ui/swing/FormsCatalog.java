package csi.pos.ui.swing;

import csi.pos.ui.swing.form.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.forms.UIFormModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FormsCatalog {

    private final Map<Class<? extends UIFormModel>, Form> forms = new HashMap<>();

    public FormsCatalog(@Autowired List<Form<?>> forms) {
        forms.forEach(this::put);
    }

    public <T extends UIFormModel> Form<T> get(Class<T> modelClass) {
        return forms.get(modelClass);
    }

    private <T extends UIFormModel> void put(Form<T> form) {
        forms.put(form.getModelClass(), form);
    }

}
