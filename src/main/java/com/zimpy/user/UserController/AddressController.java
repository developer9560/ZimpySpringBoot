package com.zimpy.user.UserController;

import com.zimpy.user.Services.AddressService;
import com.zimpy.user.dto.AddressRequest;
import com.zimpy.user.dto.AddressResponse;
import com.zimpy.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> addAddress(Authentication authentication , @Valid @RequestBody AddressRequest request){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(addressService.addAddress(userId,request));
    }
    @GetMapping
    public ResponseEntity<List<AddressResponse>> myAddress(Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(addressService.getMyAddresses(userId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long id,
            @Valid @RequestBody AddressRequest request,
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(addressService.updateAddress(userId, id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String >deleteAddress(@PathVariable Long id, Authentication authentication){
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(addressService.deleteAddress(userId, id));
    }
}
