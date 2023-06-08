package com.springboot.rest.api.blog.model;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Table(schema = "blog",
  indexes = {@Index(name = "post_index", columnList = "title, content", unique = true)}
)
@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post extends BaseAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  private String title;

  @Column(nullable = false)
  @NotNull
  private String content;

  @Column(nullable = false)
  @NotNull
  private LocalDateTime creationDate;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private GeneratedTypeEnum generatedType;

  @Column
  private LocalDateTime moderationDate;

  @Column
  private String moderationReason;

}
