package ru.crystals.pos.bl.sale.goods;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.goods.GoodsPluginScenario;
import ru.crystals.pos.bl.api.goods.Product;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.scenarios.force.ForceCompleteImpossibleException;
import ru.crystals.pos.bl.api.scenarios.force.ForceCompletedScenario;
import ru.crystals.pos.docs.data.Position;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.callback.InteractiveValueCancelledCallback;
import ru.crystals.pos.ui.forms.sale.ProductCountModel;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class GoodsPluginScenarioImpl implements GoodsPluginScenario, ForceCompletedScenario<Position> {

    private final UI ui;
    private Product product;
    private Consumer<Position> onComplete;
    private VoidListener onCancel;
    private ProductCountModel model;

    private Integer count;

    public GoodsPluginScenarioImpl(UI ui) {
        this.ui = ui;
    }

    @Override
    public void start(Product product, Consumer<Position> onComplete, VoidListener onCancel) {
        this.count = null;
        this.product = product;
        this.onComplete = onComplete;
        this.onCancel = onCancel;
        model = new ProductCountModel(product.getProductName(), "введите кол-во", this::onResult);
        ui.showForm(model);
    }

    private void onResult(InteractiveValueCancelledCallback<Integer> result) {
        switch (result.getAction()) {
            case CHANGED: this.count = result.getValue();
            break;
            case ENTERED: onCountEntered(result.getValue(), this.onComplete);
            break;
            case CANCELLED: onCancel.call();
        }
    }

    private boolean onCountEntered(Integer value, Consumer<Position> consumer) {
        if (checkValue(value)) {
            Position position = createPosition(value);
            consumer.accept(position);
            return true;
        } else {
            return false;
        }
    }

    private boolean checkValue(Integer value) {
        return value != null && value > 0;
    }

    private Position createPosition(Integer value) {
        return new Position(product.getProductName(), BigDecimal.valueOf(value));
    }

    @Override
    public Position tryToComplete() throws ForceCompleteImpossibleException {
        Optional<Integer> formValue = ui.getFormValue(model);
        if (formValue.isPresent() && checkValue(formValue.get())) {
            return createPosition(formValue.get());
        } else {
            throw new ForceCompleteImpossibleException("Incorrect current value");
        }
    }
}
