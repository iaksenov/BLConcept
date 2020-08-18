package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.Scenario;
import ru.crystals.pos.bl.api.goods.GoodsPluginScenario;
import ru.crystals.pos.bl.api.goods.Product;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.sale.SaleAddItemsScenario;
import ru.crystals.pos.bl.api.spinner.CallableSpinnerArg;
import ru.crystals.pos.bl.common.CallableSpinner;
import ru.crystals.pos.docs.DocModule;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.hw.events.listeners.BarcodeListener;
import ru.crystals.pos.hw.events.listeners.MSRTracks;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.input.InputStringFormModel;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import java.util.concurrent.Callable;

@Component
public class AddItemsScenario implements SaleAddItemsScenario, BarcodeListener {

    private final UI ui;
    private final ScenarioManager scenarioManager;
    private final DocModule docModule;
    private VoidListener onComplete;

    @Autowired
    private GoodsPluginScenario goodsPluginScenario;

    public AddItemsScenario(UI ui, ScenarioManager scenarioManager, DocModule docModule) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
        this.docModule = docModule;
    }

    @Override
    public void start(VoidListener onComplete) {
        this.onComplete = onComplete;
        showSearchForm();
    }

    @Override
    public void onBarcode(String code) {
        Scenario childScenario = scenarioManager.getChildScenario(this);
        if (childScenario instanceof GoodsPluginScenario) {
            if (((GoodsPluginScenario) childScenario).tryToComplete(this::addPositionToDB)) {
                showProductPlugin(createFakeProduct(code));
            } else {
                System.out.println("Goods plugin cannot return position " + code);
            };
        } else {
            searchProduct(code);
        }
//        ui.showForm(new MessageFormModel("Товар по ШК не найден!", this::showSearchForm));
        // поиск товара
    }

    @Override
    public void onMSR(MSRTracks msrTracks) {
        // поиск карты/купона...
    }

    private void showSearchForm() {
        ui.showForm(new InputStringFormModel("Поиск товара", "введите код товара", this::onItemEntered));
    }


    private void onItemEntered(String s) {
        // поиск товара в БД
        searchProduct(s);
    }

    @Override
    public void searchProduct(String searchString) {
        CallableSpinnerArg<Product> arg = new CallableSpinnerArg<>("Поиск товара ...", findProductCall(searchString));
        scenarioManager.startChildAsync(new CallableSpinner<>(ui), arg, this::showProductPlugin, e -> {
            ui.showForm(new MessageFormModel("Товар по строке не найден!", this::showSearchForm));
        });
    }

    private Callable<Product> findProductCall(String searchString) {
        return () -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (searchString == null || searchString.length() == 0) {
                throw new Exception("Товар не найден");
            }
            return createFakeProduct(searchString);
        };
    }

    private Product createFakeProduct(String searchString) {
        return new Product("Товар " + searchString);
    }

    private void showProductPlugin(Product product) {
        scenarioManager.startChild(goodsPluginScenario, product, this::onGoodsPluginFinish, () -> onGoodsPluginCancel(product.getProductName()));
    }

    private void onGoodsPluginCancel(String s) {
        System.out.println("Plugin canceled");
        ui.showForm(new MessageFormModel(s + " не был добавлен", this::showSearchForm));
    }

    private void onGoodsPluginFinish(Position position) {
        addPositionToDB(position);
        showSearchForm();
    }

    private void addPositionToDB(Position position) {
        docModule.addPosition(position);
    }

    @Override
    public void doFinish() {
        // if it possible to finish
        onComplete.call();
    }

}
