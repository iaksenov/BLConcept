package csi.pos.ui.swing.forms;

import org.springframework.stereotype.Component;
import ru.crystals.pos.ui.forms.spinner.SpinnerModel;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;

@Component
public class SpinnerForm extends Form<SpinnerModel> {

    private JLabel label;

    @Override
    public JComponent create() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        label = new JLabel();
        panel.add(label, BorderLayout.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Roboto", Font.PLAIN, 32));
        return panel;
    }

    @Override
    public Class<SpinnerModel> getModelClass() {
        return SpinnerModel.class;
    }

    @Override
    public void onModelChanged(SpinnerModel model) {
        label.setText("<> " + model.getMessage() + " <>");
    }

}
