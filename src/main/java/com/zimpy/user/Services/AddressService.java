package com.zimpy.user.Services;

import com.zimpy.user.dto.AddressRequest;
import com.zimpy.user.dto.AddressResponse;
import com.zimpy.user.entity.Address;
import com.zimpy.user.entity.Type;
import com.zimpy.user.entity.User;
import com.zimpy.user.repository.AddressRepository;
import com.zimpy.user.repository.UserRepository;
import com.zimpy.util.AddressMapper;

import  java.util.List;
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;



    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressResponse addAddress(Long userId, AddressRequest request){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        if(request.isDefault()){
            addressRepository.findByUserId(userId)
                    .forEach(address -> {
                        address.setDefault(false);
                        addressRepository.save(address);
                    });
        }
        Address address = new Address();
        address.setUser(user);
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setDefault(request.isDefault());
        address.setType(request.getType());
        addressRepository.save(address);

        AddressResponse addressResponse = AddressMapper.enittyToResponse(address);
        return  addressResponse;
    }



}
