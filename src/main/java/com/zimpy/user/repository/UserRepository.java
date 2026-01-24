package com.zimpy.user.repository;

import com.zimpy.user.entity.Role;
import com.zimpy.user.entity.User;
import com.zimpy.user.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    Page<User> findByRole(Role role, Pageable pageable);
    long countByStatus(UserStatus status);
    @Query("""
            SELECT COUNT(u) FROM User u WHERE u.createdAt>=:from
            """)
    long countNewUserSince(LocalDateTime from);

}
