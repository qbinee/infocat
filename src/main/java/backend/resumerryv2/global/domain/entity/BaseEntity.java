/* Licensed under InfoCat */
package backend.resumerryv2.global.domain.entity;

import backend.resumerryv2.global.converter.BooleanConverter;
import java.time.LocalDateTime;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
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
    private Boolean isDelete;

    @PrePersist
    public void prePersist() {
        this.isDelete = this.isDelete == null ? false : this.isDelete;
    }
}
