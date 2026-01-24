package com.zimpy.admin.service;

import com.zimpy.admin.dto.AdminUserAnalyticsResponse;
import com.zimpy.admin.dto.UserDetailResponse;
import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.user.entity.Role;
import com.zimpy.user.entity.User;
import com.zimpy.user.entity.UserStatus;
import com.zimpy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // get all users
    public Page<UserDetailResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::entityToResponse);
    }

    public Page<UserDetailResponse> getUsers(UserStatus status, Role role, Pageable pageable) {
        if (status != null) {
            return userRepository.findByStatus(status, pageable).map(this::entityToResponse);
        }
        if (role != null) {
            return userRepository.findByRole(role, pageable).map(this::entityToResponse);
        }
        return userRepository.findAll(pageable).map(this::entityToResponse);

    }

    public AdminUserAnalyticsResponse getUserAnalytics() {
        long total = userRepository.count();
        long active = userRepository.countByStatus(UserStatus.ACTIVE);
        long blocked = userRepository.countByStatus(UserStatus.BLOCKED);
        long inactive = userRepository.countByStatus(UserStatus.INACTIVE);
        long today = userRepository.countNewUserSince(LocalDateTime.now().minusDays(1));
        long month = userRepository.countNewUserSince(LocalDateTime.now().minusDays(30));
        return new AdminUserAnalyticsResponse(total, active, blocked, inactive, today, month);
    }

    @Transactional
    public void updateStatus(Long userId, UserStatus status) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        if (user.getStatus() == status) {
            return; // idempotent
        }

        user.setStatus(status);
        userRepository.save(user);
    }

    private UserDetailResponse entityToResponse(User user) {
        return new UserDetailResponse(user.getId(), user.getEmail(), user.getFullName(),
                user.getPhone(), user.getRole(), user.getStatus(), user.getCreatedAt(), user.getUpdatedAt());
    }

}
