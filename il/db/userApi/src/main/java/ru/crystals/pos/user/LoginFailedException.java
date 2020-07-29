package ru.crystals.pos.user;

public class LoginFailedException extends Exception {

    public LoginFailedException(String message) {
        super(message);
    }
}
