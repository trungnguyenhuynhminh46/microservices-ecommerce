package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.exception.IdentityDomainException;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class UserAccount extends AggregateRoot<UniqueUUID> {
    private String username;
    private String email;
    private String password;
    private boolean passwordEncoded = false;
    private Set<Role> roles;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public void setId(UUID id) {
        super.setId(new UniqueUUID(id));
    }

    private UserAccount(Builder builder) {
        super.setId(builder.id);
        username = builder.username;
        email = builder.email;
        password = builder.password;
        passwordEncoded = builder.passwordEncoded;
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

    public boolean isPasswordEncoded() {
        return passwordEncoded;
    }

    public void setPasswordEncoded(boolean passwordEncoded) {
        this.passwordEncoded = passwordEncoded;
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

    public String getUserId() {
        return this.getId().getValue().toString();
    }

    public static final class Builder {
        private UniqueUUID id;
        private String username;
        private String email;
        private String password;
        private boolean passwordEncoded;
        private Set<Role> roles;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
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

        public Builder passwordEncoded(boolean val) {
            passwordEncoded = val;
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

    public boolean isValidForInitialization() {
        return getUsername() != null && getEmail() != null && getPassword() != null && getId() == null;
    }

    public void validateUserAccount() {
        if (!isValidForInitialization()) {
            throw new IdentityDomainException(IdentityResponseCode.USER_ACCOUNT_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
    }

    public void initializeUserAccount() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }
}
