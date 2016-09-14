package com.nichesoftware.service.exception;

/**
 * Created by n_che on 13/09/2016.
 */
public class GenericException extends RuntimeException {
    // Fields   --------------------------------------------------------------------------------------------------------
    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Code de l'erreur
     */
    private int errorCode;
    /**
     * Message associé à l'erreur
     */
    private String errorMessage;

    // Constructor   ---------------------------------------------------------------------------------------------------
    /**
     * Constructeur par défaut
     */
    public GenericException() {
        super();
    }

    /**
     * Constructeur
     * @param e
     */
    public GenericException(Exception e) {
        super(e);
    }

    /**
     * Constructeur
     * @param message
     */
    public GenericException(String message) {
        super(message);
    }

    /**
     * Constructeur
     * @param e
     * @param message
     */
    public GenericException(String message, Exception e) {
        super(message, e);
    }

    /**
     * Constructeur avec code et message
     * @param code
     * @param message
     */
    public GenericException(int code, final String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter sur le code associé à l'erreur
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Getter sur le message associé à l'erreur
     * @return errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter sur le code associé à l'erreur
     * @param code
     */
    public void setErrorCode(int code) {
        this.errorCode = code;
    }

    /**
     * Setter sur le message associé à l'erreur
     * @param message
     */
    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }
}
