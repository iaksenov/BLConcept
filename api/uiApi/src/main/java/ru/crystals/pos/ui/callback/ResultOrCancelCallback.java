package ru.crystals.pos.ui.callback;

public interface ResultOrCancelCallback<R> {

    void onEntered(R result);

    void onCancel();

}
