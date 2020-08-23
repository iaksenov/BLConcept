package ru.crystals.pos.bl.api.goods;

import ru.crystals.pos.bl.api.scenarios.InOutCancelScenario;
import ru.crystals.pos.bl.api.scenarios.special.ForceCompletedScenario;
import ru.crystals.pos.docs.data.Position;

public interface GoodsPluginScenario extends InOutCancelScenario<Product, Position>, ForceCompletedScenario<Position> {

}
