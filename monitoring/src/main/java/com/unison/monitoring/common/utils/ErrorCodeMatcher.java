package com.unison.monitoring.common.utils;

import com.unison.monitoring.common.exception.ErrorCode;
import org.springframework.security.authentication.BadCredentialsException;

public class ErrorCodeMatcher {
    public static ErrorCode getErrorCode(Exception exception){
        ErrorCode errorCode = null;

        //Security Exception
        if(errorCode == null){
            errorCode = getSecurityErrorCode(exception);
        }
        else {
            errorCode = ErrorCode.DEFAULT;
        }
        return errorCode;
    }

    private static ErrorCode getSecurityErrorCode(Exception exception){
        ErrorCode errorCode = null;

        //Security Exception
        if(exception instanceof BadCredentialsException) {
            errorCode = ErrorCode.BAD_CREDENTIALS;
        }

        return errorCode;
    }
}
