package org.example.listener;

import org.example.entity.Audit;
import org.hibernate.event.spi.*;

public class AuditTableListener implements PreDeleteEventListener, PreInsertEventListener {
    @Override
    public boolean onPreDelete(PreDeleteEvent event) {
        auditEntity(event, "Delete");
        return false;
    }

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        auditEntity(event, "Insert");
        return false;
    }

    public void auditEntity(AbstractPreDatabaseOperationEvent event, String operation) {
        if (event.getEntity().getClass() != Audit.class) {
            Audit audit = Audit.builder()
                    .entityId(event.getId())
                    .entityName(event.getEntityName())
                    .entityContent(event.getEntity().toString())
                    .operation(operation)
                    .build();
            event.getSession().save(audit);
        }

    }
}
