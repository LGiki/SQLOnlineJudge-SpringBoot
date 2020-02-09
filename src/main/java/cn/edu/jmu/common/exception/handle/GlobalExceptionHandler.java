package cn.edu.jmu.common.exception.handle;

import cn.edu.jmu.common.exception.BadRequestException;
import cn.edu.jmu.common.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountExpiredException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.PAYMENT_REQUIRED;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.valueOf;

/**
 * @author sgh
 * @date 2019/8/21 下午3:45
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(BAD_REQUEST.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 用户不存在异常
     */
    @ExceptionHandler(UnknownAccountException.class)
    public ResponseEntity<ApiError> unknownAccountException(UnknownAccountException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(UNAUTHORIZED.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 无权限访问异常
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiError> unauthorizedException(UnauthorizedException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(FORBIDDEN.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 用户密码错误
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<ApiError> incorrectCredentialsException(IncorrectCredentialsException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(UNAUTHORIZED.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 验证错误异常
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> authenticationException(AuthenticationException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(UNAUTHORIZED.value(), "登录失败");
        return buildResponseEntity(apiError);
    }

    /**
     * 处理 接口无权访问异常AccessDeniedException
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(FORBIDDEN.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理登录失败异常AccountExpiredException
     */
    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity handleAccessAccountExpiredException(AccountExpiredException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(PAYMENT_REQUIRED.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> badRequestException(BadRequestException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(e.getStatus(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        ApiError apiError = new ApiError(BAD_REQUEST.value(), str[1] + ":" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, valueOf(apiError.getStatus()));
    }
}
