package com.tuber.profile.service.domain.entity;

import com.tuber.domain.entity.AggregateEntity;
import com.tuber.domain.valueobject.id.UniqueUUIDId;
import com.tuber.profile.service.domain.constant.ProfileResponseCode;
import com.tuber.profile.service.domain.exception.ProfileDomainException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

public class UserProfile extends AggregateEntity<UniqueUUIDId> {
    private final int MIN_AGE = 16;
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

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getCity() {
        return city;
    }

    public UserProfile validate() {
        validateForInitialize();
        validateDob();
        return this;
    }

    public UserProfile initialize() {
        setId(new UniqueUUIDId(UUID.randomUUID()));
        return this;
    }

    protected void validateForInitialize() {
        if(getId() != null) {
            throw new ProfileDomainException(ProfileResponseCode.DEFAULT_RESPONSE_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value(), "User profile already initialized");
        }
    }

    protected void validateDob() {
        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < MIN_AGE) {
            throw new ProfileDomainException(ProfileResponseCode.DEFAULT_RESPONSE_CODE, HttpStatus.BAD_REQUEST.value(), String.format("User must be at least %s years old", MIN_AGE));
        }
    }
}
