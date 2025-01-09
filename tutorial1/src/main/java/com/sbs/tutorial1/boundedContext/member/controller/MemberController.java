package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// controller 만들면 제일 먼제 @Controller 붙이기
// controller는 판단을 하지 않고 Service에 보내주는 역할
// controller는 유효성 검사를 해주는 역할
@Controller
public class MemberController {
  private final MemberService memberService;

  public MemberController() {
    memberService = new MemberService();
  }

  @GetMapping("/member/login")
  @ResponseBody
  public RsData login(String username, String password) {
    /*Map<String, Object> rsData = new LinkedHashMap<>(){{
      put("ResultCode", "S-1");
      put("msg", "%s님 환영합니다.".formatted(username));
    }};
    이 코드 대신 아래 코드로 대체
    */

    if(username == null || username.trim().isEmpty()){
      return RsData.of("F-3", "username(을)를 입력해주세요.");
    }

    if(password == null || password.trim().isEmpty()){
      return RsData.of("F-4", "password(을)를 입력해주세요.");
    }

    return memberService.tryLogin(username, password);

    // RsData를 클래스로 만들면 매번 객체 만들어 of메서드 호출하는 것 대신
    // of메서드를 static으로 만들어서 더 편하게
    // return RsData.of("S-1", "%s님 환영합니다.".formatted(username));
  }
}
