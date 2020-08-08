package csi.pos.ui.swing.components;

import ru.crystals.pos.hw.events.keys.ControlKey;
import ru.crystals.pos.hw.events.keys.ControlKeyType;
import ru.crystals.pos.hw.events.keys.TypedKey;
import ru.crystals.pos.hw.events.listeners.ControlKeyListener;
import ru.crystals.pos.hw.events.listeners.TypedKeyListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

/**
 * Для отображения ввода
 */
public class InputLabel extends JLabel implements ControlKeyListener, TypedKeyListener {

    public InputLabel() {
        setBorder(new LineBorder(Color.DARK_GRAY, 1, false));
        setHorizontalAlignment(SwingConstants.RIGHT);
        setFont(new Font("Roboto", Font.PLAIN, 32));
    }

    @Override
    public void onControlKey(ControlKey controlKey) {
        if (ControlKeyType.ESC == controlKey.getControlKeyType()) {
            setText(" ");
        } else if (ControlKeyType.BACKSPACE == controlKey.getControlKeyType() && getText().length() > 0) {
            String text = getTextOverride();
            String substring = text.substring(0, text.length() - 1);
            if (substring.length() == 0) {
                substring = " ";
            }
            setText(substring);
        }
    }

    protected String getTextOverride() {
        return super.getText();
    }

    @Override
    public void onTypedKey(TypedKey key) {
        Character character = key.getCharacter();
        setText(getTextOverride().concat(String.valueOf(character)));
    }
}
