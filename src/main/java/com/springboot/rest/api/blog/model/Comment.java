package com.springboot.rest.api.blog.model;

import java.time.LocalDateTime;

import org.hibernate.envers.Audited;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "blog",
  indexes = {@Index(name = "comment_index", columnList = "author, content, post_id", unique = true)}
)
@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Column(nullable = false)
  private String content;

  @NotNull
  @Column(nullable = false)
  private String author;

  @NotNull
  @Column(nullable = false)
  private LocalDateTime creationDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GeneratedTypeEnum generatedType;

  @NotNull
  @ManyToOne
  @JoinColumn(name = "post_id", referencedColumnName = "id", nullable = false)
  private Post post;

  @Column
  private LocalDateTime moderationDate;

  @Column
  private String moderationReason;

}
