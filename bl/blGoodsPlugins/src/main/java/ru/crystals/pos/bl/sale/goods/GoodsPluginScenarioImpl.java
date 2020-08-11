package ru.crystals.pos.bl.sale.goods;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.listener.VoidListener;
import ru.crystals.pos.bl.api.sale.goods.GoodsPluginScenario;
import ru.crystals.pos.bl.api.sale.goods.Position;
import ru.crystals.pos.bl.api.sale.goods.Product;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.forms.sale.ProductCountModel;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class GoodsPluginScenarioImpl implements GoodsPluginScenario {

    private final UI ui;
    private Product product;
    private Consumer<Position> onComplete;
    private VoidListener onCancel;
    private ProductCountModel model;

    public GoodsPluginScenarioImpl(UI ui) {
        this.ui = ui;
    }

    @Override
    public void start(Product product, Consumer<Position> onComplete, VoidListener onCancel) {
        this.product = product;
        this.onComplete = onComplete;
        this.onCancel = onCancel;
        model = new ProductCountModel(product.getProductName(), "введите кол-во", this::onResult);
        ui.showForm(model);
    }

    private void onResult(Optional<Integer> result) {
        if (result.isPresent()) {
            onCountEntered(result.get());
        } else {
            onCancel.call();
        }
    }

    private void onCountEntered(Integer integer) {
        if (integer > 0) {
            Position position = new Position(product.getProductName(), BigDecimal.valueOf(integer));
            onComplete.accept(position);
        }
    }

    @Override
    public boolean forceComplete() {
        return false;
    }
}
