package com.medicinalManage.exception;

import com.medicinalManage.common.ResponseEntity;
import com.medicinalManage.common.ResponseStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity handleIllegalStateException(HttpServletRequest request, IllegalStateException ex) {
        ex.printStackTrace();
        return new ResponseEntity(ResponseStatus.ERROR.getStatus(), ex.getLocalizedMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity exceptionHandle(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return new ResponseEntity(ResponseStatus.ERROR.getStatus(), "系统错误");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        String message = null;
        if (ex.getBindingResult() != null) {
            message = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            message = ex.getLocalizedMessage();
        }
        return new ResponseEntity(ResponseStatus.ERROR.getStatus(), message);
    }
    // 业务错误
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity handleBusinessException(HttpServletRequest request, BusinessException ex) {
        ex.printStackTrace();
        return new ResponseEntity(ResponseStatus.FALSE.getStatus(), ex.getLocalizedMessage());
    }
    // 参数校验错误
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        ex.printStackTrace();
        String message = null;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (constraintViolations != null && constraintViolations.iterator().hasNext()) {
            message = constraintViolations.iterator().next().getMessage();
        } else {
            message = "系统错误";
        }

        return new ResponseEntity(ResponseStatus.FALSE.getStatus(), message);
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity handleBindException(HttpServletRequest request, BindException ex) {
        ex.printStackTrace();
        String message = null;
        if (ex.getBindingResult() != null) {
            message = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            message = ex.getLocalizedMessage();
        }
        return new ResponseEntity(ResponseStatus.FALSE.getStatus(), message);
    }
}
