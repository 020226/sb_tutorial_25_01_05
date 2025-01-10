package com.sbs.tutorial1.boundedContext.member.controller;

import com.sbs.tutorial1.base.rs.Rq;
import com.sbs.tutorial1.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.dto.Member;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// controller 만들면 제일 먼제 @Controller 붙이기
// controller는 판단을 하지 않고 Service에 보내주는 역할
// controller는 유효성 검사를 해주는 역할
@Controller
@AllArgsConstructor
public class MemberController {
  private final MemberService memberService; // 생성자 주입 시 final 필수
  private final Rq rq; // Rq rq = new Rq(req, resp); 를 지우고도 사용하는 방법

  /* @AllArgsConstructor을 사용하면 생성자주입도 생략 가능
  // 생성자 주입: 생상자를 만들어두고 생성자를 주입시키고 있음
  // 생성자 주입의 경우 @Autowired 생략 가능
  public MemberController(MemberService memberService) {
    // 만들어진 멤버서비스를 연결만 시켜주면 됨. 만드는 것은 Ioc컨테이너에 의해.
    this.memberService = memberService;
  }
   */

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

    RsData rsData = memberService.tryLogin(username, password);

    if (rsData.isSuccess()) { // S-로 시작하면 쿠키를 발행하도록. 쿠키 발행에는 HttpServletResponse resp 필요
      Member member = (Member) rsData.getData();
      // long memberId = (long) rsData.getData(); // data는 Object이므로 형변환
      // resp.addCookie(new Cookie("loginedMemberId", memberId + "")); // 쿠키 발행
      // rq.setCookie("loginedMemberId", member.getId());
      rq.setSession("loginedMemberId", member.getId());
    }

    if (rsData.isFail()) {
      return RsData.of("F-5", "실패 테스트");
    }

    return rsData;

    // RsData를 클래스로 만들면 매번 객체 만들어 of메서드 호출하는 것 대신
    // of메서드를 static으로 만들어서 더 편하게
    // return RsData.of("S-1", "%s님 환영합니다.".formatted(username));
  }

  @GetMapping("/member/logout")
  @ResponseBody
  public RsData logout() {

    // 제거가 되면 true
    // removeCookie 대신 removeSession
    boolean cookieRemoved = rq.removeSession("loginedMemberId");

    if(!cookieRemoved){
      return RsData.of("F-1", "이미 로그아웃 상태입니다.");
    }
    return RsData.of("S-1", "로그아웃 되었습니다.");
  }

    // 마이페이지 구현
  @GetMapping("/member/me")
  @ResponseBody
  public RsData showMe() {


    // loginedMemberId를 가져와야 됨
    // "loginedMemberId"를 주면 가져오고 없으면 defaultValue 0
    long loginedMemberId = rq.getSessionAsLong("loginedMemberId", 0);

    boolean isLogined = loginedMemberId > 0;

    if(!isLogined) {
      return RsData.of("F-1", "로그인 후 이용해주세요.");
    }

    Member member = memberService.findById(loginedMemberId);

    return RsData.of("S-1", "당신의 username(은)는 %s 입니다.".formatted(member.getUsername()));
  }
}
