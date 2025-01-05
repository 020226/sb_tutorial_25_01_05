package com.sbs.tutorial1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
// 이 클래스는 컨트롤러이고 웹 요청을 받아서 작업할 것임
public class HomeController {
  @GetMapping("/home/main") // GET은 어떤 요청의 응답을 가져옴.
  // /home/main이라는 요청이 들어오면 아래 메서드 실행시켜줘
  @ResponseBody // 화면에 내가 작성한 내용을 보여줘. html 바디에 응답을 해준다
  // 아래 메서드를 실행한 후 리턴값을 응답으로 삼아서
  // body에 출력해줘
  public String showHome() {
    return "안녕하세요.";
  }
}
