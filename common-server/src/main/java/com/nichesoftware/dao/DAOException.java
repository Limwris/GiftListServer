package com.nichesoftware.dao;

/**
 * Created by n_che on 08/09/2016.
 */
public class DAOException extends Exception {

    // Fields   --------------------------------------------------------------------------------------------------------
    public static final int GENERIC_ERROR = 100;
    public static final int GENERIC_SAVE_FAILED_ERROR = 101;
    public static final int GENERIC_UPDATE_FAILED_ERROR = 102;
    public static final int KEY_ALREADY_EXISTS = 200;
    public static final int BAD_DATABASE_NAME = 300;
    public static final int BAD_TABLE_NAME = 301;
    public static final int BAD_COLUMN_NAME = 302;
    public static final int BAD_COLUMN_TYPE = 303;
    public static final int BAD_COLUMN_OUT_OF_BOUND = 404;
    public static final int BAD_FOREYGN_KEY = 400;
    public static final int GENERIC_ERROR_CODE = 999;

    /**
     * Code de l'erreur
     */
    private int code;
    private int sqlErrorCode;
    private String sqlState;

    // Constructors   --------------------------------------------------------------------------------------------------
    public DAOException(int code) {
        this.code = code;
    }

    // Getters   -------------------------------------------------------------------------------------------------------
    /**
     * Getter sur le code de l'erreur
     * @return code
     */
    public int getCode() {
        return code;
    }

    public int getSqlErrorCode() {
        return sqlErrorCode;
    }

    public void setSqlState(String sqlState) {
        this.sqlState = sqlState;
    }

    // Setters   -------------------------------------------------------------------------------------------------------
    /**
     * Setter sur le code de l'erreur
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    public void setSqlErrorCode(int sqlErrorCode) {
        this.sqlErrorCode = sqlErrorCode;
    }

    public String getSqlState() {
        return sqlState;
    }
}
