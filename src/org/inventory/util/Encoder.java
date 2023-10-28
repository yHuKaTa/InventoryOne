package org.inventory.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Objects;

public class Encoder {
    private static Encoder encoder;
    private final BCryptPasswordEncoder bCrypt;

    private Encoder() {
        bCrypt = new BCryptPasswordEncoder();
    }

    public static Encoder getInstance() {
        if (Objects.isNull(encoder)) {
            encoder = new Encoder();
        }
        return encoder;
    }

    public String encode(String text) {
        return bCrypt.encode(text);
    }

    public boolean match(String text, String other) {
        return bCrypt.matches(text, other);
    }
}
