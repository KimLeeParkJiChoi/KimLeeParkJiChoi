package com.sparta.spangeats.domain.menu.exception;

// 해당 가게의 메뉴에 접근할 수 없을 때 발생하는 예외
public class InvalidMenuAccessException extends CustomException {
  public InvalidMenuAccessException() {
    super("이 메뉴는 현재 선택한 가게에 속하지 않으므로 접근할 수 없습니다.");
  }
}
