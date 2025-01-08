package com.sbs.tutorial1.boundedContext.member.service;

import com.sbs.tutorial1.base.rsData.RsData;
import org.springframework.stereotype.Service;

@Service
// Service에서 처리하는 게 아니라 Repository에서 처리해야 함
public class MemberService {
  public RsData tryLogin(String username, String password) {
    if (!password.equals("1234")) {
      return RsData.of("F-1", "비밀번호가 일치하지 않습니다");
    }
    else if(!username.equals("user1")){
      return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
    }

    return RsData.of("S-1", "%s님 환영합니다.".formatted(username));

  }
}
