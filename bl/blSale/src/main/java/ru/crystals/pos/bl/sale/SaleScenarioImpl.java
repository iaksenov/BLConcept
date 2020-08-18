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
import ru.crystals.pos.bl.api.sale.SaleScenarioAdditional;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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

    private Collection<SaleScenarioAdditional> additional = new HashSet<>();

    public SaleScenarioImpl(UI ui, ScenarioManager scenarioManager, LayersManager layersManager) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
        this.layersManager = layersManager;
    }

    @Autowired(required = false)
    private void setAdditional(List<SaleScenarioAdditional> additional) {
        this.additional = additional;
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
        List<UIFormModel> addModels = new ArrayList<>();
        for (SaleScenarioAdditional additional : additional) {
            addModels.addAll(additional.getAdditionalModels());
        }
        ui.setLayerModels(UILayer.SALE, addModels);
        ///
        addItems();
    }


    @Override
    public void onResume() {

    }

}
