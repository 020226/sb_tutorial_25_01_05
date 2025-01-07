package com.sbs.tutorial1.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller // 이 클래스는 컨트롤러이고 웹 요청을 받아서 작업한다
public class HomeController {
  int num;
  List<Person> people;

  public HomeController() {
    num = -1;
    people = new ArrayList<>();
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

  // 테스트 케이스 생성
  @GetMapping("/home/personTestcase")
  @ResponseBody
  public String personTestcase() {
    people.add(new Person("홍길동", 11));
    people.add(new Person("홍길순", 22));
    people.add(new Person("임꺽정", 33));

    return "테스트 케이스 추가";
  }

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

     if(!removed) {
       return "%d번 사람이 존재하지 않습니다.".formatted(id);
     }

    return "%d번 사람이 삭제되었습니다.".formatted(id);
  }
}

@NoArgsConstructor // 비어있는 생성자 메서드
@AllArgsConstructor
@Data
class Article{
  int id;
  String subject;
  String content;
}

@AllArgsConstructor
@Data // 게터, 세터, ToString
// 쿼리 파라미터로 받은 사람 데이터를 담을 클래스가 필요함
class Person{
  private static int lastId; // lastId를 static으로 생성
  private final int id; // 모든 객체는 고유의 아이디를 가지기 때문에 식별자가 필요하다
  private final String name; // 수정할 일이 없는 정보이기 때문에 private final 붙이고
  private final int age; // private는 게터, 세터가 필수! final은 생성자 메서드 필수!

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