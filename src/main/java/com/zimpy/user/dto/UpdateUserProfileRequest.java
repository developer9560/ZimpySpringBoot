package com.zimpy.user.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserProfileRequest {
    @NotBlank
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
