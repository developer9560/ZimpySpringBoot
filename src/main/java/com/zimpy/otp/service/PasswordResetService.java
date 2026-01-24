package com.zimpy.otp.service;

import com.zimpy.exception.BadRequestException;
import com.zimpy.otp.entity.PasswordResetOtp;
import com.zimpy.otp.repository.PasswordResetOtpRepository;
import com.zimpy.user.entity.User;
import com.zimpy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetOtpRepository otpRepo;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public PasswordResetService(
            UserRepository userRepository,
            PasswordResetOtpRepository otpRepo,
            PasswordEncoder passwordEncoder,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.otpRepo = otpRepo;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // 1️⃣ REQUEST OTP
    public void requestReset(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {

            String otp = String.valueOf(
                    100000 + new Random().nextInt(900000)
            );

            PasswordResetOtp entity = new PasswordResetOtp();
            entity.setEmail(email);
            entity.setOtpHash(passwordEncoder.encode(otp));
            entity.setExpiresAt(LocalDateTime.now().plusMinutes(10));
            entity.setUsed(false);

            otpRepo.save(entity);
            emailService.sendOtp(email, otp);
        });
        // Always return success (security)
    }

    // 2️⃣ VERIFY OTP
    public void verifyOtp(String email, String otp) {

        PasswordResetOtp record = otpRepo
                .findTopByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new BadRequestException("Invalid OTP"));

        if (record.isUsed())
            throw new BadRequestException("OTP already used");

        if (record.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new BadRequestException("OTP expired");

        if (!passwordEncoder.matches(otp, record.getOtpHash()))
            throw new BadRequestException("Invalid OTP");
    }

    // 3️⃣ RESET PASSWORD
    @Transactional
    public void resetPassword(String email, String otp, String newPassword) {

        PasswordResetOtp record = otpRepo
                .findTopByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(() -> new BadRequestException("Invalid OTP"));

        if (record.isUsed())
            throw new BadRequestException("OTP already used");

        if (record.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new BadRequestException("OTP expired");

        if (!passwordEncoder.matches(otp, record.getOtpHash()))
            throw new BadRequestException("Invalid OTP");

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        record.setUsed(true);
        otpRepo.save(record);
    }

    public void resendOtp(String email) {

        PasswordResetOtp lastOtp = otpRepo
                .findTopByEmailOrderByCreatedAtDesc(email)
                .orElse(null);

        if (lastOtp != null) {
            // cooldown: 60 seconds
            if (lastOtp.getCreatedAt()
                    .isAfter(LocalDateTime.now().minusSeconds(60))) {
                throw new BadRequestException(
                        "Please wait before requesting another OTP"
                );
            }
        }

        // generate new OTP
        String otp = String.valueOf(
                100000 + new Random().nextInt(900000)
        );

        PasswordResetOtp entity = new PasswordResetOtp();
        entity.setEmail(email);
        entity.setOtpHash(passwordEncoder.encode(otp));
        entity.setExpiresAt(LocalDateTime.now().plusMinutes(10));
        entity.setUsed(false);

        otpRepo.save(entity);
        emailService.sendOtp(email, otp);
    }

}
