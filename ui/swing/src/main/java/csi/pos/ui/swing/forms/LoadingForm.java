package csi.pos.ui.swing.forms;

import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.forms.loading.LoadingFormModel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

@Component
public class LoadingForm extends Form<LoadingFormModel> {

    private JLabel versionLabel;
    private JLabel captionLabel;

    @Override
    public JPanel create() {
        JPanel panel = new JPanel();
        versionLabel = new JLabel();
        versionLabel.setBackground(Color.GRAY);
        versionLabel.setForeground(Color.WHITE);
        versionLabel.setOpaque(true);
        captionLabel = new JLabel();
        captionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        captionLabel.setFont(new Font("Roboto", Font.PLAIN, 32));
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(""), BorderLayout.NORTH);
        panel.add(captionLabel, BorderLayout.CENTER);
        panel.add(versionLabel, BorderLayout.SOUTH);
        return panel;
    }

    @Override
    public Class<LoadingFormModel> getModelClass() {
        return LoadingFormModel.class;
    }

    @Override
    public void onModelChanged(LoadingFormModel model) {
        versionLabel.setText(model.getVersion());
        captionLabel.setText(model.getCaption());
    }

}
