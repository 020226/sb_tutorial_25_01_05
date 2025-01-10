package com.sbs.tutorial1.boundedContext.home.controller;

import com.sbs.tutorial1.boundedContext.member.entity.Member;
import com.sbs.tutorial1.boundedContext.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller // 이 클래스는 컨트롤러이고 웹 요청을 받아서 작업한다
public class HomeController {
  int num;
  List<Person> people;

  // 필드 주입
  @Autowired // 알아서 MemberService 객체를 만들어 memberService와 연결시켜줌
  private MemberService memberService;

  public HomeController() {
    num = -1;
    people = new ArrayList<>();

  }

  @GetMapping("/home/user1")
  @ResponseBody
  public Member showUser1() {
    return memberService.findByUserName("user1");
  }

  @GetMapping("/home/main") // GET은 어떤 요청의 응답을 가져옴
  // `/home/main`이라는 요청이 들어오면 아래 메서드를 실행시켜줘
  @ResponseBody // 리턴값을 응답으로 받아서 html의 body에 뿌려
  public String showHome() {
    return "안녕";
  }

  @GetMapping("/home/increase")
  @ResponseBody
  public int increase() {
    num++;
    return num;
  }

  // /home/plus?a=1&b=2
  @GetMapping("/home/plus")
  @ResponseBody
  public int plus(@RequestParam(defaultValue = "0") int a, int b) {
    return a + b; // a,b를 형변환하지 않아도 되는 이유는 스프링부트가 알아서 받아온 문자열을 정수로 변환
  }

  @GetMapping("/home/article")
  @ResponseBody
  public Article showArticle() {
    Article article = new Article(1, "제목1", "내용1");
    return article;
  }

  // 요청 : http://localhost:8080/home/addPerson?name=홍길동&age=11
  // 응답 : 1번 사람이 추가되었습니다.
  @GetMapping("/home/addPerson")
  @ResponseBody
  public String addPerson(String name, int age) { // 응답결과가 문자열이기 때문에 String

    Person p = new Person(name, age); // id 필드가 있지만 넣지 않음. 실제값은 파라미터로 받기 때문에 넣지 않는다
    System.out.println("p " + p);
    people.add(p); // 리스트에 담아줌

    // 실제로 추가된 것이 아니라 메시지 출력된 결과일 뿐
    // 이 메서드 내에 리스트를 만들어 담으면 지역변수이므로 x
    return "%d번 사람이 추가되었습니다.".formatted(p.getId()); // Person 클래스 필드에 id를 만들어주었으므로 id 가져올 수 있음

  }

  @GetMapping("/home/showPeople")
  @ResponseBody
  public List<Person> showPeople() {
    return people;
  }

  // 쿠키 발행하기 실습
  @GetMapping("/home/cookie/increase")
  @ResponseBody
  public int showCookieIncrease(HttpServletRequest req, HttpServletResponse resp) throws IOException{
    // HttpServletRequest req: 받은 편지
    // HttpServletResponse resp: 보낼 편지
    /* void
    int age = Integer.parseInt(req.getParameter("age")); // ?를 기준으로 age값 넘겨주면 실제론 문자열인데 정수화하여 age에 넘겨줌
    resp.getWriter().append("Hello, you are %d years old".formatted(age)); // jsp에서 사용했던 요청 보내면 Hello 보내줌
    */

    // void: `/home/cookie/increase` 요청 날리면 쿠키가 key, value 형태로 발행된다
    // f12 눌러서 확인가능
    // resp.addCookie(new Cookie("age", "15"));

    // 쿠키를 가지고 브라우저를 구분하기 시작함
    int countInCookie = 0;
    if (req.getCookies() != null) { // null이 아니라는 것 = 쿠키가 이미 들어있다(쿠키가 발행되어 있음)
      countInCookie = Arrays.stream(req.getCookies())
          .filter(cookie -> cookie.getName().equals("count")) // 쿠키를 가져오는데 그 이름이 count
          .map(cookie -> cookie.getValue()) // 쿠키의 value를 가져와라
          .mapToInt(Integer::parseInt) // 가져올 때 형변환
          .findAny() // 찾아서 가져오고
          .orElse(0); // 없으면 0 반환
    }


    int newCountInCookie = countInCookie + 1;

    // 쿠키 발행, countInCookie + 1 + ""은 받은 쿠키+1을 문자열로 변환해준 것
    resp.addCookie(new Cookie("count", newCountInCookie + ""));


    // 쿠폰이 올 때마다 도장 하나씩 찍어주는 구조
    return newCountInCookie;
  }


  // 테스트 케이스 생성
  @GetMapping("/home/personTestcase")
  @ResponseBody
  public String personTestcase() {
    people.add(new Person("홍길동", 11));
    people.add(new Person("홍길순", 22));
    people.add(new Person("임꺽정", 33));

    return "테스트 케이스 추가";
  }

  // 요청 : http://localhost:8080/home/removePerson?id=2
  //응답 : 2번 사람이 삭제되었습니다.
  @GetMapping("/home/removePerson")
  @ResponseBody
  public String removePerson(int id) { // 파라미터로 id를 넘겨서 삭제

    /*
    Person target = null;

    // 반복문 돌려서 리스트 안에서 id에 맞는 객체 찾아서 삭제
    for(Person p : people) {
      if(p.getId() == id) {
        target = p;
        break;
      }
    }

    if(target == null) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }

    // System.out.println(target); // 객체 정보 잘 찾았나 확인

    people.remove(target);
     */

    // 스트림문법
    // person.getId() == id
    // 위 함수가 참인 요소가 존재하면, 해당 요소를 삭제한다.
    // 해당 함수의 삭제 결과는 true or false
    // 정상적으로 삭제가 이뤄지면 true 반환, 삭제를 실패하면 false 반환
    boolean removed = people.removeIf(person -> person.getId() == id);

    if (!removed) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }

    return "%d번 사람이 삭제되었습니다.".formatted(id);
  }

  // 요청 : http://localhost:8080/home/modifyPerson?id=1&name=홍홍홍&age=44
  // 응답 : 1번 사람이 수정되었습니다.
  @GetMapping("/home/modifyPerson")
  @ResponseBody
  public String modifyPerson(int id, String name, int age) { // 파라미터를 3개 받음


    /*Person target = null;

    // 반복문 돌려서 리스트 안에서 id에 맞는 객체 찾아서 삭제
    for (Person p : people) {
      if (p.getId() == id) {
        target = p;
        break;
      }
    }

    if (target == null) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }

    // System.out.println(target); // 객체 정보 잘 찾았나 확인

    // /home/modifyPerson?id=1&name=홍홍홍&age=44
    // 파라미터 입력한 값을 서버에 저장을 해줘야 수정이 됨
    // person 클래스는 각 필드를 private로 접근을 제한하기 때문에
    // 접근하기 위해 Setter가 필요!
    // JSON 라이브러리가 Getter를 통해 데이터를 가져와서 화면에 띄워줌
    // JSON -> 자바 오브젝트로 변환해야 한다
    // 이때 필요한 것이 Setter
    // Setter는 상수처리 = final을 풀어줘야 접근 가능
    // id는 고유하기 때문에 final을 풀어줄 필요 없다


     */

    Person target = people.stream()
            .filter(p -> p.getId() == id) // 해당 녀석이 참인 것만 필터링
            .findFirst() // 찾은 것 중에 하나만 남는데, 그 하나 남은 것을 필터링
            .orElse(null); // 없으면 null 리턴

    if(target == null) {
      return "%d번 사람이 존재하지 않습니다.".formatted(id);
    }

    target.setName(name);
    target.setAge(age); // 수정 완료


    return "%d번 사람이 수정되었습니다.".formatted(id);


  }

  @NoArgsConstructor // 비어있는 생성자 메서드
  @AllArgsConstructor
  @Data
  class Article {
    int id;
    String subject;
    String content;
  }

  @AllArgsConstructor
  @Data // 게터, 세터, ToString
// 쿼리 파라미터로 받은 사람 데이터를 담을 클래스가 필요함
  class Person {
    private static int lastId; // lastId를 static으로 생성
    private final int id; // 모든 객체는 고유의 아이디를 가지기 때문에 식별자가 필요하다
    private String name; // 수정할 일이 없는 정보이기 때문에 private final 붙이고
    private int age; // private는 게터, 세터가 필수! final은 생성자 메서드 필수!

    static { // static 생성자
      // static은 프로그램 실행 되지마자 딱 한 번 실행됨
      lastId = 0; // lastId 세팅
    }

    // 메서드 오버로딩
    public Person(String name, int age) { // 데이터가 id, name, age 세 개이지만 두 개만 받는 생성자임
      // 어떻게 id를 받아오나
      this(lastId++, name, age); // 생성자 메서드 안에서 this는 자기 자신 생성자메서드
      // this.id = id; 와 같은 것
      // 이로써 객체가 생성될 때마다 id값이 1씩 증가됨
    }
  }
}