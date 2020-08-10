package csi.pos.ui.swing.forms;

import org.springframework.stereotype.Component;
import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;

@Component
public class MessageForm extends Form<MessageFormModel> implements ControlKeyListener {

    private JLabel messageLabel;
    private Runnable callback;

    @Override
    public JComponent create() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        messageLabel = new JLabel();
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVerticalAlignment(SwingConstants.CENTER);
        messageLabel.setFont(new Font("Roboto", Font.PLAIN, 24));
        panel.add(messageLabel, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public Class<MessageFormModel> getModelClass() {
        return MessageFormModel.class;
    }

    @Override
    public void onModelChanged(MessageFormModel model) {
        messageLabel.setText(model.getMessage());
        callback = model.getCallback();
    }


    @Override
    public void onControlKey(ControlKey controlKey) {
        if (ControlKeyType.ENTER == controlKey.getControlKeyType() || ControlKeyType.ESC == controlKey.getControlKeyType()) {
            callback.run();
        }
    }

}
