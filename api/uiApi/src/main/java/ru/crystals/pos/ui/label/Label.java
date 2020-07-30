package ru.crystals.pos.ui.label;

public class Label {

    private LabelIcon labelIcon;

    private String text;

    public Label(LabelIcon labelIcon, String text) {
        this.labelIcon = labelIcon;
        this.text = text;
    }

    public static Label empty(String text) {
        return new Label(LabelIcon.EMPTY, text);
    }

    public static Label info(String text) {
        return new Label(LabelIcon.INFO, text);
    }

    public static Label warning(String text) {
        return new Label(LabelIcon.WARNING, text);
    }

    public static Label error(String text) {
        return new Label(LabelIcon.ERROR, text);
    }

    public LabelIcon getLabelIcon() {
        return labelIcon;
    }

    public String getText() {
        return text;
    }
}
