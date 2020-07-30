package csi.pos.ui.swing.forms;

import csi.pos.ui.swing.csi.pos.ui.swing.components.InputLabel;
import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.forms.loading.LoginFormModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;

@Component
public class LoginForm extends Form<LoginFormModel> {

    private JLabel title;
    private JLabel info;
    private JLabel error;
    private InputLabel input;

    @Override
    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        title = new JLabel();
        info = new JLabel();
        error = new JLabel();
        input = new InputLabel();
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
    }

}
