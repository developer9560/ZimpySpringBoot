package com.zimpy.user.dto;

import com.zimpy.user.entity.Role;

import java.time.LocalDateTime;

public class FullUserProfileResponse {
    private Long id;
    private String email;
    private String phone;
    private String fullName;
    private Role role;
    private LocalDateTime createdAt;

    public FullUserProfileResponse(Long id, String email, String phone, String fullName, Role role,
            LocalDateTime createdAt) {

        this.id = id;
        this.email = email;
        this.phone = phone;
        this.fullName = fullName;

        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
