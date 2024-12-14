package com.tuber.domain.valueobject.id;

import java.util.UUID;

public class UniqueUUIDId extends BaseId<UUID> {
    public UniqueUUIDId(UUID uuid) {
        super(uuid);
    }
}
