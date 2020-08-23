package ru.crystals.pos.bl.sale.purchase;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.sale.PurchaseStage;
import ru.crystals.pos.bl.events.CurrentScenarioChanged;

import java.util.function.Predicate;

@Component("SaleScenarioChangePredicate")
public class SaleScenarioChangePredicate implements Predicate<CurrentScenarioChanged> {

    @Override
    public boolean test(CurrentScenarioChanged event) {
        return event.getCurrentScenario() instanceof PurchaseStage;
    }

}
