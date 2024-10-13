package com.tuber.entity;

import com.tuber.domain.entity.AggregateEntity;
import com.tuber.domain.valueobject.id.UserAccountId;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class UserAccount extends AggregateEntity<UserAccountId> {
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public void setId(UUID id) {
        super.setId(new UserAccountId(id));
    }

    private UserAccount(Builder builder) {
        super.setId(builder.userAccountId);
        username = builder.username;
        email = builder.email;
        password = builder.password;
        roles = builder.roles;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static final class Builder {
        private UserAccountId userAccountId;
        private String username;
        private String email;
        private String password;
        private Set<Role> roles;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder userAccountId(UserAccountId val) {
            userAccountId = val;
            return this;
        }

        public Builder username(String val) {
            username = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDate val) {
            updatedAt = val;
            return this;
        }

        public UserAccount build() {
            return new UserAccount(this);
        }
    }
}
