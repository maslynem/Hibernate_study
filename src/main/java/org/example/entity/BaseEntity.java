package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

public interface BaseEntity<K extends Serializable> {
    K getId();

    void setId(K id);
}
