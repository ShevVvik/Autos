package com.shevvvik.autos.web.controllers;

import com.shevvvik.autos.configuration.exceptions.ObjectNotFound;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({ObjectNotFound.class, SQLException.class})
    public String databaseError(Model model, Exception exception) {
        if (exception instanceof ObjectNotFound) {
            model.addAttribute("errorMessage", exception.getMessage());
        }
        return "/errorPage";
    }
}