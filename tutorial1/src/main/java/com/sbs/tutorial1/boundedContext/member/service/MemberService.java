package com.sbs.tutorial1.boundedContext.member.service;

import com.sbs.tutorial1.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

// @Component // 아래 클래스는 Ioc 컨테이너에 의해 생사소멸이 관리된다.
@Service
// Service에서 처리하는 게 아니라 Repository에서 처리해야 함
public class MemberService {
  public MemberRepository memberRepository;

  public MemberService() {
    memberRepository = new MemberRepository();
  }
  public RsData tryLogin(String username, String password) {
    // 서비스 입장에서 리포지터리에 username 주면 찾아서 서비스로 보내줘
    Member member = memberRepository.findByUserName(username);

    if (member == null) {
      return RsData.of("F-2", "%s(은)는 존재하지 않는 회원입니다.".formatted(username));
    }

    if(!member.getPassword().equals(password)){
      return RsData.of("F-1", "비밀번호가 일치하지 않습니다");

    }


    if (!password.equals("1234")) {
    }
    else if(!username.equals("user1")){
    }
    // 전에는 resultCode, msg만 넘겼는데 수정하여 member.getId()를 같이 넘기고 있다
    return RsData.of("S-1", "%s님 환영합니다.".formatted(username), member); // rq도입으로 member.getId() -> mebmer

  }

  public Member findByUserName(String username) {
    return memberRepository.findByUserName(username); // 리포지터리에 username 달라고 요청
  }

  public Member findById(long id) {
    return memberRepository.findById(id); // 리포지터리에 id 달라고 요청
  }
}
