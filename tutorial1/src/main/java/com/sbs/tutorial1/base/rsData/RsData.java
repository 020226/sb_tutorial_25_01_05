package com.sbs.tutorial1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
  private final String resultCode; // final 붙으면
  private final String msg; // 생성자 메서드 필수
  private final Object data; // 성공실패 여부 + 로그인 데이터도 보내주기 위해

  // 데이터를 두 개 받으면 실행하여 아래로 오버로딩한 코드로 토스
  public static RsData of(String resultCode, String msg) {
    return new RsData(resultCode, msg, null); // 생성자 메서드 필요


  /*
  객체를 생성할 때 사용되는 메소드
  객체 생성 후 객체의 초기화를 하는 역할 수행
  - 객체를 생성함과 동시에 기억공간이 만들어졌으니 데이터를 저장할수있다.
  저장하는 행위를 초기화(initialize)라고 한다.
  */
  }

  // 메서드 오버로딩
  public static RsData of(String resultCode, String msg, Object data) {
    return new RsData(resultCode, msg, data);
  }

  public boolean isSuccess() {
    return resultCode.startsWith("S-"); // startsWith("S-"): S-로 시작하면
  }

  // Jackson 규칙 is~메서드 -> 출력 결과가 is 없이 Fail로 나옴
  public boolean isFail() {
    return resultCode.startsWith("F-"); // startsWith("F-"): S-로 시작하면
  }


}
