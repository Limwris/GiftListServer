package com.nichesoftware.utils;

import org.springframework.http.HttpStatus;

/**
 * Created by n_che on 13/09/2016.
 */
public final class RestUtils {
    // Constructors   --------------------------------------------------------------------------------------------------
    /**
     * Constructeur défini privé
     */
    private RestUtils() {
        // Nothing
    }

    // Methods   -------------------------------------------------------------------------------------------------------
    public static boolean isError(HttpStatus status) {
        HttpStatus.Series series = status.series();
        return (HttpStatus.Series.CLIENT_ERROR.equals(series)
                || HttpStatus.Series.SERVER_ERROR.equals(series));
    }
}
