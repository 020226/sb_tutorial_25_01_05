package com.sbs.tutorial1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
// 이 클래스는 컨트롤러이고 웹 요청을 받아서 작업할 것임
public class HomeController {
  int num; // 인스턴스 변수 만들고 값은 넣지 않는다

  public HomeController() { // 객체 만들지 않아도 @Controller가 객체 알아서 만들어줌 = 컨테이너를 통해
    num = -1; // 인스턴스 변수에 값을 할당하기 위해 생성사 메서드
  }


  @GetMapping("/home/main") // GET은 어떤 요청의 응답을 가져옴.
  // `/home/main`이라는 요청이 들어오면 아래 메서드 실행시켜줘
  @ResponseBody // 화면에 내가 작성한 내용을 보여줘. html 바디에 응답을 해준다
  // 아래 메서드를 실행한 후 리턴값을 응답으로 삼아서
  // body에 출력해줘
  public String showHome() {
    return "안녕하세요.";
  }

  @GetMapping("/home/increase")
  @ResponseBody
  // 스프링부터에서 응답한 결과를 body에 보내줄 때는 모든 것이 문자열!
  // 웹 브라우저는 무조건 String만 이해한다 = 정수를 넘겨도 스트링으로 이해한다
  public int increase() {
    // int num = -1; // num이 지역변수라 실행할 때마다 초기화되고 다시 -1
    num++;
    return num;
  }

  // `/home/plus?a=1&b=2` 쿼리 파라미터, 쿼리 스트링
  // 매개변수명과 쿼리 파라미터는 동일한 이름으로 사용해야 한다
  @GetMapping("/home/plus")
  @ResponseBody
  public int plus(@RequestParam(defaultValue = "0") int a, int b) { // 매개변수를 써주면 스프링부트가 알아서 파라미터로 인식
    // rq.getIntParam("a", 0); <- 스프링부트는 @RequestParam으로 해결, defaultValue는 무조건 문자열
    // 현재 a만 defaultValue가 있고 b는 없는 상태. b는 생략 불가능
    return a + b; // 문자열만 인식하는 스프링부트이지만 받아온 a,b를 알아서 정수로 인식
  }

  // 웹 브라우저가 이해하는 언어는 오직 자바스크립트
  // 스프링부트가 웹브라우저에 데이터를 넘겨줄 때 JSON을 이용함
  // 다양한 리턴타입
  @GetMapping("/home/returnBoolean")
  @ResponseBody
  public boolean showReturnBoolean() {
    return true;
  }

  @GetMapping("/home/returnDouble")
  @ResponseBody
  public Double showReturnDouble() {
    return Math.PI;
  }

  @GetMapping("/home/returnArray")
  @ResponseBody
  public int[] showReturnArray() {
    int[] arr = new int[]{10, 20, 30};
    return arr; // 배열의 주소가 아닌 @ResponseBody를 통해 toString 형태로 보여줌
  }

  @GetMapping("/home/returnIntList")
  @ResponseBody
  public List<Integer> showReturnIntList() {
    List<Integer> list = new ArrayList<>(){{
      add(10);
      add(20);
      add(30);
    }};
    /*
    List<Integer> list = new ArrayList<>();
    list.add(10);
    list.add(20);
    list.add(30);
    */
    return list;
  }

  // list와 map의 차이: 출력형태 [], {}
  // 자바스크립트에서 {}로 객체 표현. key, value
  @GetMapping("/home/returnMap")
  @ResponseBody
  public Map<String, Object> showReturnMap() {
    Map<String, Object> map = new LinkedHashMap<>() {{
      put("id", 1);
      put("subject", "제목1");
      put("content", "내용1");
    }};
    return map;
  }

}