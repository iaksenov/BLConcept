package ru.crystals.pos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.login.LoginScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.loading.LoadingFormModel;


public class Loader {

    private static final Logger log = LoggerFactory.getLogger(Loader.class);

    public Loader() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    public static void main(String[] args) throws InterruptedException {
        log.info("App start");
        Loader loader = new Loader();
        loader.startSpring();
    }

    private void startSpring() throws InterruptedException {
        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext();
        context1.scan("csi.pos.ui.swing");
        context1.refresh();

        UI ui = context1.getBean(UI.class);
        LoadingFormModel loadingFormModel = new LoadingFormModel("Загрузка", "v0.0.1");
        ui.showForm(UILayer.START, loadingFormModel);

        Thread.sleep(1500L);

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
        context2.scan("ru.crystals.pos");
        context2.setParent(context1);
        context2.refresh();
        startBL(context2);
    }

    public void startBL(AnnotationConfigApplicationContext ctx) {
        ScenarioManager scenarioManager = ctx.getBean(ScenarioManager.class);
        LoginScenario loginScenario = ctx.getBean(LoginScenario.class);
        scenarioManager.startScenario(loginScenario);
    }

}
