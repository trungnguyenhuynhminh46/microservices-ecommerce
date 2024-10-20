package com.tuber.identity.service.dataaccess.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "refresh_token")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshTokenJpaEntity {
    @Id
    String token;

    @Column(name = "user_id", nullable = false)
    UUID userId;

    boolean isRevoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    UserAccountJpaEntity userAccount;
}
