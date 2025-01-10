package com.sbs.tutorial1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Article {
  @Id // id 칼럼에 PRIMARY KEY 적용
  @GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT
  private long id;
  private LocalDateTime createDate; // 데이터 생성 날짜
  private LocalDateTime modifyDate; // 데이터 수정 날짜
  private String subject;
  private String content;
}
