package csi.pos.ui.swing.forms;

import ru.crystals.pos.ui.forms.UIValueFormModel;

import java.util.Optional;

public abstract class ValueForm<T extends UIValueFormModel<V>, V> extends Form<T> {

    public abstract Optional<V> getCurrentValue();

}
