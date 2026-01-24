package com.zimpy.admin.dto;

import com.zimpy.user.entity.UserStatus;

public class UpdateUserStatusRequest {
    private UserStatus status;

    public UserStatus getStatus() {
        return status;
    }
}
