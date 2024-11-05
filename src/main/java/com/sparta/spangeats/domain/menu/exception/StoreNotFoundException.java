package com.sparta.spangeats.domain.menu.exception;

public class StoreNotFoundException extends CustomException{
    public StoreNotFoundException(Long message) {
        super("가게를 찾을 수 없습니다.");
    }
}
