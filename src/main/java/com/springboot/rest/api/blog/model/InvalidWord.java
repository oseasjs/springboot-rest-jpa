package com.springboot.rest.api.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Table(schema = "blog" ,
  indexes = {@Index(name = "invalid_word_index", columnList = "word", unique = true)}
)
@Audited
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvalidWord extends BaseAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String word;

  @Column(nullable = false)
  @NotNull
  private LocalDateTime creationDate;

}
