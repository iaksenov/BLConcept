package ru.crystals.pos.ui.forms.sale;

import ru.crystals.pos.ui.callback.ResultOrCancelCallback;
import ru.crystals.pos.ui.forms.UIFormModel;

public class ProductCountModel extends UIFormModel {

    private String productName;
    private String countHint;
    private ResultOrCancelCallback<Integer> callback;

    public ProductCountModel(String productName, String countHint, ResultOrCancelCallback<Integer> callback) {
        this.productName = productName;
        this.countHint = countHint;
        this.callback = callback;
    }

    public String getProductName() {
        return productName;
    }

    public String getCountHint() {
        return countHint;
    }

    public ResultOrCancelCallback<Integer> getCallback() {
        return callback;
    }

}
