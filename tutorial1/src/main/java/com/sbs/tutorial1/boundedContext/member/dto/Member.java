package com.sbs.tutorial1.boundedContext.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Member {
  private static int lastId;
  private final long id; // final이 붙으면 비어있는 생성자 만들 수 없음(@No~)
  private final String username;
  private String password; // 비밀번호는 수정할 수 있기 때문에 final x

  static {
    lastId = 0;
  }

  // username과 password만 만들면 id 자동으로 증가되어 생성되는
  public Member(String username, String password) {
    this(++lastId, username, password);

  }

}
