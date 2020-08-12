package ru.crystals.pos.bl.api.sale.goods;

import ru.crystals.pos.bl.api.InOutCancelScenario;

import java.util.function.Consumer;

public interface GoodsPluginScenario extends InOutCancelScenario<Product, Position> {

    boolean tryToComplete(Consumer<Position> completeConsumer);

}
