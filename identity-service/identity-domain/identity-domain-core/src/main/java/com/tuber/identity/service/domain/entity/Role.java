package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.domain.valueobject.id.EnumId;

import java.util.Set;

public class Role extends BaseEntity<EnumId<UserRole>> {
    private String description;

    private Set<Permission> permissions;

    public Role(EnumId<UserRole> role) {
        super.setId(role);
    }

    public Role(EnumId<UserRole> role, Set<Permission> permissions) {
        super.setId(role);
        this.permissions = permissions;
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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
