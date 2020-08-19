package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.LayersManager;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.sale.AddPaymentsScenario;
import ru.crystals.pos.bl.api.sale.AddPositionsScenario;
import ru.crystals.pos.bl.api.sale.CalcDiscountScenario;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseResult;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseScenario;
import ru.crystals.pos.bl.api.sale.SaleScenario;
import ru.crystals.pos.bl.api.sale.SaleScenarioAdditional;
import ru.crystals.pos.bl.api.scenarios.special.ForceCompleteImpossibleException;
import ru.crystals.pos.docs.DocModule;
import ru.crystals.pos.hw.events.keys.FuncKey;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

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
    private final DocModule docModule;

    // stage 1
    private final AddPositionsScenario addPositionsScenario;
    // stage 2
    private final CalcDiscountScenario calcDiscount;
    // stage 3
    private final AddPaymentsScenario addPayments;
    // stage 4
    private final RegisterPurchaseScenario registerPurchase;

    private Collection<SaleScenarioAdditional> additional = new HashSet<>();

    public SaleScenarioImpl(UI ui, ScenarioManager scenarioManager, LayersManager layersManager, DocModule docModule, AddPositionsScenario addPositionsScenario,
                            CalcDiscountScenario calcDiscount, AddPaymentsScenario addPayments, RegisterPurchaseScenario registerPurchase) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
        this.layersManager = layersManager;
        this.docModule = docModule;
        this.addPositionsScenario = addPositionsScenario;
        this.calcDiscount = calcDiscount;
        this.addPayments = addPayments;
        this.registerPurchase = registerPurchase;
    }

    @Autowired(required = false)
    private void setAdditional(List<SaleScenarioAdditional> additional) {
        this.additional = additional;
    }

    /**
     * Добавление позиций
     */
    private void addItems() {
        docModule.setDiscountAmount(null);
        scenarioManager.startChild(addPositionsScenario, this::calcDiscount);
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
        scenarioManager.startChild(addPayments, "CASH", this::registerPurchase, this::addItems);
    }

    /**
     * Регистрация чека
     */
    private void registerPurchase() {
        try {
            scenarioManager.startChild(registerPurchase, docModule.getCurrentPurchase(), this::purchaseRegistered);
        } catch (Exception e) {
            ui.showForm(new MessageFormModel("Всё. Приехали. Сценарий печати сломался.", this::onRegisterFail));
        }
    }

    private void onRegisterFail() {
        System.exit(0);
    }

    private void purchaseRegistered(RegisterPurchaseResult registerPurchaseResult) {
        docModule.purchaseRegistered(registerPurchaseResult.getPurchase());
        addItems();
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
        if (scenarioManager.getChildScenario(this) == addPositionsScenario) {
            try {
                scenarioManager.tryToComplete(addPositionsScenario, this::calcDiscount);
            } catch (ForceCompleteImpossibleException e) {
                System.out.println("Can't force complete AddItemsScenario " + e.getLocalizedMessage());
            }
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
    public void onSubtotal() {
        doSubtotal();
    }

}
