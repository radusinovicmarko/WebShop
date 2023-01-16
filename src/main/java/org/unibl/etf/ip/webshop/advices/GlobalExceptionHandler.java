package org.unibl.etf.ip.webshop.advices;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.unibl.etf.ip.webshop.exceptions.HttpException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HandlerMethod handlerMethod) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpException.class)
    public final ResponseEntity<Object> handleHttpException(HttpException exception, HandlerMethod method) {
        getLogger(method).error(getMessage(exception));
        if (exception.getStatus() == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(exception.getData(), exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception exception, HandlerMethod method) {
        getLogger(method).error(getMessage(exception));
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Logger getLogger(HandlerMethod handlerMethod) {
        return Logger.getLogger(handlerMethod.getMethod().getDeclaringClass());
    }

    private String getMessage(Throwable throwable) {
        StringBuilder builder = new StringBuilder();
        builder.append(throwable);
        builder.append(System.lineSeparator());
        Arrays.stream(throwable.getStackTrace()).limit(1).forEach(l -> {
            builder.append(l);
            builder.append(System.lineSeparator());
        });
        return builder.toString();
    }
}
