package com.tuber.domain.valueobject.id;

public class EnumId<ID extends Enum<ID>> extends BaseId<ID> {

    public EnumId(ID id) {
        super(id);
    }
}
