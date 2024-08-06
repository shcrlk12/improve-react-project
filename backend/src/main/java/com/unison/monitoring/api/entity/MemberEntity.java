package com.unison.monitoring.api.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "Member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class MemberEntity {
    @Id
    private String id;

    @Column(nullable = false, length = 64)
    //SHA-256
    private String pw;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = true)
    @Setter
    private LocalDateTime lastLoginTime;

    @Column(nullable = false)
    @ColumnDefault("1")
    private boolean isActive;

    @Column(nullable = false)
    @ColumnDefault("0")
    private boolean isDelete;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @ColumnDefault("'System'")
    private String createdBy;

    @UpdateTimestamp
    @Column(nullable = true)
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private String updatedBy;
}
