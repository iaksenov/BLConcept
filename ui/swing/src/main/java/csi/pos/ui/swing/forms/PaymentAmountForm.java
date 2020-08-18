package csi.pos.ui.swing.forms;

import csi.pos.ui.swing.components.InputLabel;
import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;
import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.sale.PaymentAmountModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class PaymentAmountForm extends ValueForm<PaymentAmountModel, BigDecimal> implements ControlKeyListener, TypedKeyListener {

    private boolean defaultValue;

    @Override
    public Class<PaymentAmountModel> getModelClass() {
        return PaymentAmountModel.class;
    }

    private JLabel paymentName;
    //private JLabel countHint;
    private InputLabel input;
    private Consumer<InteractiveValueCancelledCallback<BigDecimal>> inputCallback;

    @Override
    public JPanel create() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        paymentName = new JLabel();
       // countHint = new JLabel();
        input = new InputLabel();
        panel.add(paymentName, BorderLayout.CENTER);
        JPanel downPanel = new JPanel(new BorderLayout());
        panel.add(downPanel, BorderLayout.SOUTH);
        //downPanel.add(countHint, BorderLayout.NORTH);
        downPanel.add(input, BorderLayout.CENTER);
        input.setText(" ");
        return panel;
    }


    @Override
    public Optional<BigDecimal> getCurrentValue() {
        return Optional.ofNullable(getValue());
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        if (ControlKeyType.ENTER == controlKey.getControlKeyType() && inputCallback != null) {
            inputCallback.accept(InteractiveValueCancelledCallback.entered(this.getValue()));
            input.setText(" ");
        } else if (ControlKeyType.ESC == controlKey.getControlKeyType() && input.getText().trim().length() == 0) {
            inputCallback.accept(InteractiveValueCancelledCallback.cancelled());
        } else {
            input.onControlKey(controlKey);
        }
    }

    private BigDecimal getValue() {
        try {
            return new BigDecimal(input.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public void onTypedKey(TypedKey key) {
        if (key.getCharacter() >= '0' && key.getCharacter() <= '9') {
            if (defaultValue) {
                input.setText(" ");
            }
            input.onTypedKey(key);
            defaultValue = false;
            inputCallback.accept(InteractiveValueCancelledCallback.changed(this.getValue()));
        } else if ((key.getCharacter() == '.' || key.getCharacter() == ',') && !input.getText().contains(".")) {
            if (defaultValue) {
                input.setText(" ");
            }
            input.onTypedKey(new TypedKey('.'));
            inputCallback.accept(InteractiveValueCancelledCallback.changed(this.getValue()));
        }
    }

    @Override
    public void onModelChanged(PaymentAmountModel model) {
        paymentName.setText(model.getPaymentName());
        input.setText(model.getValue().toString());
        defaultValue = true;
        inputCallback = model.getCallback();
    }

}
