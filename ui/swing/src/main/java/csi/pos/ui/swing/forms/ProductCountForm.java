package csi.pos.ui.swing.forms;

import csi.pos.ui.swing.components.InputLabel;
import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;
import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.sale.ProductCountModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class ProductCountForm extends ValueForm<ProductCountModel, Integer> implements ControlKeyListener, TypedKeyListener {

    private JLabel productName;
    private JLabel countHint;
    private InputLabel input;
    private Consumer<InteractiveValueCancelledCallback<Integer>> inputCallback;

    @Override
    public JPanel create() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        productName = new JLabel();
        countHint = new JLabel();
        input = new InputLabel();
        panel.add(productName, BorderLayout.CENTER);
        JPanel downPanel = new JPanel(new BorderLayout());
        panel.add(downPanel, BorderLayout.SOUTH);
        downPanel.add(countHint, BorderLayout.NORTH);
        downPanel.add(input, BorderLayout.CENTER);
        input.setText(" ");
        return panel;
    }


    @Override
    public Class<ProductCountModel> getModelClass() {
        return ProductCountModel.class;
    }

    @Override
    public Optional<Integer> getCurrentValue() {
        return Optional.ofNullable(getValue());
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        if (ControlKeyType.ENTER == controlKey.getControlKeyType() && inputCallback != null) {
            inputCallback.accept(InteractiveValueCancelledCallback.entered(this.getValue()));
            input.setText("");
        } else if (ControlKeyType.ESC == controlKey.getControlKeyType() && input.getText().trim().length() == 0) {
            inputCallback.accept(InteractiveValueCancelledCallback.cancelled());
        } else {
            input.onControlKey(controlKey);
        }
    }

    private Integer getValue() {
        try {
            return Integer.valueOf(input.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void onTypedKey(TypedKey key) {
        if (key.getCharacter() >= '0' && key.getCharacter() <= '9') {
            input.onTypedKey(key);
            inputCallback.accept(InteractiveValueCancelledCallback.changed(this.getValue()));
        }
    }

    @Override
    public void onModelChanged(ProductCountModel model) {
        productName.setText(model.getProductName());
        countHint.setText(model.getCountHint());
        inputCallback = model.getCallback();
    }

}
