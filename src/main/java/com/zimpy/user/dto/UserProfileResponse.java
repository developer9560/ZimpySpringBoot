package com.zimpy.user.dto;

public class UserProfileResponse {

    private Long id;
    private String email;
    private String phone;
    private String fullName;

    public UserProfileResponse(
            Long id,
            String email,
            String phone,
            String fullName) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;

    }

    // getters

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullName() {
        return fullName;
    }
}
