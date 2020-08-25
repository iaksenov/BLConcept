package ru.crystals.pos.user;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

import java.util.Optional;

/**
 * Реализация модуля кассиров
 */
@Service
public class UserModuleImpl implements UserModule {

    private final UserDAO dao;
    private final ApplicationEventPublisher publisher;
    private DBUser user;

    public UserModuleImpl(UserDAO dao, ApplicationEventPublisher publisher) {
        this.dao = dao;
        this.publisher = publisher;
    }

    @Override
    public User loginByPassword(String password) throws LoginFailedException {
        Optional<DBUser> user = dao.getUserByPassword(password);
        user.ifPresent(this::dispatchEvent);
        return user.orElseThrow(() -> new LoginFailedException("Пользователь не найден"));
    }

    @Override
    public User loginByBarcode(String code) throws LoginFailedException {
        Optional<DBUser> user = dao.getUserByBarcode(code);
        user.ifPresent(this::dispatchEvent);
        return user.orElseThrow(() -> new LoginFailedException("Пользователь не найден"));
    }

    @Override
    public User loginByMSR(MSRTracks msrTracks) throws LoginFailedException {
        Optional<DBUser> user = dao.getUserByMSR(msrTracks);
        user.ifPresent(this::dispatchEvent);
        return user.orElseThrow(() -> new LoginFailedException("Пользователь не найден"));
    }

    @Override
    public boolean isBarcodeForLogin(String barcode) {
        return barcode != null && barcode.startsWith(UserDAO.PREFIX);
    }

    @Override
    public boolean isCurrentUserBarcode(String barcode) {
        return user != null && user.getBarcode().equals(barcode);
    }

    @Override
    public void logoff() {
        this.user = null;
        dispatchEvent(null);
    }

    private void dispatchEvent(DBUser user) {
        this.user = user;
        publisher.publishEvent(new UserAuthorisedEvent(user));
    }

}
