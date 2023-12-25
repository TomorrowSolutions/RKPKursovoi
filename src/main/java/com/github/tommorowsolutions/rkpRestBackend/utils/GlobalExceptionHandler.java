package com.github.tommorowsolutions.rkpRestBackend.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler{
    Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());
    @ExceptionHandler(Exception.class)
    public ModelAndView handleApplicationException(Exception ex) {
        logger.info(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("Error");
        modelAndView.addObject("message", ex.getMessage());
        return modelAndView;
    }

}
