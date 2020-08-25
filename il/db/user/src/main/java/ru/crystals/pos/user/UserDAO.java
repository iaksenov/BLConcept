package ru.crystals.pos.user;

import org.springframework.stereotype.Service;
import ru.crystals.pos.hw.events.listeners.MSRTracks;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDAO {

    public static final String PREFIX = "X-";
    private final List<DBUser> users;

    public UserDAO() {
        users = Stream.of(
            getUser("Кассир", "К", "К", "2", PREFIX + "002", "X-002", UserRight.SALE),
            getUser("Ст.кассир", "С", "С", "1", PREFIX + "001", "X-001", UserRight.SHIFT),
            getUser("Инженер", "И", "И", "3", PREFIX + "003", "X-003", UserRight.CONFIGURATION)
        ).collect(Collectors.toList());
    }

    private DBUser getUser(String firstName, String lastName, String middleName, String pwd, String barcode, String msr, UserRight... rights) {
        return new DBUser(firstName, lastName, middleName, Stream.of(rights).collect(Collectors.toSet()), pwd, barcode, msr);
    }

    public Optional<DBUser> getUserByPassword(String password) {
        return users.stream().filter(u -> password.equals(u.getPwd())).findFirst();
    }

    public Optional<DBUser> getUserByBarcode(String code) {
        return users.stream().filter(u -> code.equals(u.getBarcode())).findFirst();
    }

    public Optional<DBUser> getUserByMSR(MSRTracks msrTracks) {
        return users.stream().filter(u -> msrTracks.getTrack2().equals(u.getMsr())).findFirst();
    }
}
