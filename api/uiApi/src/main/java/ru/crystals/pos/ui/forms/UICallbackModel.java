package ru.crystals.pos.ui.forms;

import java.util.function.Consumer;

public interface UICallbackModel<C> {

    Consumer<C> getCallback();

}
