package com.sarana_NIS.newTown_backend.security.encoding;

import org.springframework.security.crypto.password.PasswordEncoder;

public class HexPasswordEncoder implements PasswordEncoder {

    private final HexBinaryConverter hexConverter = new HexBinaryConverter();

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        return hexConverter.toHex(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null && encodedPassword == null) {
            return true;
        }
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        String encodedInput = encode(rawPassword);
        return encodedInput.equals(encodedPassword);
    }
}