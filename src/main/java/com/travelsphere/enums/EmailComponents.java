package com.travelsphere.enums;

public class EmailComponents {
    public static final String EMAIL_SUBJECT =
            "Travelsphere 회원가입 인증 메일";
    public static final String EMAIL_CONTENT =
            "아래 링크를 클릭하시면 회원가입 인증이 완료됩니다.\n" +
                    "http://localhost:8080/api/v1/users/%d/verification";
}
