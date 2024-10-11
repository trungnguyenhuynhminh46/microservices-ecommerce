package com.tuber.entity;

import com.tuber.domain.entity.AggregateEntity;
import com.tuber.domain.valueobject.id.UserAccountId;

import java.time.LocalDate;
import java.util.Set;

public class UserAccount extends AggregateEntity<UserAccountId> {
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private LocalDate createdAt;
    private LocalDate updatedAt;

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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
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
