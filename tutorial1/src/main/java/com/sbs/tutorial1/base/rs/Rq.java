package com.sbs.tutorial1.base.rs;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
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
  private String getSessionAsStr(String name, String defaultValue) {
    try {
      //  session에서 name을 가져와서 문자화
      String value = (String) req.getSession().getAttribute(name); // name으로 접근한 session 데이터 가져옴
      // 형변환 하는 이유는 getAttribute 값이 Object 이기 때문

      if(value == null) return defaultValue;

      return value;
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public long getSessionAsLong(String name, long defaultValue) {
    try {
      long value = (long) req.getSession().getAttribute(name);

      return value;
    } catch (Exception e) {
      return defaultValue;
    }

  }



  public void setSession(String name, long value) {
    HttpSession session = req.getSession();
    session.setAttribute(name, value);
  }

  public boolean removeSession(String name) {
    // session 객체를 가져오기 위해 외워야 함
    HttpSession session = req.getSession();

    // 세션을 가져왔는데 없으면 삭제를 못 했다는 의미
    if(session.getAttribute(name) == null) return false; // name은 로그인 id.
    session.removeAttribute(name);
    return true;

  }

  // 로그인이 되었는지 확인
  public boolean isLogined() {
    // 세션에서 loginedMemberId으로 된 것이 있는지 확인
    long loginedMemberId = getSessionAsLong("loginedMemberId", 0);
    return loginedMemberId > 0;
  }

  public boolean isLogout() {
    return !isLogined();
  }

  public long getLoginedMember() {
    return getSessionAsLong("loginedMemberId", 0); // member를 가져오기 위함
  }
}
