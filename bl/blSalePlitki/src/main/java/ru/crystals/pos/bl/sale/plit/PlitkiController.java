package ru.crystals.pos.bl.sale.plit;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.LayersManager;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.sale.AddPositionsScenario;
import ru.crystals.pos.bl.api.sale.SaleScenario;
import ru.crystals.pos.bl.api.sale.SaleScenarioAdditional;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.UIFormModel;
import ru.crystals.pos.ui.forms.sale.PlitkiFormModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class PlitkiController implements SaleScenarioAdditional {

    private final PlitkiFormModel plitkiModel;
    private final LayersManager layersManager;
    private final ScenarioManager scenarioManager;
    private final AddPositionsScenario addPositionsScenario;
    private final SaleScenario saleScenario;

    public PlitkiController(LayersManager layersManager, ScenarioManager scenarioManager, AddPositionsScenario addPositionsScenario, SaleScenario saleScenario) {
        this.layersManager = layersManager;
        this.scenarioManager = scenarioManager;
        this.addPositionsScenario = addPositionsScenario;
        this.saleScenario = saleScenario;
        this.plitkiModel = new PlitkiFormModel(new ArrayList<>(), this::onPlitkaClick);
        initModel();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(this::onTimer, 5, 5, TimeUnit.SECONDS);
    }

    private void onTimer() {
        int rnd = new Random().nextInt(0xFFFFFF);
        plitkiModel.getPlitki().set(plitkiModel.getPlitki().size() - 1, Integer.toHexString(rnd));
        plitkiModel.modelChanged();
    }

    private void initModel() {
        plitkiModel.getPlitki().clear();
        plitkiModel.getPlitki().add("-= EXIT =- ");

        plitkiModel.getPlitki().add("p:cash");
        plitkiModel.getPlitki().add("p:bank");

        plitkiModel.getPlitki().add("Кефир");
        plitkiModel.getPlitki().add("Полбатона");
        plitkiModel.getPlitki().add("Вода");
        plitkiModel.getPlitki().add("Спички");
        plitkiModel.getPlitki().add("Туалет");
        plitkiModel.getPlitki().add("Капучино");
    }

    private void onPlitkaClick(String s) {
        System.out.println("Plitka clicked " + s);
        if (s.contains("EXIT")) {
            layersManager.setLayer(UILayer.LOGIN);
        } else if (s.startsWith("p:")) {
            saleScenario.doSubtotal(s.replace("p:", ""));
        } else {
            if (scenarioManager.isActive(addPositionsScenario)) {
                addPositionsScenario.onSearchProduct(s);
            }
        }
    }

    @Override
    public Collection<UIFormModel> getAdditionalModels() {
        return Collections.singleton(plitkiModel);
    }

}
