package com.sparta.spangeats.domain.menu.exception;

public class InvalidMenuAccessException extends CustomException {

  public InvalidMenuAccessException() {
    super("메뉴 접근 권한이 없습니다.");
  }

  public InvalidMenuAccessException(String message) {
    super(message);
  }
}
