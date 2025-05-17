package com.highway.lottery.common.dto;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public abstract  class BaseEntity {

//    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(length = 36, updatable = false, nullable = false)
//    private UUID id;
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_seq")
//    @SequenceGenerator(name = "entity_seq", sequenceName = "entity_sequence", allocationSize = 50)
//    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_number_seq")
    @SequenceGenerator(name = "ticket_number_seq", sequenceName = "ticket_number_seq", allocationSize = 50)
    private Long id;



    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;


    @Column(name="active", nullable = false)
    private Boolean active = true;

    @Version
    @Column(name = "version")
    private Integer version;
}
