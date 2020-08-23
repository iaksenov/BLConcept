package ru.crystals.pos.bl.sale.purchase;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.crystals.pos.bl.api.sale.PurchaseStage;
import ru.crystals.pos.bl.api.sale.SaleScenario;
import ru.crystals.pos.bl.api.sale.SaleScenarioAdditional;
import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.bl.events.CurrentScenarioChanged;
import ru.crystals.pos.docs.data.Payment;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.docs.event.PurchaseUpdatedEvent;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFormCallback;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFrameModel;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseStages;
import ru.crystals.pos.ui.forms.sale.purchase.UIPayment;
import ru.crystals.pos.ui.forms.sale.purchase.UIPosition;
import ru.crystals.pos.ui.forms.sale.purchase.UIPurchase;

import java.util.Collection;
import java.util.Collections;

@Service
public class PurchaseFrameController implements SaleScenarioAdditional {

    private final PurchaseFrameModel model;
    private final SaleScenario saleScenario;

    public PurchaseFrameController(SaleScenario saleScenario) {
        this.saleScenario = saleScenario;
        this.model = new PurchaseFrameModel(this::frameCallback);
    }

    private void frameCallback(PurchaseFormCallback purchaseFormCallback) {
        switch (purchaseFormCallback.getAction()) {
            case SUBTOTOAL:saleScenario.doSubtotal();
            break;
        }
    }

    @EventListener
    private void onPurchaseUpdated(PurchaseUpdatedEvent event) {
        model.setPurchase(convertPurchase(event.getPurchase()));
        model.modelChanged();
    }

    @EventListener(condition="@SaleScenarioChangePredicate.test(#event)")
    private void onScenarioChanged(CurrentScenarioChanged event) {
        Scenario scenario = event.getCurrentScenario();
        PurchaseStages purchaseStage = ((PurchaseStage) scenario).getPurchaseStage();
        model.setPurchaseStage(purchaseStage);
        model.modelChanged();
    }

    private UIPurchase convertPurchase(Purchase purchase) {
        UIPurchase uip = new UIPurchase();
        uip.setDiscountAmount(purchase.getDiscountAmount());
        for (Position position : purchase.getPositions()) {
            uip.getPositions().add(convertPosition(position));
        }
        for (Payment payment : purchase.getPayments()) {
            uip.getPayments().add(convertPayment(payment));
        }
        return uip;
    }

    private UIPayment convertPayment(Payment payment) {
        return new UIPayment(payment.getTypeName(), payment.getSumm());
    }

    private UIPosition convertPosition(Position position) {
        return new UIPosition(position.getName(), position.getItem(), position.getPrice(), position.getCount());
    }

    @Override
    public Collection<UIFormModel> getAdditionalModels() {
        return Collections.singleton(model);
    }
}
