package ru.crystals.pos.ui.forms.sale;

import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.UIValueCallbackFormModel;

import java.util.function.Consumer;

public class ProductCountModel extends UIValueCallbackFormModel<Integer, InteractiveValueCancelledCallback<Integer>> {

    private final String productName;
    private final String countHint;

    public ProductCountModel(String productName, String countHint, Consumer<InteractiveValueCancelledCallback<Integer>> callback) {
        super(callback);
        this.productName = productName;
        this.countHint = countHint;
    }

    public String getProductName() {
        return productName;
    }

    public String getCountHint() {
        return countHint;
    }

}
