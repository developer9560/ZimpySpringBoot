package com.zimpy.admin;

import com.cloudinary.Api;
import com.zimpy.admin.dto.AdminUserAnalyticsResponse;
import com.zimpy.admin.dto.UpdateUserStatusRequest;
import com.zimpy.admin.dto.UserDetailResponse;
import com.zimpy.admin.service.AdminService;
import com.zimpy.common.ApiResponse;
import com.zimpy.user.entity.Role;
import com.zimpy.user.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<Page<UserDetailResponse>>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(new ApiResponse<>(200, "users fetched", adminService.getAllUsers(pageable)));
    }

    @GetMapping("/users")
    public ApiResponse<Page<UserDetailResponse>> getUsers(
            @RequestParam(required = false) UserStatus status,
            @RequestParam(required = false) Role role,
            Pageable pageable) {
        return new ApiResponse<>(200, "users fetched", adminService.getUsers(status, role, pageable));
    }

    @GetMapping("/analytics")
    public ApiResponse<AdminUserAnalyticsResponse> analytics() {
        return new ApiResponse<>(200, "user Analytics", adminService.getUserAnalytics());
    }

    @PatchMapping("/users/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateStatus(
            @PathVariable Long userId,
            @RequestBody UpdateUserStatusRequest request) {
        adminService.updateStatus(userId, request.getStatus());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "User status updated successfully", null));
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Admin dashboard access granted";
    }

}
