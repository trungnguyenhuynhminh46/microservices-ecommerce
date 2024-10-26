package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.domain.valueobject.id.EnumId;

import java.util.Set;

public class Role extends BaseEntity<EnumId<UserRole>> {
    private String description;

    private Set<Permission> permissions;

    private Role(Builder builder) {
        super.setId(builder.id);
        setDescription(builder.description);
        setPermissions(builder.permissions);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return super.getId().getValue().toString();
    }


    public static final class Builder {
        private EnumId<UserRole> id;
        private String description;
        private Set<Permission> permissions;

        private Builder() {
        }

        public Builder id(UserRole id) {
            this.id = new EnumId<>(id);
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder permissions(Set<Permission> val) {
            permissions = val;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
