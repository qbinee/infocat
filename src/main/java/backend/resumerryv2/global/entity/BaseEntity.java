/* Licensed under InfoCat */
package backend.resumerryv2.global.entity;

import backend.resumerryv2.global.converter.BooleanConverter;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseEntity {

  @CreatedDate private LocalDateTime createdDate;

  @LastModifiedDate private LocalDateTime modifiedDate;

  @Convert(converter = BooleanConverter.class)
  @Column
  private Boolean isDelete;

  @PrePersist
  public void prePersist() {
    this.isDelete = false;
  }
}
