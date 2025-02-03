package com.tuber.identity.service.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserData {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String city;
    private List<String> roleName;
}

