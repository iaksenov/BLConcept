package ru.crystals.pos.ui.forms.sale.purchase;

import ru.crystals.pos.ui.forms.UIFormCallbackModel;

import java.util.function.Consumer;

public class PurchaseFrameModel extends UIFormCallbackModel<PurchaseFormCallback> {

    private PurchaseStages purchaseStage;

    private UIPurchase purchase = new UIPurchase();

    public PurchaseFrameModel(Consumer<PurchaseFormCallback> callback) {
        super(callback);
    }

    public UIPurchase getPurchase() {
        return purchase;
    }

    public void setPurchase(UIPurchase purchase) {
        this.purchase = purchase;
    }

    public PurchaseStages getPurchaseStage() {
        return purchaseStage;
    }

    public void setPurchaseStage(PurchaseStages purchaseStage) {
        this.purchaseStage = purchaseStage;
    }
}
