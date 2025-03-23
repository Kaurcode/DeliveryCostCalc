package com.fujitsu.deliverycostcalc.exceptionhandling;

import com.fujitsu.deliverycostcalc.exception.EmptyXmlTagValueException;
import com.fujitsu.deliverycostcalc.exception.InvalidMoneyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(EmptyXmlTagValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleEmptyXmlTag(EmptyXmlTagValueException ex) {
        return "Weather data error: " + ex.getMessage();
    }

    @ExceptionHandler(InvalidMoneyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleInvalidMoney(InvalidMoneyException ex) {
        return "Invalid money format: " + ex.getMessage();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNoSuchElement(NoSuchElementException ex) {
        return "Resource not found.";
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseBody
    public String handleCustomStatus(ResponseStatusException ex) {
        return ex.getReason();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleGeneric(Exception ex) {
        return "Unexpected error: " + ex.getMessage();
    }
}
