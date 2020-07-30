package csi.pos.ui.swing.csi.pos.ui.swing.components;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class InputLabel extends JLabel {

    public InputLabel() {
        setBorder(new LineBorder(Color.DARK_GRAY, 1, false));
        setHorizontalAlignment(SwingConstants.RIGHT);
        setFont(new Font("Roboto", Font.PLAIN, 32));
    }

}
