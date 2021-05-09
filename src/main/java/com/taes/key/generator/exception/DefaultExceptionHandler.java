package com.taes.key.generator.exception;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;

@Log4j2
@ControllerAdvice
public class DefaultExceptionHandler
{
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ExceptionResponse> DatabaseExceptionHandler(DataAccessException e)
    {
        String uuid = UUID.randomUUID().toString();
        log.error("{}/{}", uuid, ExceptionUtils.getStackTrace(e));

        // Database exception message는 외부로 노출시키지 않습니다.
        return new ResponseEntity<>(
            new ExceptionResponse(ApiErrorCode.DATABASE_GENERIC_ERROR, uuid, HttpStatus.INTERNAL_SERVER_ERROR.value())
            , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(ExceptionUtils.getStackTrace(e));

        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        return new ResponseEntity<>(
            new ExceptionResponse(ApiErrorCode.BAD_REQUEST_PARAMETER_ERROR, message, HttpStatus.BAD_REQUEST.value())
            , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> ApiExceptionHandler(ApiException e)
    {
        log.error(ExceptionUtils.getStackTrace(e));

        return new ResponseEntity<>(
            new ExceptionResponse(e.getErrorCode(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value())
            , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> ExceptionHandler(Exception e)
    {
        log.error(ExceptionUtils.getStackTrace(e));

        return new ResponseEntity<>(
            new ExceptionResponse(ApiErrorCode.GENERIC_ERROR, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value())
            , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
