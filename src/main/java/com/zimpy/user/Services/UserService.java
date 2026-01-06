package com.zimpy.user.Services;

import com.zimpy.user.dto.UpdateUserProfileRequest;
import com.zimpy.user.dto.UserProfileResponse;
import com.zimpy.user.entity.User;
import com.zimpy.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserProfileResponse getCurrentUser(Long userId){

        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));

        return new UserProfileResponse(user.getId(),user.getEmail(),user.getPhone(),user.getFirstName(),user.getLastName());
    }

    public UserProfileResponse updateProfile(Long userId, UpdateUserProfileRequest request){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);
        return  new UserProfileResponse(user.getId(),user.getEmail(),user.getPhone(), user.getFirstName(), user.getLastName());

    }

}
