package ru.crystals.pos.bl.sale;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.VoidListener;
import ru.crystals.pos.bl.api.sale.SaleAddItemsScenario;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRTracks;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.input.InputStringFormModel;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import java.util.function.Consumer;

@Component
public class AddItemsScenario implements SaleAddItemsScenario, BarcodeListener {

    private final UI ui;
    private Consumer<Void> onComplete;
    private VoidListener onCancel;

    public AddItemsScenario(UI ui) {
        this.ui = ui;
    }

    @Override
    public void onBarcode(String code) {
        System.out.println("AddItemsScenario received barcode " + code);
        ui.showForm(new MessageFormModel("Товар по ШК не найден!", this::showSearch));
        // поиск товара
    }

    @Override
    public void onMSR(MSRTracks msrTracks) {
        // поиск карты/купона...
    }

    @Override
    public void start(Consumer<Void> onComplete, VoidListener onCancel) {
        this.onComplete = onComplete;
        this.onCancel = onCancel;
        showSearch();
    }

    private void showSearch() {
        ui.showForm(new InputStringFormModel("Поиск товара", "введите код товара", this::searchByInput));
    }


    private void searchByInput(String s) {
        // поиск товара в БД
        System.out.println("search product by input string " + s);
        ui.showForm(new MessageFormModel("Товар по строке не найден!", this::showSearch));
    }

    @Override
    public void doFinish() {

    }

}
