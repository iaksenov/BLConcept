package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.sale.AddPaymentsScenario;
import ru.crystals.pos.bl.api.sale.AddPositionsScenario;
import ru.crystals.pos.bl.api.sale.CalcDiscountScenario;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseResult;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseScenario;
import ru.crystals.pos.bl.api.sale.SaleScenario;
import ru.crystals.pos.bl.api.sale.SaleScenarioAdditional;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;
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
 * Реализация сценария продажи.
 */
@Component
public class SaleScenarioImpl implements SaleScenario {

    private UI ui;
    private final ScenarioManager scenarioManager;
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

    public SaleScenarioImpl(ScenarioManager scenarioManager, DocModule docModule, AddPositionsScenario addPositionsScenario,
                            CalcDiscountScenario calcDiscount, AddPaymentsScenario addPayments, RegisterPurchaseScenario registerPurchase) {
        this.scenarioManager = scenarioManager;
        this.docModule = docModule;
        this.addPositionsScenario = addPositionsScenario;
        this.calcDiscount = calcDiscount;
        this.addPayments = addPayments;
        this.registerPurchase = registerPurchase;
    }

    @Autowired(required = false)
    private void injectAdditional(List<SaleScenarioAdditional> additional) {
        this.additional = additional;
    }

    /**
     * Добавление позиций
     */
    private void addItems() {
        addItems(null);
    }

    private void addItems(String searchString) {
        docModule.setDiscountAmount(null);
        scenarioManager.startChild(addPositionsScenario, searchString);
    }


    /**
     * Расчет скидок
     * @param payType
     */
    private void calcDiscount(String payType) {
        scenarioManager.startChild(calcDiscount, () -> addPayments(payType), this::addItems);
    }

    /**
     * Добавление оплат
     * @param payType
     */
    private void addPayments(String payType) {
        scenarioManager.startChild(addPayments, payType, this::registerPurchase, this::addItems);
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
                break;
            case PAYMENT:
                doPayment(funcKey.getPayload());
                break;
            case GOOD:
                doSearchProduct(funcKey.getPayload());
                break;
        }
    }

    private void doSearchProduct(String searchSting) {
        if (scenarioManager.getChildScenario(this) == addPositionsScenario) {
            addPositionsScenario.searchProduct(searchSting);
        } else if (scenarioManager.getChildScenario(this) == addPayments) {
            try {
                scenarioManager.tryToCancel(addPayments, () -> addItems(searchSting));
            } catch (ForceImpossibleException e) {
                e.printStackTrace();
            }
        }
    }

    private void doPayment(String payType) {
        doSubtotalPrivate(payType);
    }

    private void doSubtotalPrivate(String payType) {
        if (scenarioManager.getChildScenario(this) == addPositionsScenario) {
            try {
                scenarioManager.tryToComplete(addPositionsScenario, () -> calcDiscount(payType));
            } catch (ForceImpossibleException e) {
                System.out.println("Can't force complete AddItemsScenario " + e.getLocalizedMessage());
            }
        } else if (scenarioManager.getChildScenario(this) == addPayments) {
            addPayments.changePaymentType(payType);
        }
    }

    @Override
    public void start(UI ui) {
        this.ui = ui;
        initAdditional();
        // Тут в зависимости от состояния чека запускается нужный сценарий
        addItems();
    }

    /**
     * Инициализация дополнений
     */
    private void initAdditional() {
        List<UIFormModel> addModels = new ArrayList<>();
        for (SaleScenarioAdditional additional : additional) {
            addModels.addAll(additional.getAdditionalModels());
        }
        ui.setLayerModels(UILayer.SALE, addModels);
    }

    @Override
    public void doSubtotal() {
        Scenario childScenario = scenarioManager.getChildScenario(this);
        if (childScenario == addPositionsScenario) {
            doSubtotalPrivate(null);
        }
    }

    @Override
    public void paymentSelected(String paymentType) {
        doSubtotalPrivate(paymentType);
    }

}
