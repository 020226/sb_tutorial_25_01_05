package com.sbs.tutorial1.base.rs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.util.Arrays;

@AllArgsConstructor
public class Rq {
  private final HttpServletRequest req;
  private final HttpServletResponse resp;

  // setCookie를 두 개 만드는 이유: 쿠키값이 문자열/정수 둘다 들어올 수 있기 때문
  public void setCookie(String name, long value) {
    setCookie(name, value+""); // id값이 long타입이면 실행됨
  }

  public void setCookie(String name, String value) {
    resp.addCookie(new Cookie(name, value));
  }

  public boolean removeCookie(String name) {
    Cookie cookie = Arrays.stream(req.getCookies())
        .filter(c -> c.getName().equals(name)) // 쿠키 이름이 같은 것을 가져와서
        .findFirst() // 찾았으면 cookie 객체와 연결이 되고
        .orElse(null);

    if (cookie != null) {
      cookie.setMaxAge(0); // 쿠키 만료 시킴
      resp.addCookie(cookie);

      return true; // 처리 했으면 true 넘겨줌
    }
    return false;
  }

  public long getCookieAsLong(String name, long defaultValue) {
    String value = getCookie(name, null);

    // 안 들어옴
    if(value == null) return defaultValue;

    // value값이 일치하지 않을 수 있기 때문에 try-catch로 감싸줌
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      return defaultValue;
      }
    }

  private String getCookie(String name, String defaultValue) {
    if(req.getCookies() == null) return defaultValue;

    return Arrays.stream(req.getCookies())
        .filter(cookie -> cookie.getName().equals(name)) // 쿠키 이름이 loginedMemberId인 것을 가져와서
        .map(Cookie::getValue) // .map(cookie -> cookie.getValue) 동일. cookie에서 값을 꺼내고
        .findFirst() // 그 중에서 첫 번째 가져와라
        .orElse(defaultValue);
  }
}
