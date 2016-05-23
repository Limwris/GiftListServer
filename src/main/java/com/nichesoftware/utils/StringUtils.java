package com.nichesoftware.utils;

/**
 * Created by Kattleya on 22/05/2016.
 */
public final class StringUtils {
    private StringUtils() {
        // Nothing
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.length() == 0;
    }
}
