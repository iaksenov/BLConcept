package ru.crystals.pos.bl.sale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.payment.PaymentPluginInArg;
import ru.crystals.pos.bl.api.payment.PaymentPluginScenario;
import ru.crystals.pos.bl.api.sale.AddPaymentsScenario;
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
public class AddPaymentsScenarioImpl implements AddPaymentsScenario  {

    private UI ui;
    private ScenarioManager scenarioManager;
    private DocModule docModule;
    private Map<String, PaymentPluginScenario> pluginsMap;

    private static final String DEFAULT_PAYMENT_TYPE = "cash";

    public AddPaymentsScenarioImpl(UI ui, ScenarioManager scenarioManager,
                                   DocModule docModule,
                                   @Autowired(required = false)
                                   Collection<PaymentPluginScenario> plugins) {
        this.ui = ui;
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
    public void start(String preferredPaymentType, VoidListener onComplete, VoidListener onCancel) {
        if (pluginsMap.isEmpty()) {
            ui.showForm(new MessageFormModel("Плагины оплат отсутствуют", onCancel::call));
        } else {
            String paymentType = Optional.ofNullable(preferredPaymentType).orElse(DEFAULT_PAYMENT_TYPE);
            PaymentPluginScenario preferredPlugin = pluginsMap.get(paymentType.toLowerCase());
            if (preferredPlugin == null) {
                ui.showForm(new MessageFormModel("Плагин " + paymentType + " отсутствует", onCancel::call));
            } else {
                PaymentPluginInArg arg = new PaymentPluginInArg(new BigDecimal("0.00"));
                scenarioManager.startChild(preferredPlugin, arg,
                    p -> onPluginComplete(p, onComplete),
                    () -> onPaymentPluginCancel(onCancel));
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

}
