package csi.pos.ui.swing.forms;

import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.UIModelListener;

import javax.swing.JComponent;

public abstract class Form<T extends UIFormModel> implements UIModelListener<T> {

    public abstract JComponent create();

    public abstract Class<T> getModelClass();

}
