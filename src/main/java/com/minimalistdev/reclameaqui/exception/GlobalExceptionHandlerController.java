package com.minimalistdev.reclameaqui.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(LocaleNotFoundException.class)
  public void handleLocaleNotFoundException(HttpServletResponse res, LocaleNotFoundException ex) throws IOException {
    res.sendError(ex.getHttpStatus().value(), ex.getMessage());
  }

  @ExceptionHandler(InvalidParameterException.class)
  public void invalidParameterException(HttpServletResponse res, InvalidParameterException ex) throws IOException {
    res.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
  }

  @ExceptionHandler(DuplicateKeyException.class)
  public void duplicateKeyExceptionException(HttpServletResponse res, DuplicateKeyException ex) throws IOException {
    res.sendError(HttpStatus.BAD_REQUEST.value(), "This resource can't be create because already exists");
  }

  @ExceptionHandler(Exception.class)
  public void handleException(HttpServletResponse res) throws IOException {
    res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
  }

}
