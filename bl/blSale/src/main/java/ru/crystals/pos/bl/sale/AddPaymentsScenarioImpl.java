package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.payment.PaymentPluginInArg;
import ru.crystals.pos.bl.api.payment.PaymentPluginScenario;
import ru.crystals.pos.bl.api.sale.AddPaymentsScenario;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceCancelledScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceImpossibleException;
import ru.crystals.pos.docs.DocModule;
import ru.crystals.pos.docs.data.Payment;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class AddPaymentsScenarioImpl implements AddPaymentsScenario, ForceCancelledScenario {

    private UI ui;
    private VoidListener onComplete;
    private VoidListener onCancel;
    private final ScenarioManager scenarioManager;
    private final DocModule docModule;
    private final Map<String, PaymentPluginScenario> pluginsMap;

    private static final String DEFAULT_PAYMENT_TYPE = "cash";

    public AddPaymentsScenarioImpl(ScenarioManager scenarioManager,
                                   DocModule docModule,
                                   @Autowired(required = false)
                                   Collection<PaymentPluginScenario> plugins) {
        this.scenarioManager = scenarioManager;
        this.docModule = docModule;
        pluginsMap = new HashMap<>();
        if (plugins != null) {
            for (PaymentPluginScenario plugin : plugins) {
                pluginsMap.putIfAbsent(plugin.getPaymentTypeName().toLowerCase(), plugin);
            }
        }
    }

    @Override
    public void start(UI ui, String preferredPaymentType, VoidListener onComplete, VoidListener onCancel) {
        this.ui = ui;
        this.onComplete = onComplete;
        this.onCancel = onCancel;
        startPaymentPlugin(preferredPaymentType);
    }

    private void startPaymentPlugin(String preferredPaymentType) {
        if (pluginsMap.isEmpty()) {
            this.ui.showForm(new MessageFormModel("Плагины оплат отсутствуют", this.onCancel::call));
        } else {
            String paymentType = Optional.ofNullable(preferredPaymentType).orElse(DEFAULT_PAYMENT_TYPE);
            PaymentPluginScenario preferredPlugin = pluginsMap.get(paymentType.toLowerCase());
            if (preferredPlugin == null) {
                this.ui.showForm(new MessageFormModel("Плагин " + paymentType + " отсутствует", this.onCancel::call));
            } else {
                PaymentPluginInArg arg = new PaymentPluginInArg(new BigDecimal("0.00"));
                scenarioManager.startChild(preferredPlugin, arg,
                    p -> onPluginComplete(p, this.onComplete),
                    () -> onPaymentPluginCancel(this.onCancel));
            }
        }
    }

    private void onPluginComplete(Payment payment, VoidListener onScenarioComplete) {
        docModule.addPayment(payment);
        // TODO: если оплат достаточно, то можно и завершиться
        onScenarioComplete.call();
    }

    private void onPaymentPluginCancel(VoidListener onScenarioCancel) {
        // TODO: если в чеке есть оплаты, то назад пути нет
        onScenarioCancel.call();
    }

    @Override
    public void changePaymentType(String paymentType) {
        Scenario childScenario = scenarioManager.getChildScenario(this);
        if (childScenario instanceof PaymentPluginScenario) {
            try {
                scenarioManager.tryToCancel(childScenario, () -> startPaymentPlugin(paymentType));
            } catch (ForceImpossibleException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void tryToCancel() throws ForceImpossibleException {
        Scenario childScenario = scenarioManager.getChildScenario(this);
        if (childScenario instanceof PaymentPluginScenario) {
            scenarioManager.tryToCancel(childScenario, null);
        }
    }

}
