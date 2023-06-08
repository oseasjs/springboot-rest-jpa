package com.springboot.rest.api.blog.model;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Table(schema = "blog",
  indexes = {@Index(name = "comment_index", columnList = "author, content, post_id", unique = true)}
)
@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
