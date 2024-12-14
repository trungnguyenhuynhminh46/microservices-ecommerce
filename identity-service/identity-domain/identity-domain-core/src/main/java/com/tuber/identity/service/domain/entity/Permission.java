package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.UniqueStringId;

public class Permission extends BaseEntity<UniqueStringId> {
    private String description;

    private Permission(Builder builder) {
        super.setId(builder.id);
        setDescription(builder.description);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return super.getId().getValue();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static final class Builder {
        private UniqueStringId id;
        private String description;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = new UniqueStringId(id);
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
