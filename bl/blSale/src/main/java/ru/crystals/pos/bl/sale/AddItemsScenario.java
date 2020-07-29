package ru.crystals.pos.bl.sale;

import ru.crystals.pos.bl.api.VoidListener;
import ru.crystals.pos.bl.api.sale.SaleAddItemsScenario;
import ru.crystals.pos.hw.events.listeners.MSRTracks;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.input.InputStringFormModel;

import java.util.function.Consumer;

public class AddItemsScenario implements SaleAddItemsScenario {

    private final UI ui;

    public AddItemsScenario(UI ui) {
        this.ui = ui;
    }

    @Override
    public void onBarcode(String code) {
        // поиск товара
    }

    @Override
    public void onMSR(MSRTracks msrTracks) {
        // поиск карты/купона...
    }

    @Override
    public void start(Consumer<Void> onComplete, VoidListener onCancel) {
        ui.showForm(new InputStringFormModel("Поиск товара", "введите код товара", this::searchByInput));
    }

    private void searchByInput(String s) {
        // поиск товара в БД
    }

    @Override
    public void doFinish() {

    }

}
