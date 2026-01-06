package com.zimpy.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserProfileRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
