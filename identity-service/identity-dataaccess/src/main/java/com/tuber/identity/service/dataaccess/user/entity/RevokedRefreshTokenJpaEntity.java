package com.tuber.identity.service.dataaccess.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "revoked_refresh_token")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RevokedRefreshTokenJpaEntity {
    @Id
    String token;
    @Column(name = "user_id", nullable = false)
    UUID userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false, insertable = false)
    @ToString.Exclude
    UserAccountJpaEntity userAccount;
    @Column(name = "expires_in", nullable = false)
    Instant expiresIn;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RevokedRefreshTokenJpaEntity that = (RevokedRefreshTokenJpaEntity) o;
        return getToken() != null && Objects.equals(getToken(), that.getToken());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
