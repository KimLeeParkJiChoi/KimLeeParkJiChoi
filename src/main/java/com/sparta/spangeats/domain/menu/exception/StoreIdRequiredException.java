package com.sparta.spangeats.domain.menu.exception;

// 가게 ID가 필요할 때 발생하는 예외
public class StoreIdRequiredException extends CustomException {
    public StoreIdRequiredException() {
        super("가게 ID는 필수입니다.");
    }
}
