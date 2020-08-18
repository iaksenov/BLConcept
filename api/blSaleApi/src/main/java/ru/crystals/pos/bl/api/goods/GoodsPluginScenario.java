package ru.crystals.pos.bl.api.goods;

import ru.crystals.pos.bl.api.InOutCancelScenario;
import ru.crystals.pos.docs.data.Position;

import java.util.function.Consumer;

public interface GoodsPluginScenario extends InOutCancelScenario<Product, Position> {

    boolean tryToComplete(Consumer<Position> completeConsumer);

}
