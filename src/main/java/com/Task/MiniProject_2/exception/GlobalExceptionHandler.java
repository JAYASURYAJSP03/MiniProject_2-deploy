package com.Task.MiniProject_2.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppointmentNotFoundException.class)
    public String handleAppointmentNotFound(AppointmentNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // Return error page
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // Return error page
    }
}
