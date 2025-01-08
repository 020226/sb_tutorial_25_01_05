package com.sbs.tutorial1.base.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData {
  private final String resultCode; // final 붙으면
  private final String msg; // 생성자 메서드 필수

  public static RsData of(String resultCode, String msg) {
    return new RsData(resultCode, msg); // 생성자 메서드 필요
  /*
  객체를 생성할 때 사용되는 메소드
  객체 생성 후 객체의 초기화를 하는 역할 수행
  - 객체를 생성함과 동시에 기억공간이 만들어졌으니 데이터를 저장할수있다.
  저장하는 행위를 초기화(initialize)라고 한다.
  */
  }
}
