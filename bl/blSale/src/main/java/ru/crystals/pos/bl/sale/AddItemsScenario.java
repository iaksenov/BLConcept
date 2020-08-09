package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.VoidListener;
import ru.crystals.pos.bl.api.sale.SaleAddItemsScenario;
import ru.crystals.pos.bl.api.sale.goods.GoodsPluginScenario;
import ru.crystals.pos.bl.api.sale.goods.Position;
import ru.crystals.pos.bl.api.sale.goods.Product;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRTracks;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.input.InputStringFormModel;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import java.util.function.Consumer;

@Component
public class AddItemsScenario implements SaleAddItemsScenario, BarcodeListener {

    private final UI ui;
    private final ScenarioManager scenarioManager;
    private Consumer<Void> onComplete;
    private VoidListener onCancel;

    @Autowired
    private GoodsPluginScenario goodsPluginScenario;

    public AddItemsScenario(UI ui, ScenarioManager scenarioManager) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void onBarcode(String code) {
        System.out.println("AddItemsScenario received barcode " + code);
        ui.showForm(new MessageFormModel("Товар по ШК не найден!", this::showSearchForm));
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
        showSearchForm();
    }

    private void showSearchForm() {
        ui.showForm(new InputStringFormModel("Поиск товара", "введите код товара", this::onItemEntered));
    }


    private void onItemEntered(String s) {
        // поиск товара в БД
        System.out.println("search product by input string " + s);
        if (s == null || s.length() == 0) {
            ui.showForm(new MessageFormModel("Товар по строке не найден!", this::showSearchForm));
        } else {
            showProductPlugin(s);
        }
    }

    @Override
    public void showProductPlugin(String s) {
        String productName = "Товар " + s;
        Product product = new Product(productName);
        scenarioManager.startSubScenario(goodsPluginScenario, product, this::onPluginOk, () -> onPluginCancel(productName));
    }

    private void onPluginCancel(String s) {
        System.out.println("Plugin canceled");
        ui.showForm(new MessageFormModel(s + " не был добавлен", this::showSearchForm));
    }

    private void onPluginOk(Position position) {
        System.out.println(position);
        showSearchForm();
    }

    @Override
    public void doFinish() {

    }

}
