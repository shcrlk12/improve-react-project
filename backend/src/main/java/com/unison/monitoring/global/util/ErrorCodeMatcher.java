package com.unison.monitoring.global.util;

import com.unison.monitoring.global.error.ErrorCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

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
