package ru.crystals.pos;

import csi.pos.ui.swing.forms.LoginForm;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.loading.LoadingFormModel;
import ru.crystals.pos.ui.forms.loading.LoginFormModel;
import ru.crystals.pos.ui.label.Label;

import java.util.function.Consumer;


public class Loader {

    public Loader() {
    }

    public static void main(String[] args) throws InterruptedException {
        Loader loader = new Loader();
        loader.start();
    }

    private void start() throws InterruptedException {
        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext();
        context1.scan("csi.pos.ui.swing");
        context1.refresh();

        UI ui = context1.getBean(UI.class);
        LoadingFormModel loadingFormModel = new LoadingFormModel("Загрузка", "v0.0.1");
        ui.showForm(UILayer.START, loadingFormModel);

        Thread.sleep(200L);

        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext();
        context2.scan("ru.crystals.pos");
        context2.setParent(context1);
        context2.refresh();

        Thread.sleep(200L);

        loadingFormModel.setCaption("Загрузилось");
        loadingFormModel.modelChanged();
        System.out.println("Application started");

        Thread.sleep(200L);

        LoginFormModel login = new LoginFormModel("Открыта смена", Label.empty("С фискальником всё нормально"),
            null, this::onPasswordEntered);
        ui.showForm(UILayer.LOGIN, login);

        Thread.sleep(500L);

        ui.setLayer(UILayer.START);

        Thread.sleep(500L);

        ui.setLayer(UILayer.LOGIN);

    }

    private void onPasswordEntered(String pwd) {

    }

}
