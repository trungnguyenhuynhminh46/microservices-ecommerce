package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.id.EnumId;

import java.util.HashSet;
import java.util.Set;

public class Permission extends BaseEntity<EnumId<UserPermission>> {
    private String description;

    private Permission(Builder builder) {
        super.setId(builder.id);
        setDescription(builder.description);
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

    public static final class Builder {
        private EnumId<UserPermission> id;
        private String description;

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

        public Permission build() {
            return new Permission(this);
        }
    }
}
