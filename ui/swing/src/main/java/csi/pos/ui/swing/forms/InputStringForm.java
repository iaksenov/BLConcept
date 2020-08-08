package csi.pos.ui.swing.forms;

import csi.pos.ui.swing.components.InputLabel;
import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;
import ru.crystals.pos.ui.forms.input.InputStringFormModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.function.Consumer;

@Component
public class InputStringForm extends Form<InputStringFormModel>  implements ControlKeyListener, TypedKeyListener {

    private JLabel title;
    private JLabel hint;
    private InputLabel input;
    private Consumer<String> inputCallback;

    @Override
    public JPanel create() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        title = new JLabel();
        hint = new JLabel();
        input = new InputLabel();
        panel.add(title, BorderLayout.CENTER);
        JPanel downPanel = new JPanel(new BorderLayout());
        panel.add(downPanel, BorderLayout.SOUTH);
        downPanel.add(hint, BorderLayout.NORTH);
        downPanel.add(input, BorderLayout.CENTER);
        input.setText(" ");
        return panel;
    }

    @Override
    public Class<InputStringFormModel> getModelClass() {
        return InputStringFormModel.class;
    }

    @Override
    public void onModelChanged(InputStringFormModel model) {
        title.setText(model.getTitle());
        hint.setText(model.getHint());
        inputCallback = model.getCallback();
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        if (ControlKeyType.ENTER == controlKey.getControlKeyType() && inputCallback != null) {
            inputCallback.accept(input.getText().trim());
            input.setText("");
        } else {
            input.onControlKey(controlKey);
        }
    }

    @Override
    public void onTypedKey(TypedKey key) {
        input.onTypedKey(key);
    }

}
