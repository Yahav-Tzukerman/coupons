package com.yahav.coupons.exceptions;

import com.yahav.coupons.enums.ErrorType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class ServerExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ServerExceptionResponse handleException(Throwable throwable, HttpServletResponse response) {

        if (throwable instanceof ServerException) {

            ServerException serverException = (ServerException) throwable;

            ErrorType errorType = serverException.getErrorType();
            int errorNumber = errorType.getErrorNumber();
            String errorMessage = errorType.getErrorMessage();
            String formattedTimeStamp = getTimestamp();
            response.setStatus(errorType.getHtmlErrorCode());

            ServerExceptionResponse error = new ServerExceptionResponse(errorNumber, errorMessage, formattedTimeStamp);
            if (errorType.isShowStackTrace()) {
                serverException.printStackTrace();
            }
            return error;
        }

        ErrorType errorType = ErrorType.GENERAL_ERROR;
        int errorNumber = errorType.getErrorNumber();
        String errorMessage = errorType.getErrorMessage();
        String formattedTimeStamp = getTimestamp();
        response.setStatus(errorType.getHtmlErrorCode());
        throwable.printStackTrace();

        return new ServerExceptionResponse(errorNumber, errorMessage, formattedTimeStamp);

    }

    private String getTimestamp() {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return timestamp.format(format);
    }
}
