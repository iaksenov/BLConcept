package ru.crystals.pos.bl.sale.purchase;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.crystals.pos.bl.api.sale.SaleScenarioAdditional;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.docs.data.Purchase;
import ru.crystals.pos.docs.event.PurchaseUpdatedEvent;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFormCallback;
import ru.crystals.pos.ui.forms.sale.purchase.PurchaseFrameModel;
import ru.crystals.pos.ui.forms.sale.purchase.UIPosition;
import ru.crystals.pos.ui.forms.sale.purchase.UIPurchase;

import java.util.Collection;
import java.util.Collections;

@Service
public class PurchaseFrameController implements SaleScenarioAdditional {

    private final PurchaseFrameModel model;

    public PurchaseFrameController() {
        this.model = new PurchaseFrameModel(this::frameCallback);
    }

    private void frameCallback(PurchaseFormCallback purchaseFormCallback) {

    }

    @EventListener
    private void onPurchaseUpdated(PurchaseUpdatedEvent event) {
        model.setPurchase(convertPurchase(event.getPurchase()));
        model.modelChanged();
    }

    private UIPurchase convertPurchase(Purchase purchase) {
        UIPurchase uip = new UIPurchase();
        uip.getPositions().clear();
        for (Position position : purchase.getPositions()) {
            uip.getPositions().add(convertPosition(position));
        }
        return uip;
    }

    private UIPosition convertPosition(Position position) {
        return new UIPosition(position.getName(), position.getItem(), position.getPrice(), position.getCount());
    }

    @Override
    public Collection<UIFormModel> getAdditionalModels() {
        return Collections.singleton(model);
    }
}
