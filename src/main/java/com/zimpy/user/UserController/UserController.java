package com.zimpy.user.UserController;

import com.zimpy.user.Services.UserService;
import com.zimpy.user.dto.UpdateUserProfileRequest;
import com.zimpy.user.dto.UserProfileResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserProfileResponse me(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return userService.getCurrentUser(userId);
    }
    @PatchMapping("/me")
    public UserProfileResponse me(Authentication authentication, @Valid @RequestBody UpdateUserProfileRequest request){
        Long userId =(Long) authentication.getPrincipal();
        return userService.updateProfile(userId,request);
    }


}
