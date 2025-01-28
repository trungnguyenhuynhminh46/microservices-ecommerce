package com.tuber.domain.valueobject.id;

import java.util.UUID;

public class UniqueUUID extends BaseId<UUID> {
    public UniqueUUID(UUID uuid) {
        super(uuid);
    }
}
