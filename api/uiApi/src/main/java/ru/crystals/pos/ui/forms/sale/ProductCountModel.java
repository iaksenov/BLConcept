package ru.crystals.pos.ui.forms.sale;

import ru.crystals.pos.ui.forms.UIFormCallbackModel;

import java.util.Optional;
import java.util.function.Consumer;

public class ProductCountModel extends UIFormCallbackModel<Optional<Integer>> {

    private final String productName;
    private final String countHint;

    public ProductCountModel(String productName, String countHint, Consumer<Optional<Integer>> callback) {
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
