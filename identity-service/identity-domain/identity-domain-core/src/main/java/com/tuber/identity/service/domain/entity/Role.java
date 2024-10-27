package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;

import java.util.HashSet;
import java.util.Set;

public class Role extends BaseEntity<String> {
    private String description;

    private Set<Permission> permissions = new HashSet<>();

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
        return super.getId();
    }

    public static final class Builder {
        private String id;
        private String description;
        private Set<Permission> permissions = new HashSet<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
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
