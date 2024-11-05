package com.sparta.spangeats.domain.menu.exception;

// 메뉴 데이터가 유효하지 않을 때 발생하는 예외
public class InvalidMenuDataException extends CustomException {
    public InvalidMenuDataException() {
        super("메뉴 이름과 가격은 필수 입력 사항입니다.");
    }
}
