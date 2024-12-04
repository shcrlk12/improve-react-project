package com.unison.monitoring.alarm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "Alarm")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class AlarmEntity {

    @EmbeddedId
    private Id id;

    @Column(nullable = false)
    private String alarmCode;

    @Column(nullable = true)
    private String comment;

    @Column(nullable = true)
    private String remark;

    @Getter
    @Setter
    @Embeddable
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Id implements Serializable {
        @Column
        private LocalDateTime timestamp;

        @Column
        private String alarmNumber;

        @Column
        private UUID turbineUuid;
    }
}
