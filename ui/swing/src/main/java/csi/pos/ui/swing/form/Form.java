package csi.pos.ui.swing.form;

import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.UIModelListener;

import javax.swing.JPanel;

public abstract class Form<T extends UIFormModel> implements UIModelListener<T> {

    public abstract JPanel getPanel(T model);

    public abstract Class<T> getModelClass();

}
