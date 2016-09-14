package com.nichesoftware.utils;

/**
 * Classe de manipulation de String
 */
public final class StringUtils {
    /**
     * Constructeur défini privé
     */
    private StringUtils() {
        // Nothing
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }
}
