package com.main.agency_booking.utils;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public abstract class PersistedEntity {
    
    public PersistedEntity() {
        deleted = false;
        dateCreated = LocalDateTime.now();
        dateModified = LocalDateTime.now();
    }
    
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;
    
    @Column(name = "date_modified", nullable = false)
    protected LocalDateTime dateModified;
    
    @Column(name = "deleted", nullable = false)
    protected boolean deleted;
}
