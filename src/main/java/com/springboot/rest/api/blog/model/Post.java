package com.springboot.rest.api.blog.model;

import com.springboot.rest.api.blog.enums.GeneratedTypeEnum;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(schema = "blog",
  indexes = {@Index(name = "post_index", columnList = "title, content", unique = true)}
)
@Audited
@Data
@AllArgsConstructor
@NoArgsConstructor
//@Builder
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

}
