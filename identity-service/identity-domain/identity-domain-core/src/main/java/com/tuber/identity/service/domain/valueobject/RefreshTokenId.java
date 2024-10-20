package com.tuber.identity.service.domain.valueobject;

import com.tuber.domain.valueobject.id.BaseId;

public class RefreshTokenId extends BaseId<String> {
    public RefreshTokenId(String token) {
        super(token);
    }

    public String getToken() {
        return this.getValue();
    }
}
