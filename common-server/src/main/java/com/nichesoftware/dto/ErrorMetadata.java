package com.nichesoftware.dto;

/**
 * Created by n_che on 13/09/2016.
 */
public class ErrorMetadata extends Metadata {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Code de l'erreur
     */
    private int code;
    /**
     * Message associé à l'erreur
     */
    private String message;

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter sur le code associé à l'erreur
     * @return errorCode
     */
    public int getCode() {
        return code;
    }

    /**
     * Getter sur le message associé à l'erreur
     * @return errorMessage
     */
    public String getMessage() {
        return message;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter sur le code associé à l'erreur
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Setter sur le message associé à l'erreur
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
