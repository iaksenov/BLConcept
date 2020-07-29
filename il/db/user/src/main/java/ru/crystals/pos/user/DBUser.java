package ru.crystals.pos.user;

import java.util.Set;

public class DBUser extends User {

    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final Set<UserRight> rights;
    private final String pwd;
    private final String barcode;
    private final String msr;

    public DBUser(String firstName, String lastName, String middleName, Set<UserRight> rights, String pwd, String barcode, String msr) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.rights = rights;
        this.pwd = pwd;
        this.barcode = barcode;
        this.msr = msr;
    }

    public String getPwd() {
        return pwd;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getMsr() {
        return msr;
    }
}
