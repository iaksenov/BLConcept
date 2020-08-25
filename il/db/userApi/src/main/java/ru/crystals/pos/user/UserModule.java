package ru.crystals.pos.user;

import ru.crystals.pos.hw.events.listeners.MSRTracks;

public interface UserModule {

    User loginByPassword(String password) throws LoginFailedException;

    User loginByBarcode(String code) throws LoginFailedException;

    User loginByMSR(MSRTracks msrTracks) throws LoginFailedException;

    boolean isBarcodeForLogin(String barcode);

    boolean isCurrentUserBarcode(String barcode);

    void logoff();
}
