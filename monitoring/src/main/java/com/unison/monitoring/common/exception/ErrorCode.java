package com.unison.monitoring.common.exception;


/**
 * E001~099 - Request or Response error
 * E101~199 - Authenticate and Authorization erorr
 * E201~299 - Server internal error
 */
public enum ErrorCode {
    //E000 - default error
    DEFAULT("E000", "Unknown error"),

    /*E001 ~ E099*/

    /*E101 ~ E199*/
    USERNAME_NOT_FOUND("E101", "User not found"),
    BAD_CREDENTIALS("E102", "Bad credentials"),
    AUTHENTICATION_ENTRY_POINT("E103", "Failed Authentication"),

    /*E201 ~ E299*/
    INTERNAL_SERVER_ERROR("E203", "Internal server error");

    private final String code;
    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
