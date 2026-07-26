package com.sarana_NIS.newTown_backend.security.encoding;

public class HexPasswordDecoder {

    private final HexBinaryConverter hexConverter = new HexBinaryConverter();

    public String decode(String encodedPassword) {
        if (encodedPassword == null || encodedPassword.isEmpty()) {
            return null;
        }
        byte[] bytes = hexConverter.fromHex(encodedPassword);
        return new String(bytes);
    }

    public boolean isValidHex(String encodedPassword) {
        return encodedPassword != null &&
                encodedPassword.matches("^[0-9A-Fa-f]+$") &&
                encodedPassword.length() % 2 == 0;
    }
}