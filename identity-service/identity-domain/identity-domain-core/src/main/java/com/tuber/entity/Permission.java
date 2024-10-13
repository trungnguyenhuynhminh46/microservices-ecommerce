package com.tuber.entity;

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

    public void setDescription(String description) {
        this.description = description;
    }
}
