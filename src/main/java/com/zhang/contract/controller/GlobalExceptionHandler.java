package com.zhang.contract.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Map<String, Object> handleException(HttpServletResponse response, Exception exception) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("error", exception.getMessage());
        return map;
    }
}
