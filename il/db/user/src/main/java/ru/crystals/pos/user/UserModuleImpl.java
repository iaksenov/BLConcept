package ru.crystals.pos.user;

import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

/**
 * Реализация модуля кассиров
 */
@Service
public class UserModuleImpl implements UserModule {

    private final UserDAO dao;

    public UserModuleImpl(UserDAO dao) {
        this.dao = dao;
    }

    @Override
    public User loginByPassword(String password) throws LoginFailedException {
        return dao.getUserByPassword(password).orElseThrow(() -> new LoginFailedException("Пользователь не найден"));
    }

    @Override
    public User loginByBarcode(String code) throws LoginFailedException {
        return dao.getUserByBarcode(code).orElseThrow(() -> new LoginFailedException("Пользователь не найден"));
    }

    @Override
    public User loginByMSR(MSRTracks msrTracks) throws LoginFailedException {
        return dao.getUserByMSR(msrTracks).orElseThrow(() -> new LoginFailedException("Пользователь не найден"));
    }

}
