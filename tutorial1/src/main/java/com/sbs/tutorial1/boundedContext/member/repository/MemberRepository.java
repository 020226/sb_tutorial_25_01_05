package com.sbs.tutorial1.boundedContext.member.repository;

import com.sbs.tutorial1.boundedContext.member.dto.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository // 컴포넌트와 같다
public class MemberRepository {
  List<Member> members;

  public MemberRepository() {
    members = new ArrayList<>();

    // 테스트데이터 생성
    members.add(new Member("user1", "1234"));
    members.add(new Member("user2", "1234"));
    members.add(new Member("user3", "1234"));
    members.add(new Member("user4", "1234"));
    members.add(new Member("user5", "1234"));


  }


  public Member findByUserName(String username) {
    return members.stream()
        .filter(member -> member.getUsername().equals(username))
        .findFirst()
        .orElse(null);
  }

  public Member findById(long id) {
    return members.stream() // 있으면 가져오고 없으면 null
        .filter(member -> member.getId() == id)
        .findFirst()
        .orElse(null);
  }
}
