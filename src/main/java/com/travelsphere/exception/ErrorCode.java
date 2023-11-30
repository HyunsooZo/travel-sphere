package com.travelsphere.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    //undefined
    UNDEFINED_EXCEPTION(BAD_REQUEST,"알 수 없는 오류가 발생하였습니다."),

    //user
    USER_INFO_NOT_FOUND(NOT_FOUND, "존재하지 않는 사용자입니다.");

    private final HttpStatus status;
    private final String message;

}
