package ru.crystals.pos.bl.manager;

import ru.crystals.pos.bl.api.scenarios.Scenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.UIValueFormModel;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public class UIProxyImpl implements UI {

    private final Scenario scenario;
    private final UI ui;
    private Predicate<Scenario> checkCurrentScenario;

    public UIProxyImpl(Scenario scenario, UI ui, Predicate<Scenario> checkCurrentScenario) {
        this.scenario = scenario;
        this.ui = ui;
        this.checkCurrentScenario = checkCurrentScenario;
    }

    @Override
    public void showForm(UIFormModel uiForm) {
        checkScenario();
        ui.showForm(uiForm);
    }

    private void checkScenario() {
        if (!checkCurrentScenario.test(scenario)) {
            throw new RuntimeException("Scenario " + scenario.getClass().getName() + " is not current");
        }
    }

    @Override
    public <V> Optional<V> getFormValue(UIValueFormModel<V> model) {
        checkScenario();
        return ui.getFormValue(model);
    }

    @Override
    public void setLayerModels(UILayer uiLayer, Collection<UIFormModel> models) {
        checkScenario();
        ui.setLayerModels(uiLayer, models);
    }

}
