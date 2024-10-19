package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.domain.valueobject.id.EnumId;

public class Permission extends BaseEntity<EnumId<UserPermission>> {
    private String description;

    public Permission(EnumId<UserPermission> permission) {
        super.setId(permission);
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return super.getId().getValue().toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
