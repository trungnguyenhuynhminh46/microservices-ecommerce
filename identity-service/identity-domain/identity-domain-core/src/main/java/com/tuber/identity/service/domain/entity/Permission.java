package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.id.EnumId;

import java.util.HashSet;
import java.util.Set;

public class Permission extends BaseEntity<EnumId<UserPermission>> {
    private String description;

    private Set<Role> roles = new HashSet<>();

    private Permission(Builder builder) {
        super.setId(builder.id);
        setDescription(builder.description);
        setRoles(builder.roles);
    }

    public static Builder builder() {
        return new Builder();
    }

    public UserPermission getName() {
        return super.getId().getValue();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void assignForRole(Role role) {
        this.roles.add(role);
        role.getPermissions().add(this);
    }

    public void removeFromRole(Role role) {
        this.roles.remove(role);
        role.getPermissions().remove(this);
    }

    public static final class Builder {
        private EnumId<UserPermission> id;
        private String description;
        private Set<Role> roles = new HashSet<>();

        private Builder() {
        }

        public Builder id(UserPermission id) {
            this.id = new EnumId<>(id);
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder roles(Set<Role> val) {
            roles = val;
            return this;
        }

        public Permission build() {
            return new Permission(this);
        }
    }
}
