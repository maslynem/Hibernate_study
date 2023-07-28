package org.example.listener;

import org.example.entity.AuditableEntity;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;

public class AuditListener {

    @PrePersist
    public void prePersist(AuditableEntity obj) {
        obj.setCreatedAt(Instant.now());
    }

    @PreUpdate
    public void preUpdate(AuditableEntity obj) {
        obj.setUpdatedAt(Instant.now());
    }
}
