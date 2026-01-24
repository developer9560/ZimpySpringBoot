package com.zimpy.user.UserController;

import com.zimpy.common.ApiResponse;
import com.zimpy.exception.UnauthorizedException;
import com.zimpy.user.Services.UserService;
import com.zimpy.user.dto.FullUserProfileResponse;
import com.zimpy.user.dto.UpdateUserProfileRequest;
import com.zimpy.user.dto.UserProfileResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> me(Authentication authentication){
        if(authentication==null|| !authentication.isAuthenticated()){
            throw new UnauthorizedException("please login first");
        }
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(  new ApiResponse<>(200,"succefully get",userService.getCurrentUser(userId)) );
    }
    @GetMapping("/allusers")
    @PreAuthorize("hasRole('ADMIN')")
    private ResponseEntity<ApiResponse<List<FullUserProfileResponse>>> getAllUsers(Authentication authentication){
        if(authentication==null || !authentication.isAuthenticated()){
            throw new UnauthorizedException("please login");
        }

        return ResponseEntity.ok(new ApiResponse<>(200,"all users found",userService.getAll()));
    }

    @PatchMapping("/me")
    public UserProfileResponse me(Authentication authentication, @Valid @RequestBody UpdateUserProfileRequest request){
        if(authentication==null || !authentication.isAuthenticated()){
            throw new UnauthorizedException("you are not login , please logi first");
        }
        Long userId =(Long) authentication.getPrincipal();
        return userService.updateProfile(userId,request);
    }

}
