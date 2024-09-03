package com.kenny.exceptionhandler;

/* 사용자 정의 Exception 클래스 */
public class MemberRegistException extends Exception {  // Throwable <- Exception <- RuntimeException, IOException

    // 에러 상황에 대한 메세지를 취급하기 위함. 생성자를 통해 메세지 전달
    public MemberRegistException(String message) {
        super(message);
    }
}
