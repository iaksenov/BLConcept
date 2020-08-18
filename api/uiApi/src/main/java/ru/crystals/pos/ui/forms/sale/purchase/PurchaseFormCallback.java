package ru.crystals.pos.ui.forms.sale.purchase;

/**
 * Обратные события от UI фрейма с чеком.
 * Например редактирование позиции, удаление позиции, кнопка "Подитог" и пр.
 */
public class PurchaseFormCallback {

    public enum Action {
        SUBTOTOAL
    };

    private final Action action;

    private PurchaseFormCallback(Action action) {
        this.action = action;
    }

    public static PurchaseFormCallback subtotal() {
        return new PurchaseFormCallback(Action.SUBTOTOAL);
    }

    public Action getAction() {
        return action;
    }
}
