package com.foresys.api.system.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception e) {
        Map<String, Object> result = new HashMap<String, Object>();
      
        if(e instanceof ResponseStatusException) {
        	ResponseStatusException rse = (ResponseStatusException) e;
        	result.put("httpStatus", rse.getRawStatusCode());
        	result.put("message", rse.getMessage());
        	
        	return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
        }
        
        result.put("message", e.getMessage());
        result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    @AllArgsConstructor
    class ServerMessage {
        private String message = "success";
        private HttpStatus httpStatus;
        @Override
        public String toString() {
            return message;
        }

    }
}