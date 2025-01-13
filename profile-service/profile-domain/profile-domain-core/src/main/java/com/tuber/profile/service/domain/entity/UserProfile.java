package com.tuber.profile.service.domain.entity;

import com.tuber.domain.entity.AggregateEntity;
import com.tuber.domain.valueobject.id.UniqueUUIDId;

import java.time.LocalDate;
import java.util.UUID;

public class UserProfile extends AggregateEntity<UniqueUUIDId> {
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String city;

    private UserProfile(Builder builder) {
        super.setId(builder.id);
        userId = builder.userId;
        firstName = builder.firstName;
        lastName = builder.lastName;
        dob = builder.dob;
        city = builder.city;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void setId(UUID id) {
        super.setId(new UniqueUUIDId(id));
    }


    public static final class Builder {
        private UniqueUUIDId id;
        private String userId;
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private String city;

        private Builder() {
        }

        public Builder id(UniqueUUIDId val) {
            id = val;
            return this;
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder firstName(String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(String val) {
            lastName = val;
            return this;
        }

        public Builder dob(LocalDate val) {
            dob = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public UserProfile build() {
            return new UserProfile(this);
        }
    }
}
