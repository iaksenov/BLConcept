package ru.crystals.pos.bl.sale.goods;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.VoidListener;
import ru.crystals.pos.bl.api.sale.goods.GoodsPluginScenario;
import ru.crystals.pos.bl.api.sale.goods.Position;
import ru.crystals.pos.bl.api.sale.goods.Product;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.callback.ResultOrCancelCallback;
import ru.crystals.pos.ui.forms.sale.ProductCountModel;

import java.math.BigDecimal;
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
        model = new ProductCountModel(product.getProductName(), "введите кол-во", new ResultOrCancelCallback<Integer>() {
            @Override
            public void onEntered(Integer result) {
                onCountEntered(result);
            }

            @Override
            public void onCancel() {
                onCancel.call();
            }
        });
        ui.showForm(model);
    }

    private void onCountEntered(Integer integer) {
        if (integer > 0) {
            Position position = new Position(product.getProductName(), BigDecimal.valueOf(integer));
            onComplete.accept(position);
        }
    }

}
