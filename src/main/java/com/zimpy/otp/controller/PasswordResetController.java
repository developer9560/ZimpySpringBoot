package com.zimpy.otp.controller;

import com.zimpy.common.ApiResponse;
import com.zimpy.otp.dto.OtpVerifyRequest;
import com.zimpy.otp.dto.PasswordResetConfirmRequest;
import com.zimpy.otp.dto.PasswordResetRequest;
import com.zimpy.otp.service.PasswordResetService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/password ")
public class PasswordResetController {

    private final PasswordResetService service;

    public PasswordResetController(PasswordResetService service) {
        this.service = service;
    }

    @PostMapping("/reset/request")
    public ApiResponse<Void> request(
            @Valid @RequestBody PasswordResetRequest request
    ){
        service.requestReset(request.getEmail());
        return new ApiResponse<>(200,
                "If email exists, OTP sent ",
                null
        );
    }

    @PostMapping("/reset/verify")
    public ApiResponse<Void> verify(
            @Valid @RequestBody OtpVerifyRequest request
    ){
        service.verifyOtp(request.getEmail(), request.getOtp());
        return new ApiResponse<>(200, "OTP verified", null);
    }

    @PostMapping("/reset/confirm")
    public ApiResponse<Void> confirm(
            @Valid @RequestBody PasswordResetConfirmRequest request
    ) {
        service.resetPassword(
                request.getEmail(),
                request.getOtp(),
                request.getNewPassword()
        );
        return new ApiResponse<>(200, "Password reset successful", null);
    }
    @PostMapping("/reset/resend")
    public ApiResponse<Void> resend(
            @Valid @RequestBody PasswordResetRequest request
    ){
        service.resendOtp(request.getEmail());
        return new ApiResponse<>(200, "OTP resent", null);
    }

}
