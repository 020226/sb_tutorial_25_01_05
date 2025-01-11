package com.sbs.tutorial1.boundedContext.article.repository;

import com.sbs.tutorial1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository : 생략가능
// SQL 쿼리 등 JPA, DB 관련
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
