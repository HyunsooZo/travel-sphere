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
    UNDEFINED_EXCEPTION(BAD_REQUEST, "알 수 없는 오류가 발생하였습니다."),

    //user
    USER_INFO_NOT_FOUND(NOT_FOUND, "존재하지 않는 사용자입니다."),
    EXISTING_USER(BAD_REQUEST, "이미 존재하는 사용자입니다."),
    WRONG_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    USER_STATUS_NOT_ACTIVE(BAD_REQUEST, "사용자 상태가 활성화되어 있지 않습니다."),
    WEATHER_NOT_FOUND(NOT_FOUND, "지원하지 않는 도시입니다. 도시이름을 확인해주세요."),

    //expense
    EXPENSE_NOT_FOUND(NOT_FOUND, "존재하지 않는 지출입니다."),
    NOT_MY_EXPENSE(BAD_REQUEST, "본인의 지출만 수정/삭제할 수 있습니다.");

    private final HttpStatus status;
    private final String message;

    }
