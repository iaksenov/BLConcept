package ru.crystals.pos;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.api.login.LoginScenario;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.loading.LoadingFormModel;


public class Loader {

    public Loader() {
    }

    public static void main(String[] args) throws InterruptedException {
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

        Thread.sleep(600L);

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
