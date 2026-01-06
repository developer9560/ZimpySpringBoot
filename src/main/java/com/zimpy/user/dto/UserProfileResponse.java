package com.zimpy.user.dto;

public class UserProfileResponse {

    private Long id;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;

    public UserProfileResponse(
            Long id,
            String email,
            String phone,
            String firstName,
            String lastName
    ) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
