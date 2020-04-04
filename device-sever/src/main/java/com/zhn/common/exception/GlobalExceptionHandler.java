package com.zhn.common.exception;

import com.zhn.common.bean.ResponseVO;
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
    public ResponseVO handleIllegalStateException(HttpServletRequest request, IllegalStateException ex) {
        ex.printStackTrace();
        return new ResponseVO(false, ex.getLocalizedMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseVO exceptionHandle(HttpServletRequest request, Exception e) {
        e.printStackTrace();
        return new ResponseVO(false, "系统错误");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        String message = null;
        if (ex.getBindingResult() != null) {
            message = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            message = ex.getLocalizedMessage();
        }
        return new ResponseVO(false, message);
    }
    // 未登录错误
    @ExceptionHandler(NotLoginException.class)
    public ResponseVO handleNotLoginException(HttpServletRequest request, NotLoginException ex) {
        ex.printStackTrace();
        return new ResponseVO(false, ex.getMessage());
    }
    // 请求无权限
    @ExceptionHandler(ForbiddenException.class)
    public ResponseVO handleForbiddenException(HttpServletRequest request, ForbiddenException ex) {
        ex.printStackTrace();
        return new ResponseVO(false, ex.getMessage());
    }
    // 业务错误
    @ExceptionHandler(BusinessException.class)
    public ResponseVO handleBusinessException(HttpServletRequest request, BusinessException ex) {
        ex.printStackTrace();
        return new ResponseVO(false, ex.getLocalizedMessage());
    }
    // 参数校验错误
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseVO handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException ex) {
        ex.printStackTrace();
        String message = null;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (constraintViolations != null && constraintViolations.iterator().hasNext()) {
            message = constraintViolations.iterator().next().getMessage();
        } else {
            message = "系统错误";
        }

        return new ResponseVO(false, message);
    }
    @ExceptionHandler(BindException.class)
    public ResponseVO handleBindException(HttpServletRequest request, BindException ex) {
        ex.printStackTrace();
        String message = null;
        if (ex.getBindingResult() != null) {
            message = ex.getBindingResult().getFieldError().getDefaultMessage();
        } else {
            message = ex.getLocalizedMessage();
        }
        return new ResponseVO(false, message);
    }
}
