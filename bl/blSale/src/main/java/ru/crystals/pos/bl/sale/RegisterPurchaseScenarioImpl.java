package ru.crystals.pos.bl.sale;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseResult;
import ru.crystals.pos.bl.api.sale.RegisterPurchaseScenario;
import ru.crystals.pos.bl.api.spinner.CallableSpinnerArg;
import ru.crystals.pos.bl.common.CallableSpinner;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

@Component
public class RegisterPurchaseScenarioImpl implements RegisterPurchaseScenario {

    private UI ui;
    private final ScenarioManager scenarioManager;

    public RegisterPurchaseScenarioImpl(ScenarioManager scenarioManager) {
        this.scenarioManager = scenarioManager;
    }

    @Override
    public void start(UI ui, Purchase purchase, Consumer<RegisterPurchaseResult> onComplete) throws Exception {
        this.ui = ui;
        printPurchase(purchase, onComplete);
    }

    private void printPurchase(Purchase purchase, Consumer<RegisterPurchaseResult> onComplete) {
        CallableSpinnerArg<Purchase> process = new CallableSpinnerArg<>("Печать чека ...", printCheck(purchase));
        scenarioManager.startChildAsync(new CallableSpinner<>(), process,
            p -> onPrintComplete(p, onComplete),
            e -> onPrintError(e, purchase, onComplete));
    }

    private void onPrintError(Exception e, Purchase purchase, Consumer<RegisterPurchaseResult> onComplete) {
        ui.showForm(new MessageFormModel("Чек не печатается. Повторим еще раз.",
            () -> printPurchase(purchase, onComplete)));
    }

    private void onPrintComplete(Purchase purchase, Consumer<RegisterPurchaseResult> onComplete) {
        RegisterPurchaseResult result = new RegisterPurchaseResult(purchase);
        onComplete.accept(result);
    }

    private Callable<Purchase> printCheck(Purchase purchase) {
        return () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return purchase;
        };
    }

}
