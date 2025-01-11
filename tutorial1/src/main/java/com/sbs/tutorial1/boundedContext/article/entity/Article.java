package com.sbs.tutorial1.boundedContext.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // 엔티티 변경 사항을 감지하는 리스너
public class Article {
  @Id // primary key가 id 칼럼 적용
  @GeneratedValue(strategy = IDENTITY) // AUTO_INCREMENT
  private long id;

  @CreatedDate // 생성 시 자동으로 값 설정
  @Column(updatable = false) // 업데이트 발생시 날짜가 수정되지 않도록 설정
  private LocalDateTime createDate; // 데이터 생성 날짜

  @LastModifiedDate // 수정 시 자동으로 값 갱신
  private LocalDateTime modifyDate; // 데이터 수정 날짜
  private String subject;
  private String content;
}