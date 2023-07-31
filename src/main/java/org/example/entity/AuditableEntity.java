package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.listener.AuditListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditListener.class})
public abstract class AuditableEntity<K extends Serializable> implements BaseEntity<K> {
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
