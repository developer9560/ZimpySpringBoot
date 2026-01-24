package com.zimpy.user.Services;

import com.zimpy.exception.ResourceNotFoundException;
import com.zimpy.user.dto.FullUserProfileResponse;
import com.zimpy.user.dto.UpdateUserProfileRequest;
import com.zimpy.user.dto.UserProfileResponse;
import com.zimpy.user.entity.User;
import com.zimpy.user.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileResponse getCurrentUser(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new UserProfileResponse(user.getId(), user.getEmail(), user.getPhone(), user.getFullName());
    }

    public List<FullUserProfileResponse> getAll() {
        List<User> userList = userRepository.findAll();

        return userList.stream().map(user -> new FullUserProfileResponse(user.getId(), user.getEmail(), user.getPhone(),
                user.getFullName(), user.getRole(), user.getCreatedAt())).toList();
    }

    public UserProfileResponse updateProfile(Long userId, UpdateUserProfileRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.getFullName());
        userRepository.save(user);
        return new UserProfileResponse(user.getId(), user.getEmail(), user.getPhone(), user.getFullName());

    }

}
