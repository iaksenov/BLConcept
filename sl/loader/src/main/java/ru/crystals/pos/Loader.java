package ru.crystals.pos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.crystals.pos.bl.ScenarioManager;
import ru.crystals.pos.bl.events.HWEventRouter;
import ru.crystals.pos.bl.manager.ScenarioManagerImpl;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UIKeyListener;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.UILayers;
import ru.crystals.pos.ui.forms.loading.LoadingFormModel;
import ru.crystals.pos.ui.forms.message.MessageFormModel;

/**
 * Старт приложения
 */
public class Loader {

    private static final Logger log = LoggerFactory.getLogger(Loader.class);

    public Loader() {
        // чтобы java.util.logging конфигурить (он в jnativehook используется)
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    public static void main(String[] args) throws InterruptedException {
        log.info("App start");
        Loader loader = new Loader();
        loader.startSpring();
    }

    private void startSpring() throws InterruptedException {
        // загрузим только UI, он в отдельном пакете
        AnnotationConfigApplicationContext uiContext = new AnnotationConfigApplicationContext();
        uiContext.scan("csi.pos.ui");
        uiContext.refresh();

        UI ui = uiContext.getBean(UI.class);
        LoadingFormModel loadingFormModel = new LoadingFormModel("Загрузка", "v0.0.1");
        UILayers uiLayers = uiContext.getBean(UILayers.class);
        uiLayers.setLayer(UILayer.START);
        ui.showForm(loadingFormModel);

        //Thread.sleep(1000);

        HWEventRouter.setUiKeyListener(uiContext.getBean(UIKeyListener.class));
        ScenarioManagerImpl.setUi(ui, uiLayers);

        try {
            AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
            context2.scan("ru.crystals.pos");
            context2.refresh();
            startBL(context2);
        } catch (Exception e) {
            ui.showForm(new MessageFormModel(e.getMessage(), () -> System.exit(0)));
        }
    }

    /**
     * Запуск БЛ
     * @param ctx spring контекст
     */
    public void startBL(AnnotationConfigApplicationContext ctx) {
        ScenarioManager scenarioManager = ctx.getBean(ScenarioManager.class);
        scenarioManager.setLayer(UILayer.LOGIN);
    }

}
