package com.bookwormshop.productservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class JsonLoader {
    private static final String TEST_JSON_BASE_PATH = "/requests";

    /**
     * Loads json file from given path at {@code requests} in test resources
     * @param jsonPath the path of json file
     * @return content of the given json file as String
     * @throws IOException exception
     */
    public static String loadTestJson(String jsonPath) throws IOException {
        try (InputStream inputStream = JsonLoader.class.getResourceAsStream(TEST_JSON_BASE_PATH + jsonPath)) {
            byte[] stringBytes = Objects.requireNonNull(inputStream).readAllBytes();

            return new String(stringBytes);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Test file not found", e);
        }
    }
}
