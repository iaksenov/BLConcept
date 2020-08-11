package ru.crystals.pos.bl.api.sale.goods;

import ru.crystals.pos.bl.api.ForceCompleteScenario;
import ru.crystals.pos.bl.api.InOutCancelScenario;

public interface GoodsPluginScenario extends InOutCancelScenario<Product, Position>, ForceCompleteScenario {

}
