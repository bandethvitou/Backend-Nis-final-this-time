package com.sarana_NIS.newTown_backend.security.encoding;

import org.springframework.stereotype.Component;

@Component
public class HexBinaryConverter {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public String toHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public byte[] fromHex(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public boolean isValidHex(String hex) {
        return hex != null &&
                hex.matches("^[0-9A-Fa-f]+$") &&
                hex.length() % 2 == 0;
    }
}