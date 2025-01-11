package com.sbs.tutorial1.boundedContext.article.service;

import com.sbs.tutorial1.boundedContext.article.entity.Article;
import com.sbs.tutorial1.boundedContext.article.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

  public Article write(String subject, String content) {
    // return articleRepository.write(subject, content); 기존 JSP까지는 이런 코드였지만 스프링부트는 안씀
    Article article = Article
        .builder()
        .subject(subject)
        .content(content)
        .build();

    // JPA가 알아서 판단해서 insert/update 쿼리 실행해줌
    articleRepository.save(article); // insert 또는 update 쿼리 실행

    return article;
  }
}
