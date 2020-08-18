package ru.crystals.pos.bl.sale;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.sale.CalcDiscountScenario;
import ru.crystals.pos.bl.api.spinner.CallableSpinnerArg;
import ru.crystals.pos.bl.common.CallableSpinner;
import ru.crystals.pos.docs.DocModule;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

@Component
public class CalcDiscountScenarioImpl implements CalcDiscountScenario {

    private final UI ui;
    private final ScenarioManager scenarioManager;
    private final DocModule docModule;

    public CalcDiscountScenarioImpl(UI ui, ScenarioManager scenarioManager, DocModule docModule) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
        this.docModule = docModule;
    }

    @Override
    public void start(VoidListener onComplete, VoidListener onCancel) {
        Purchase purchase = docModule.getCurrentPurchase();
        CallableSpinnerArg<BigDecimal> arg = new CallableSpinnerArg<>("Расчет скидок ...", calculateDiscounts(purchase));
        scenarioManager.startChildAsync(new CallableSpinner<>(ui), arg, bd -> onCalcComplete(bd, onComplete), e -> onCalculateError(e, onCancel));
    }

    private void onCalcComplete(BigDecimal result, VoidListener onComplete) {
        docModule.setDiscountAmount(result);
        onComplete.call();
    }

    private void onCalculateError(Exception e, VoidListener onConfirm) {
        ui.showForm(new MessageFormModel(e.getLocalizedMessage(), onConfirm::call));
    }

    private Callable<BigDecimal> calculateDiscounts(Purchase purchase) {
        return () -> {
            if (purchase.getPositions().isEmpty()) {
                throw new Exception("В чеке нет позиций");
            }
            try {
                Thread.sleep(700L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return BigDecimal.valueOf(950, 2);
        };
    }


}
