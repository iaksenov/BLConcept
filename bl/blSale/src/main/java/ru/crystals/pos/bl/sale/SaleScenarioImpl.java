package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.LayersManager;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.sale.AddPaymentsScenario;
import ru.crystals.pos.bl.api.sale.CalcDiscountScenario;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseScenario;
import ru.crystals.pos.bl.api.sale.SaleAddItemsScenario;
import ru.crystals.pos.bl.api.sale.SaleScenario;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.sale.PlitkiFormModel;

import java.util.ArrayList;

/**
 * Класс сценария продажи.
 */
@Component
public class SaleScenarioImpl implements SaleScenario {

    private final UI ui;
    private final ScenarioManager scenarioManager;
    private final LayersManager layersManager;

    // stage 1
    @Autowired
    private SaleAddItemsScenario addItemsScenario;
    // stage 2
    //@Autowired
    private CalcDiscountScenario calcDiscount;
    // stage 3
    ///@Autowired
    private AddPaymentsScenario addPayments;
    // stage 4
    //@Autowired
    private RegisterPurchaseScenario registerPurchase;

    private final PlitkiFormModel plitkiModel;

    public SaleScenarioImpl(UI ui, ScenarioManager scenarioManager, LayersManager layersManager) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
        this.layersManager = layersManager;
        this.plitkiModel = new PlitkiFormModel(new ArrayList<>(), this::onPlitkaClick);
    }

    private void onPlitkaClick(String s) {
        System.out.println("Plitka clicked " + s);
        if (s.contains("EXIT")) {
            layersManager.setLayer(UILayer.LOGIN);
        } else {
            if (scenarioManager.getChildScenario(this) == addItemsScenario) {
                addItemsScenario.searchProduct(s);
            }
        }
    }

    /**
     * Добавление позиций
     */
    private void addItems() {
        scenarioManager.startChild(addItemsScenario, this::calcDiscount);
    }

    /**
     * Расчет скидок
     */
    private void calcDiscount() {
        scenarioManager.startChild(calcDiscount, this::addPayments, this::onCancelDiscount);
    }

    private void onCancelDiscount() {
        addItems();
    }

    /**
     * Добавление оплат
     */
    private void addPayments() {
        scenarioManager.startChild(addPayments, null, this::registerPurchase, this::calcDiscount);
    }

    /**
     * Регистрация чека
     */
    private void registerPurchase() {
        scenarioManager.startChild(registerPurchase, this::addItems);
    }


    @Override
    public void onFunctionalKey(FuncKey funcKey) {
        switch (funcKey.getFuncKeyType()) {
            case SUBTOTAL:
                doSubtotal();
            case PAYMENT:
                doPayment(funcKey.getPayload());
        }
    }

    private void doPayment(String paymentName) {
        doSubtotal();
    }

    private void doSubtotal() {
        if (scenarioManager.getChildScenario(this) == addItemsScenario) {
            addItemsScenario.doFinish();
        }
    }

    @Override
    public void start() {
        // Тут в зависимости от состояния чека запускается нужный сценарий
        ui.setLayerModels(UILayer.SALE, plitkiModel);
        plitkiModel.getPlitki().clear();
        plitkiModel.getPlitki().add("-= EXIT =- ");
        plitkiModel.getPlitki().add("Кефир");
        plitkiModel.getPlitki().add("Полбатона");
        plitkiModel.getPlitki().add("Вода");
        plitkiModel.getPlitki().add("Спички");
        plitkiModel.getPlitki().add("Туалет");
        plitkiModel.getPlitki().add("Капучино");
        plitkiModel.modelChanged();
        addItems();
    }


    @Override
    public void onResume() {

    }

}
