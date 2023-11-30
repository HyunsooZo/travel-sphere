package com.travelsphere.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class AsyncUncaughtExceptionHandlerCustom implements AsyncUncaughtExceptionHandler {

    /**
     * 비동기 작업 중 발생한 예외 처리
     *
     * @param ex     발생한 예외
     * @param method 예외가 발생한 메서드
     * @param params 메서드의 파라미터 값들
     */
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        log.error("Async Exception - " + ex.getMessage());
        log.error("Async Method - " + method.getName());
        Arrays.stream(params).forEach(param -> log.error("Parameter value - " + param));
        throw new RuntimeException(ex.getMessage());
    }
}

