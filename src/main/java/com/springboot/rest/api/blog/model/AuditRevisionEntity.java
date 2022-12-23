package com.springboot.rest.api.blog.model;

import com.springboot.rest.api.blog.audit.AuditRevisionListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;

@Data
@Entity
@Table(name = "revinfo", schema = "audit")
@AttributeOverrides({
  @AttributeOverride(name = "timestamp", column = @Column(name = "revtstmp")),
  @AttributeOverride(name = "id", column = @Column(name = "rev"))
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditRevisionListener.class)
public class AuditRevisionEntity extends DefaultRevisionEntity {
  @NotBlank
  private String username;
}
