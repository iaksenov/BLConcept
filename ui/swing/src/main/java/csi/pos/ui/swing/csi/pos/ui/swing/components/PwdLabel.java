package csi.pos.ui.swing.csi.pos.ui.swing.components;

import java.util.Collections;

public class PwdLabel extends InputLabel {

    private String text;
    private String masked;

    @Override
    public void setText(String text) {
        this.text = text;
        masked =  String.join("", Collections.nCopies(text.trim().length(), "*"));
        if (masked.length() == 0) {
            masked = " ";
        }
        super.setText(masked);
    }

    @Override
    protected String getTextOverride() {
        return text;
    }

    public String getText() {
        return this.masked;
    }

    public String getPwd() {
        return text;
    }

}
