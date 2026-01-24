package com.zimpy.auth.AuthController;

import com.zimpy.auth.dto.AdminSignupRequest;
import com.zimpy.auth.dto.LoginRequest;
import com.zimpy.auth.dto.LoginResponse;
import com.zimpy.auth.dto.SignupRequest;
import com.zimpy.security.JwtUtil;
import com.zimpy.user.entity.User;
import com.zimpy.user.entity.Role;
import com.zimpy.user.repository.UserRepository;
import jakarta.validation.Valid;

import com.zimpy.exception.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFirstName());
        user.setRole(Role.USER);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AuthenticationException("Invalid Email "));

        if(!passwordEncoder.matches(request.getPassword(),user.getPasswordHash())){
           throw  new AuthenticationException("Invalid email or password");

        }

        JwtUtil jwtUtil = new JwtUtil();

        String token = jwtUtil.generateToken(user.getId(),user.getRole().name());

        return ResponseEntity.ok(new LoginResponse(token));
    }
    @PostMapping("/admin-suraj/signup/admin")
    public ResponseEntity<?> Adminsignup(@Valid @RequestBody AdminSignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            return ResponseEntity.badRequest().body("Phone already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }


}
