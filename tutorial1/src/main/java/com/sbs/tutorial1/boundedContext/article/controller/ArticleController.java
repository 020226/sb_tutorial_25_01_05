package com.sbs.tutorial1.boundedContext.article.controller;

import com.sbs.tutorial1.base.rsData.RsData;
import com.sbs.tutorial1.boundedContext.article.entity.Article;
import com.sbs.tutorial1.boundedContext.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article") // /article을 생략할 수 있도록 미리 만들어줌
@RequiredArgsConstructor // 필드 중에서 final 붙은 것만 인자로 입력받는 생성자 생성. Rq 사용을 위해.
public class ArticleController {
  // Service가 없어서 임시로 Repository 생성
  private final ArticleService articleService; // 서비스 객체는 스프링부트 IoC컨테이너에 의해 관리

  @GetMapping("/write")
  @ResponseBody
  public RsData write(String subject, String content) {
    // 제목과 내용 입력 안 되었을 때 유효성검사
    if(subject == null || subject.trim().isEmpty()) {
      return RsData.of("F-1", "subject(을)를 입력해주세요.");
    }

    if(content == null || content.trim().isEmpty()) {
      return RsData.of("F-2", "content(을)를 입력해주세요.");
    }

    Article createArticle = articleService.write(subject, content);


    return RsData.of("S-1", "%d번 글이 생성되었습니다".formatted(createArticle.getId()), createArticle);
  }
}
