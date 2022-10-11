package com.thunv.ecommerceou.controller;

import com.thunv.ecommerceou.res.ModelResponse;
import com.thunv.ecommerceou.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ValidatorHandleController extends ResponseEntityExceptionHandler {
    @Autowired
    private Utils utils;
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = this.utils.getAllErrorValidation(ex.getBindingResult());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ModelResponse("400","Invalid information",errors)
        );
    }
}
