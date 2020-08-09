package csi.pos.ui.swing.forms;

import csi.pos.ui.swing.components.PwdLabel;
import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;
import ru.crystals.pos.ui.forms.loading.LoginFormModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.util.function.Consumer;

/**
 * Форма логина
 */
@Component
public class LoginForm extends Form<LoginFormModel> implements ControlKeyListener, TypedKeyListener {

    private JLabel title;
    private JLabel info;
    private JLabel error;
    private PwdLabel input;
    private Consumer<String> passwordCallback;

    @Override
    public JPanel create() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        title = new JLabel();
        info = new JLabel();
        error = new JLabel();
        input = new PwdLabel();
        panel.add(title, BorderLayout.NORTH);
        panel.add(info, BorderLayout.CENTER);
        JPanel downPanel = new JPanel(new BorderLayout());
        panel.add(downPanel, BorderLayout.SOUTH);
        downPanel.add(error, BorderLayout.NORTH);
        downPanel.add(input, BorderLayout.CENTER);
        input.setText(" ");
        return panel;
    }

    @Override
    public Class<LoginFormModel> getModelClass() {
        return LoginFormModel.class;
    }

    @Override
    public void onModelChanged(LoginFormModel model) {
        title.setText(model.getTitle());
        info.setText(model.getInfoLabel() == null ? "" : model.getInfoLabel().getText());
        error.setText(model.getLoginFailedText() == null ? "" : model.getLoginFailedText().getText());
        passwordCallback = model.getCallback();
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        if (ControlKeyType.ENTER == controlKey.getControlKeyType() && passwordCallback != null) {
            passwordCallback.accept(input.getPwd().trim());
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
