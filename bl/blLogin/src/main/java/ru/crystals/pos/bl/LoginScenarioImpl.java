package ru.crystals.pos.bl;

import org.springframework.stereotype.Component;
import ru.crystals.pos.bl.api.login.LoginScenario;
import ru.crystals.pos.bl.api.sale.SaleScenario;
import ru.crystals.pos.hw.events.listeners.MSRTracks;
import ru.crystals.pos.ui.UI;
import ru.crystals.pos.ui.UILayer;
import ru.crystals.pos.ui.forms.loading.LoginFormModel;
import ru.crystals.pos.ui.label.Label;
import ru.crystals.pos.user.LoginFailedException;
import ru.crystals.pos.user.User;
import ru.crystals.pos.user.UserModule;
import ru.crystals.pos.user.UserRight;

@Component
public class LoginScenarioImpl implements LoginScenario {

    private final UI ui;
    private final ScenarioManager scenarioManager;
    private final UserModule userModule;
    private final SaleScenario saleScenario;

    private LoginFormModel model;

    public LoginScenarioImpl(UI ui, ScenarioManager scenarioManager,
                             UserModule userModule, SaleScenario saleScenario) {
        this.ui = ui;
        this.scenarioManager = scenarioManager;
        this.userModule = userModule;
        this.saleScenario = saleScenario;
    }

    @Override
    public void start() {
        this.model = showLoginForm("");
    }

    private LoginFormModel showLoginForm(String errorText) {
        LoginFormModel model = new LoginFormModel(getShiftText(),
            getInfoText(),
            Label.error(errorText),
            this::onPasswordEntered);
        ui.showForm(UILayer.LOGIN, model);
        return model;
    }

    private void onPasswordEntered(String password) {
        try {
            User user = userModule.loginByPassword(password);
            startNextScenario(user);
        } catch (LoginFailedException e) {
            onLoginError(e);
        }
    }

    @Override
    public void onBarcode(String code) {
        try {
            User user = userModule.loginByBarcode(code);
            startNextScenario(user);
        } catch (LoginFailedException e) {
            onLoginError(e);
        }
    }

    @Override
    public void onMSR(MSRTracks msrTracks) {
        try {
            User user = userModule.loginByMSR(msrTracks);
            startNextScenario(user);
        } catch (LoginFailedException e) {
            onLoginError(e);
        }
    }

    private void onLoginError(LoginFailedException error) {
        model.setLoginFailedText(Label.error(error.getLocalizedMessage()));
        model.modelChanged();
    }

    private void startNextScenario(User user) {
        showLoginForm(user.getFirstName() + " ");
        if (user.hasRight(UserRight.SALE)) {
            scenarioManager.startScenario(saleScenario);
        } else if (user.hasRight(UserRight.SHIFT)) {
            showLoginForm(user.getFirstName() + " еще не реализовано");
        } else if (user.hasRight(UserRight.CONFIGURATION)) {
            showLoginForm(user.getFirstName() + " еще не реализовано");
        }
    }

    private Label getInfoText() {
        return Label.empty("Есть незавершенный чек");
    }

    private String getShiftText() {
        return "Открыта смена №00";
    }

}
